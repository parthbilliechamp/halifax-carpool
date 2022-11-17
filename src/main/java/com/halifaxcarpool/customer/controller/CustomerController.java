package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.CustomerConstants;
import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.IRideRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class CustomerController {
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";

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

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model) {
        String rideRequestsAttribute = "rideRequests";
        IRideRequest viewRideRequests = new RideRequestImpl();
        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(1);
        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

}
