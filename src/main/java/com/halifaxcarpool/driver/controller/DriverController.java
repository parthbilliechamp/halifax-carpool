package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.RideRequestImpl;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";

    @GetMapping("/driver/view_rides")
    String viewRides(Model model) {
        String ridesAttribute = "rides";
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new RideImpl();
        List<Ride> rideList = ride.viewRides(1, ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return VIEW_RIDES_UI_FILE;
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model){
        model.addAttribute("ride", new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public void createNewRide(@ModelAttribute("ride") Ride ride){
        IRide newRideCreation = new RideImpl();
        System.out.println("Heyyy");
        System.out.println(ride.getStartLocation());
        System.out.println(ride.getRideId());
        System.out.println(ride.getDateTime());
        newRideCreation.createNewRide(ride);
    }

}
