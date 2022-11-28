package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.authentication.AuthenticationFacade;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.registration.CustomerRegistrationImpl;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CustomerController {
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
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
        return "redirect:/customer/view_ride_requests";
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
    String viewRides(Model model) {
        String rideRequestsAttribute = "rideRequests";
        IRideRequest viewRideRequests = new RideRequestImpl();
        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(1);
        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model) {
        model.addAttribute("rideRequest", new RideRequest());
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public void createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest){
        IRideRequest rideRequestForCreation = new RideRequestImpl();
        rideRequestForCreation.createRideRequest(rideRequest);
    }

}
