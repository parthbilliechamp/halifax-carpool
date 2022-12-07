package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.commons.business.IRideToRequestMapper;
import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperImpl;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.customer.business.authentication.AuthenticationFacade;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.recommendation.DirectRouteRideFinder;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.customer.business.registration.CustomerRegistrationImpl;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.commons.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
    private static final String VIEW_RECOMMENDED_RIDES = "view_recommended_rides";
    private static final String CUSTOMER_REGISTRATION_FORM = "register_customer_form";
    private static final String CUSTOMER_LOGIN_FROM = "login_customer_form";


    @GetMapping("/customer/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("customer", new Customer());
        if(httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object) 1) {
            model.addAttribute("loggedInError", "noError");
        }
        else if(httpServletRequest.getSession().getAttribute("loggedInCustomer") == (Object) 0) {
            model.addAttribute("loggedInError", "error");
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 1);
        }
        return CUSTOMER_LOGIN_FROM;
    }

    @PostMapping("/customer/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        Customer validCustomer = authenticationFacade.authenticate(customer.getCustomerEmail(), customer.getCustomerPassword());
        model.addAttribute("customer", customer);
        if (validCustomer == null) {
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 0);
            return "redirect:/customer/login";
        }
        httpServletRequest.getSession().setAttribute("loggedInCustomer", validCustomer);
        System.out.println(httpServletRequest.getSession().getAttribute("loggedInCustomer"));
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping ("/customer/logout")
    String logoutCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        if(httpServletRequest.getSession().getAttribute("loggedInCustomer") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 1);
        }
        return "redirect:/customer/login";
    }


    @GetMapping("/customer/register")
    String registerCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return CUSTOMER_REGISTRATION_FORM;
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute("customer") Customer customer) {
        ICustomerRegistration customerRegistration = new CustomerRegistrationImpl();
        customerRegistration.registerCustomer(customer);
        return "index.html";
    }

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model,
                     HttpServletRequest request) {
        String rideRequestsAttribute = "rideRequests";
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        System.out.println(customer);
        IRideRequest viewRideRequests = new RideRequestImpl();
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customer.getCustomerId(), rideRequestsDao);
        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

    @GetMapping("/customer/view_recommended_rides")
    String viewRecommendedRides(@RequestParam("rideRequestId") int rideRequestId,
                                @RequestParam("startLocation") String startLocation,
                                @RequestParam("endLocation") String endLocation,
                                Model model) {
        //TODO get customer id from session
        int customerId = 1;
        RideRequest rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
        String recommendedRidesAttribute = "recommendedRides";
        RideFinder rideFinder = new DirectRouteRideFinder();
        //rideFinder = new MultiRouteRideFinderDecorator(rideFinder);
        List<Ride> rideList = rideFinder.findMatchingRides(rideRequest);
        model.addAttribute(recommendedRidesAttribute, rideList);
        model.addAttribute("rideRequestId", rideRequestId);
        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam("rideId") int rideId,
                           @RequestParam("rideRequestId") int rideRequestId) {
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
        rideToRequestMapper.sendRideRequest(rideId, rideRequestId, rideToRequestMapperDao);
        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model,
                                   HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerId(customer.getCustomerId());
        model.addAttribute("rideRequest", rideRequest);
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public String createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest,
                                  HttpServletRequest request ){
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        rideRequest.setCustomerId(customer.customerId);
        IRideRequest rideRequestForCreation = new RideRequestImpl();
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
        rideRequestForCreation.createRideRequest(rideRequest, rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping ("/customer/cancel_ride_request")
    public String cancelRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest, HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("loggedInCustomer");
        rideRequest.setCustomerId(customer.customerId);
        IRideRequest rideRequestObj = new RideRequestImpl();
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
        rideRequestObj.cancelRideRequest(rideRequest, rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

}
