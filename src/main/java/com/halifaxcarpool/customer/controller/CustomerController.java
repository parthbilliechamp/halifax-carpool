package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.recommendation.DirectRouteRideFinder;
import com.halifaxcarpool.customer.business.recommendation.MultiRouteRideFinderDecorator;
import com.halifaxcarpool.customer.business.recommendation.RideFinder;
import com.halifaxcarpool.customer.business.registration.CustomerRegistrationImpl;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
    private static final String VIEW_RECOMMENDED_RIDES = "view_recommended_rides";
    private static final String CUSTOMER_REGISTRATION_FORM = "register_customer_form";

    @GetMapping("/customer/login")
    @ResponseBody
    String login() {
        //#TODO read from user input
        String userName = "";
        String password = "";
        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        boolean isValidCustomer = authenticationFacade.authenticate(userName, password);
        return "";
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
        return VIEW_RECOMMENDED_RIDES;
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
