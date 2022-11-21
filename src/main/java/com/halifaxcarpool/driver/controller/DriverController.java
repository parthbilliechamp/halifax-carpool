package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.business.registration.DriverRegistrationImpl;
import com.halifaxcarpool.driver.business.registration.IDriverRegistration;
import com.halifaxcarpool.driver.presentation.DriverUI;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriverController {

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
    @ResponseBody
    String viewRides() {
        IRide viewRides = new RideImpl();
        List<Ride> rideList = viewRides.getAllRides();
        return driverUI.displayRides(rideList);
    }

}
