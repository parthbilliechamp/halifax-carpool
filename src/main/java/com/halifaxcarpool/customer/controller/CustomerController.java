package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.authentication.*;
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

    @GetMapping("/customer/login")
    String login(@ModelAttribute("customer") Customer customer,
                 HttpServletRequest httpServletRequest) {
        customer.setCustomerId(1);
        httpServletRequest.getSession().setAttribute("customer_id", customer.getCustomerId());
        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        boolean isValidCustomer =
                authenticationFacade.authenticate(customer.getCustomerName(), customer.getCustomerPassword());
        return "redirect:/customer_view_ride_requests";
    }

    @GetMapping("/customer/register")
    String registerCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return CUSTOMER_REGISTRATION_FORM;
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute("customer") Customer customer,
                                  HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("customer_id", customer.customerId);
        ICustomerRegistration customerRegistration = new CustomerRegistrationImpl();
        customerRegistration.registerCustomer(customer);
        return "customer_view_ride_requests";
    }

    @GetMapping("/customer_view_ride_requests")
    String viewRides(Model model, HttpServletRequest httpServletRequest) {
        String rideRequestsAttribute = "rideRequests";
        int customerId = (int) httpServletRequest.getSession().getAttribute("customer_id");
        IRideRequest viewRideRequests = new RideRequestImpl();
        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customerId);
        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model){
        model.addAttribute("rideRequest", new RideRequest());
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public void createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest){
        IRideRequest rideRequestForCreation = new RideRequestImpl();
        rideRequestForCreation.createRideRequest(rideRequest);
    }

}
