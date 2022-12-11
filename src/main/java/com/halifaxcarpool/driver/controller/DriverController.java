package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.RideNodeDaoImpl;
import com.halifaxcarpool.driver.business.*;
import com.halifaxcarpool.driver.database.dao.*;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";
    private static final String DRIVER_REGISTRATION_FORM = "register_driver_form";
    private static final String VIEW_RECEIVED_REQUESTS = "view_received_requests";
    private static final String DRIVER_LOGIN_FROM = "login_driver_form";
    private static final String DRIVER_PROFILE = "driver_profile";
    private static final String CREATE_NEW_RIDE_PAGE = "create_new_ride";

    DriverModelFactory driverBusinessObjectFactory = new DriverModelMainFactory();
    DriverDaoFactory driverDaoObjectFactory = new DriverDaoMainFactory();

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
    String authenticateLoggedInDriver(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest, Model model) {

        String email = driver.getDriverEmail();
        String password = driver.getDriverPassword();

        User driverUser = new Driver();
        IUserAuthentication driverAuthentication = new UserAuthenticationImpl();
        IUserAuthenticationDao driverAuthenticationDao = new DriverAuthenticationDaoImpl();

        User validDriver =
                driverUser.loginUser(email, password, driverAuthentication, driverAuthenticationDao);
        model.addAttribute("driver", driver);

        if (null == validDriver) {
            httpServletRequest.getSession().setAttribute("loggedInDriver", 0);
            return "redirect:/driver/login";
        }
        httpServletRequest.getSession().setAttribute("loggedInDriver", validDriver);
        return "redirect:/driver/create_new_ride";
    }

    @GetMapping ("/driver/logout")
    String logoutDriver(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute("loggedInDriver") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInDriver", 1);
        }
        return "redirect:/";
    }

    @GetMapping("/driver/register")
    String registerDriver(Model model, HttpServletRequest httpServletRequest) {
        String driverLiteral = "driver";
        String driverThatTriedToRegisterLiteral = "driverThatTriedToRegister";
        String registrationErrorLiteral = "registrationError";

        Driver driver = new Driver();
        Object registrationSessionErrorAttribute = httpServletRequest.getSession().getAttribute(driverThatTriedToRegisterLiteral);

        model.addAttribute(driverLiteral, driver);

        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(driverThatTriedToRegisterLiteral, null);
        }
        return DRIVER_REGISTRATION_FORM;
    }

    @PostMapping("/driver/register/save")
    String saveRegisteredDriver(@ModelAttribute("driver") Driver driver) {
        IUserDao userDao = new DriverDaoImpl();
        driver.registerUser(userDao);
        return "redirect:/driver/login";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest request) {
        String ridesAttribute = "rides";
        Driver driver = (Driver) request.getSession().getAttribute("loggedInDriver");
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new Ride();
        List<Ride> rideList = ride.viewRides(driver.getDriverId(), ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return VIEW_RIDES_UI_FILE;
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam("rideId") int rideId) {
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new Ride();
        ride.cancelRide(rideId, ridesDao);
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
    public String showNewRideCreation(Model model) {
        model.addAttribute("ride", new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute("ride") Ride ride,
                              HttpServletRequest request) {
        Driver driver = (Driver) request.getSession().getAttribute("loggedInDriver");
        ride.setDriverId(driver.getDriverId());
        IRidesDao ridesDao = new RidesDaoImpl();
        IRideNode rideNode = new RideNodeImpl();
        IRideNodeDao rideNodeDao = new RideNodeDaoImpl();
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();
        boolean isRideCreated = ride.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
        if (isRideCreated) {
            return "redirect:/driver/view_rides";
        } else {
            return CREATE_NEW_RIDE_PAGE;
        }
    }

    @GetMapping("/driver/view_profile")
    public String viewProfile(Model model,
                              HttpServletRequest request) {
        Driver driverProfile = (Driver) request.getSession().getAttribute("loggedInDriver");
        model.addAttribute("driverProfile", driverProfile);
        return DRIVER_PROFILE;
    }

    @GetMapping("/driver/update_profile")
    public String updateProfile(Model model) {
        model.addAttribute("driverProfile", new Driver());
        return DRIVER_PROFILE;
    }

    @PostMapping("/driver/update_profile")
    public String updateDriverProfile(@ModelAttribute("driverProfile") Driver driverProfile,
                                      HttpServletRequest request) {
        Driver currentDriverProfile = (Driver) request.getSession().getAttribute("loggedInDriver");
        driverProfile.setDriverId(currentDriverProfile.getDriverId());
        driverProfile.setDriverPassword(currentDriverProfile.getDriverPassword());
        IUserDao userDao = new DriverDaoImpl();
        driverProfile.updateUser(userDao);
        request.getSession().setAttribute("loggedInDriver", driverProfile);
        return "redirect:/driver/view_profile";
    }

}
