package com.halifaxcarpool.driver.controller;

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
import com.halifaxcarpool.driver.presentation.DriverUI;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";
    private static final String DRIVER_REGISTRATION_FORM = "register_driver_form";

    DriverUI driverUI = new DriverUI();
    @GetMapping("/driver/register")
    String registerCustomer(Model model) {
        IDriverRegistration driverRegistration = new DriverRegistrationImpl();
        Driver driver = new Driver();
        driverRegistration.registerDriver(driver);
        model.addAttribute("driverAttribute", driver);
        return DRIVER_REGISTRATION_FORM;
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

}
