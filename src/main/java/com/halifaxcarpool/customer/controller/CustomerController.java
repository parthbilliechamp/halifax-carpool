package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.authentication.*;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.presentation.UserUI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class CustomerController {

    UserUI userUI = new UserUI();

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

    @GetMapping("/customer/view_ride-requests")
    @ResponseBody
    String viewRides() {
        IRideRequest viewRideRequests = new RideRequestImpl();
        List<RideRequest> rideList = viewRideRequests.viewRideRequests(1);
        return userUI.viewRideRequests();
    }

}
