package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.*;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.customer.database.dao.CustomerDaoImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.*;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    private static final String INDEX_PAGE = "index";
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
    private static final String VIEW_RECOMMENDED_RIDES = "view_recommended_rides";
    private static final String CUSTOMER_REGISTRATION_FORM = "register_customer_form";
    private static final String CUSTOMER_LOGIN_FROM = "login_customer_form";
    private static final String CUSTOMER_PROFILE_FORM = "update_customer_profile";

    private final ICustomerBusinessObjectFactory customerObjectFactory = new CustomerBusinessObjectFactoryMain();
    private final ICustomerDaoObjectFactory customerObjectDaoFactory = new CustomerDaoObjectFactoryImplMain();

    private static final Map<String, String> redirectPageStorage = new HashMap<String, String>() {
        {
            put("login", CUSTOMER_LOGIN_FROM);
        }
    };

    @GetMapping("/customer/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        String customerLiteral = "customer";
        String loggedInCustomerLiteral = "loggedInCustomer";
        String loggedInErrorLiteral = "loggedInError";

        model.addAttribute(customerLiteral, new Customer());
        if (httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral) == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, "noError");
        } else if (httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral) == (Object) 0) {
            model.addAttribute(loggedInErrorLiteral, "error");
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 1);
        }

        return redirectPageStorage.get("login");
    }

    @PostMapping("/customer/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        String customerLiteral = "customer";
        String loggedInCustomerLiteral = "loggedInCustomer";

        ICustomer customerObj = customerObjectFactory.getCustomer();
        ICustomerAuthentication customerAuthentication = customerObjectFactory.getCustomerAuthentication();
        ICustomerAuthenticationDao customerAuthenticationDao = customerObjectDaoFactory.getCustomerAuthenticationDao();

        String email = customer.getCustomerEmail();
        String password = customer.getCustomerPassword();

        Customer validCustomer = customerObj.login(email, password, customerAuthentication, customerAuthenticationDao);
        model.addAttribute(customerLiteral, customer);

        if (validCustomer == null) {
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 0);
            return "redirect:/customer/login";
        }

        httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, validCustomer);
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping("/customer/logout")
    String logoutCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest) {
        String loggedInCustomerLiteral = "loggedInCustomer";

        if (httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral) != (Object) 0) {
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 1);
        }

        return "redirect:/";
    }

    @GetMapping("/customer/register")
    String registerCustomer(Model model, HttpServletRequest httpServletRequest) {
        String customerLiteral = "customer";
        String customerThatTriedToRegisterLiteral = "customerThatTriedToRegister";
        String registrationErrorLiteral = "registrationError";

        Customer customer = new Customer();
        Object registrationSessionErrorAttribute = httpServletRequest.getSession().getAttribute(customerThatTriedToRegisterLiteral);

        model.addAttribute(customerLiteral, customer);
        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(customerThatTriedToRegisterLiteral, null);
        }
        return CUSTOMER_REGISTRATION_FORM;
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest httpServletRequest) {
        String customerThatTriedToRegisterLiteral = "customerThatTriedToRegister";

        ICustomer customerObj = customerObjectFactory.getCustomer();
        ICustomerDao customerDaoObj = customerObjectDaoFactory.getCustomerDao();

        try {
            customerObj.registerCustomer(customer, customerDaoObj);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute(customerThatTriedToRegisterLiteral, e.getMessage());
            return "redirect:/customer/register";
        }

        return "redirect:/customer/login";
    }

    @GetMapping("/customer/view_profile")
    String getCustomerProfile(Model model,
                              HttpServletRequest request) {
        String customerProfileLiteral = "customerProfile";

        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        model.addAttribute(customerProfileLiteral, customer);
        return CUSTOMER_PROFILE_FORM;
    }

    @GetMapping("/customer/update_profile")
    String updateProfile(Model model) {
        String customerProfileLiteral = "customerProfile";

        model.addAttribute(customerProfileLiteral, new Customer());
        return CUSTOMER_PROFILE_FORM;
    }

    @PostMapping("/customer/update_profile")
    String updateCustomerProfile(@ModelAttribute("customerProfile") Customer customerProfile, HttpServletRequest request) {
        String loggedInCustomerLiteral = "loggedInCustomer";

        ICustomer customer = new CustomerImpl();
        Customer customerLoggedIn = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        customerProfile.setCustomerId(customerLoggedIn.getCustomerId());
        customerProfile.setCustomerPassword(customerLoggedIn.getCustomerPassword());
        ICustomerDao customerDao = new CustomerDaoImpl();
        customer.updateCustomer(customerProfile, customerDao);
        request.getSession().setAttribute(loggedInCustomerLiteral, customerProfile);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model,
                     HttpServletRequest request) {
        if (request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            return "redirect:/customer/login";
        }
        String rideRequestsAttribute = "rideRequests";
        String loggedInCustomerLiteral = "loggedInCustomer";

        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        IRideRequest viewRideRequests = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customer.getCustomerId(), rideRequestsDao);
        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

    @GetMapping("/customer/view_recommended_rides")
    String viewRecommendedRides(@RequestParam("rideRequestId") int rideRequestId,
                                @RequestParam("startLocation") String startLocation,
                                @RequestParam("endLocation") String endLocation,
                                HttpServletRequest httpServletRequest,
                                Model model) {
        //TODO refactor it
        if (httpServletRequest.getSession().getAttribute("loggedInCustomer") == null ||
                httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            return "redirect:/customer/login";
        }
        List<List<Ride>> singleRidesList = new ArrayList<>();
        List<List<Ride>> multiRidesList = new ArrayList<>();
        String loggedInCustomerLiteral = "loggedInCustomer";
        String rideRequestIdLiteral = "rideRequestId";
        String recommendedSingleRidesAttribute = "recommendedSingleRides";
        String recommendedMultiRidesAttribute = "recommendedMultiRides";

        Customer customer = (Customer) httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);
        RideRequest rideRequest = new RideRequest(rideRequestId, customer.getCustomerId(), startLocation, endLocation);

        RideFinder rideFinder = customerObjectFactory.getDirectRouteRideFinder();
        rideFinder = new MultipleRouteRideFinderDecorator(rideFinder);

        List<List<Ride>> ListOfRideList = rideFinder.findMatchingRides(rideRequest);

        for (List<Ride> rideList : ListOfRideList) {
            if (1 == rideList.size()) {
                singleRidesList.add(rideList);
            } else {
                multiRidesList.add(rideList);
            }
        }
        model.addAttribute(recommendedSingleRidesAttribute, singleRidesList);
        model.addAttribute(recommendedMultiRidesAttribute, multiRidesList);
        model.addAttribute(rideRequestIdLiteral, rideRequestId);

        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam("rideId") int rideId,
                           @RequestParam("rideRequestId") int rideRequestId, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedInCustomer") == null || httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            return "redirect:/customer/login";
        }
        IRideToRequestMapper rideToRequestMapper = customerObjectFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = customerObjectDaoFactory.getRideToRequestMapperDao();
        rideToRequestMapper.sendRideRequest(rideId, rideRequestId, rideToRequestMapperDao);
        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/view_ongoing_rides")
    public String viewOngoingRides(HttpServletRequest request,
                                   Model model) {
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        //TODO call driver factory
        IRide ride = new RideImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        List<Ride> ongoingRides = ride.viewOngoingRides(customer.getCustomerId(), ridesDao);
        model.addAttribute("activeRides", ongoingRides);
        return "view_ongoing_rides";
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model,
                                   HttpServletRequest request) {
        if (request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            return "redirect:/customer/login";
        }
        String loggedInCustomerLiteral = "loggedInCustomer";
        String rideRequestLiteral = "rideRequest";

        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerId(customer.getCustomerId());
        model.addAttribute(rideRequestLiteral, rideRequest);
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public String createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest,
                                    HttpServletRequest request) {
        if (request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            return "redirect:/customer/login";
        }
        String loggedInCustomerLiteral = "loggedInCustomer";

        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        rideRequest.setCustomerId(customer.getCustomerId());
        IRideRequest rideRequestForCreation = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        rideRequestForCreation.createRideRequest(rideRequest, rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

}
