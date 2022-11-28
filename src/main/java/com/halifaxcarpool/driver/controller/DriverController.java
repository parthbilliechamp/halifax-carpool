package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.driver.business.RideImpl;
import com.halifaxcarpool.driver.business.authentication.AuthenticationFacade;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.halifaxcarpool.driver.business.registration.DriverRegistrationImpl;
import com.halifaxcarpool.driver.business.registration.IDriverRegistration;
import com.halifaxcarpool.driver.presentation.DriverUI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";
    private static final String DRIVER_REGISTRATION_FORM = "register_driver_form";

    private static final String DRIVER_LOGIN_FROM = "login_driver_form";

    DriverUI driverUI = new DriverUI();

    @GetMapping("/driver/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("driver", new Driver());
        if (httpServletRequest.getSession().getAttribute("loggedInDriver") == (Object) 1) {
            model.addAttribute("loggedInError", "noError");
        } else if (httpServletRequest.getSession().getAttribute("loggedInDriver") == (Object) 0) {
            model.addAttribute("loggedInError", "error");
            httpServletRequest.getSession().setAttribute("loggedInDriver", 1);
        }
        return DRIVER_LOGIN_FROM;
    }

    @PostMapping("/driver/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        Driver validDriver = authenticationFacade.authenticate(driver.getDriver_email(), driver.getDriver_password());
        model.addAttribute("driver", driver);
        if (validDriver == null) {
            httpServletRequest.getSession().setAttribute("loggedInDriver", 0);
            return "redirect:/driver/login";
        }
        httpServletRequest.getSession().setAttribute("loggedInDriver", validDriver);
        System.out.println(httpServletRequest.getSession().getAttribute("loggedInDriver"));
        return "redirect:/driver/view_rides";
    }

    @GetMapping ("/driver/logout")
    String logoutCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        if(httpServletRequest.getSession().getAttribute("loggedInDriver") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInDriver", 1);
        }
        return "redirect:/driver/login";
    }

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

}
