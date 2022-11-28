package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.IRideToRequestMapper;
import com.halifaxcarpool.commons.business.RideToRequestMapperImpl;
import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.commons.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.halifaxcarpool.driver.business.registration.DriverRegistrationImpl;
import com.halifaxcarpool.driver.business.registration.IDriverRegistration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";
    private static final String DRIVER_REGISTRATION_FORM = "register_driver_form";
    private static final String VIEW_RECEIVED_REQUESTS = "view_received_requests";

    @GetMapping("/driver/register")
    String registerDriver(Model model) {
        model.addAttribute("driver", new Driver());
        return DRIVER_REGISTRATION_FORM;
    }

    @PostMapping("/driver/register/save")
    String saveRegisteredCustomer(@ModelAttribute("driver") Driver driver) {
        IDriverRegistration driverRegistration = new DriverRegistrationImpl();
        driverRegistration.registerDriver(driver);
        return "index.html";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model) {
        String ridesAttribute = "rides";
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new RideImpl();
        List<Ride> rideList = ride.viewRides(1, ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return VIEW_RIDES_UI_FILE;
    }

    @GetMapping("/driver/view_received_requests")
    String viewReceivedRequests(@RequestParam("rideId") int rideId,
                                Model model) {
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
        List<RideRequest> receivedRideRequests =
                rideToRequestMapper.viewReceivedRequest(rideId, rideToRequestMapperDao);
        model.addAttribute("receivedRideRequests", receivedRideRequests);
        return VIEW_RECEIVED_REQUESTS;
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model){
        model.addAttribute("ride", new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public void createNewRide(@ModelAttribute("ride") Ride ride){
        IRide rideModel = new RideImpl();
        rideModel.createNewRide(ride);
        rideModel.insertRideNodes(ride);
    }

}
