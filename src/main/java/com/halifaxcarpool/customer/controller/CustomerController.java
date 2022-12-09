package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.*;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.*;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.business.beans.Ride;
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
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
    private static final String VIEW_RECOMMENDED_RIDES = "view_recommended_rides";
    private static final String CUSTOMER_REGISTRATION_FORM = "register_customer_form";
    private static final String CUSTOMER_LOGIN_FROM = "login_customer_form";

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

        ICustomerLogin customerLogin = customerObjectFactory.getCustomerLogin();
        ICustomerAuthentication customerAuthentication = customerObjectFactory.getCustomerAuthentication();

        String email = customer.getCustomerEmail();
        String password = customer.getCustomerPassword();

        Customer validCustomer =  customerLogin.login(email, password, customerAuthentication);
        model.addAttribute("customer", customer);
        if (validCustomer == null) {
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 0);
            return "redirect:/customer/login";
        }
        httpServletRequest.getSession().setAttribute("loggedInCustomer", validCustomer);
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping("/customer/logout")
    String logoutCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("loggedInCustomer") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 1);
        }
        return "redirect:/";
    }

    @GetMapping("/customer/register")
    String registerCustomer(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return CUSTOMER_REGISTRATION_FORM;
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute("customer") Customer customer) {
        ICustomerRegistration customerRegistration = customerObjectFactory.getCustomerRegistration();
        customerRegistration.registerCustomer(customer);
        return "redirect:/customer/login";
    }

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model,
                     HttpServletRequest request) {
        if(request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object)1) {
            return "redirect:/customer/login";
        }
        String rideRequestsAttribute = "rideRequests";
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
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
        if(httpServletRequest.getSession().getAttribute("loggedInCustomer") == null || httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object)1) {
            return "redirect:/customer/login";
        }
        Customer customer = (Customer) httpServletRequest.getSession().getAttribute("loggedInCustomer");
        RideRequest rideRequest = new RideRequest(rideRequestId, customer.customerId, startLocation, endLocation);
        String recommendedSingleRidesAttribute = "recommendedSingleRides";
        String recommendedMultiRidesAttribute = "recommendedMultiRides";
        RideFinder rideFinder = customerObjectFactory.getDirectRouteRideFinder();
        rideFinder = new MultipleRouteRideFinderDecorator(rideFinder);
        List<List<Ride>> ListOfRideList = rideFinder.findMatchingRides(rideRequest);
        List<List<Ride>> singleRidesList = new ArrayList<>();
        List<List<Ride>> multiRidesList = new ArrayList<>();
        for (List<Ride> rideList: ListOfRideList) {
            if (1 == rideList.size()) {
                singleRidesList.add(rideList);
            }
            else {
                multiRidesList.add(rideList);
            }
        }
        model.addAttribute(recommendedSingleRidesAttribute, singleRidesList);
        model.addAttribute(recommendedMultiRidesAttribute, multiRidesList);
        model.addAttribute("rideRequestId", rideRequestId);
        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam("rideId") int rideId,
                           @RequestParam("rideRequestId") int rideRequestId, HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute("loggedInCustomer") == null || httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object)1) {
            return "redirect:/customer/login";
        }
        IRideToRequestMapper rideToRequestMapper = customerObjectFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = customerObjectDaoFactory.getRideToRequestMapperDao();
        rideToRequestMapper.sendRideRequest(rideId, rideRequestId, rideToRequestMapperDao);
        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model,
                                   HttpServletRequest request) {
        if(request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object)1) {
            return "redirect:/customer/login";
        }
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerId(customer.getCustomerId());
        model.addAttribute("rideRequest", rideRequest);
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public String createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest,
                                    HttpServletRequest request) {
        if(request.getSession().getAttribute("loggedInCustomer") == null || request.getSession().getAttribute("loggedInCustomer") == (Object)1) {
            return "redirect:/customer/login";
        }
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        rideRequest.setCustomerId(customer.customerId);
        IRideRequest rideRequestForCreation = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        rideRequestForCreation.createRideRequest(rideRequest, rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

}
