package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.CommonsObjectFactoryImpl;
import com.halifaxcarpool.commons.business.ICommonsObjectFactory;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoMainFactory;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
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

    DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    DriverDaoFactory driverDaoFactory = new DriverDaoMainFactory();
    CustomerDaoFactory customerDaoFactory = new CustomerDaoMainFactory();
    ICommonsObjectFactory commonsObjectFactory = new CommonsObjectFactoryImpl();

    @GetMapping("/driver/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        String driverLiteral = "driver";
        String loggedInDriverLiteral = "loggedInDriver";
        String loggedInErrorLiteral = "loggedInError";
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        model.addAttribute(driverLiteral, new Driver());
        if (driverAttribute == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, "noError");
        } else if (driverAttribute == (Object) "not registered") {
            model.addAttribute(loggedInErrorLiteral, "not registered");
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        } else if (driverAttribute == (Object) "not authorized") {
            model.addAttribute(loggedInErrorLiteral, "not authorized");
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        }
        return DRIVER_LOGIN_FROM;
    }

    @PostMapping("/driver/login/check")
    String authenticateLoggedInDriver(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        String driverLiteral = "driver";
        String loggedInDriverLiteral = "loggedInDriver";
        String notApprovedDriverLiteral = "notApprovedDriver";

        String email = driver.getDriverEmail();
        String password = driver.getDriverPassword();

        User driverUser = driverModelFactory.getDriver();
        IUserAuthentication driverAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao driverAuthenticationDao = driverDaoFactory.getDriverAuthenticationDao();

        User validDriver =
                driverUser.loginUser(email, password, driverAuthentication, driverAuthenticationDao);
        model.addAttribute(driverLiteral, driver);

        if (null == validDriver) {
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, "not registered");
            return "redirect:/driver/login";
        }

        else if (0 == ((Driver) validDriver).getDriverApprovalStatus() || 1 == ((Driver) validDriver).getDriverApprovalStatus()) {
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral,"not authorized");
            return "redirect:/driver/login";
        }
        httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, validDriver);
        return "redirect:/driver/create_new_ride";
    }

    @GetMapping("/driver/logout")
    String logoutDriver(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest) {
        String loggedInDriverLiteral = "loggedInDriver";
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute != (Object) 0) {
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        }
        return "redirect:/";
    }

    @GetMapping("/driver/register")
    String registerDriver(Model model, HttpServletRequest httpServletRequest) {
        String driverLiteral = "driver";
        String driverThatTriedToRegisterLiteral = "driverThatTriedToRegister";
        String registrationErrorLiteral = "registrationError";

        User driver = driverModelFactory.getDriver();
        Object registrationSessionErrorAttribute = httpServletRequest.getSession().getAttribute(driverThatTriedToRegisterLiteral);

        model.addAttribute(driverLiteral, driver);

        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(driverThatTriedToRegisterLiteral, null);
        }
        return DRIVER_REGISTRATION_FORM;
    }

    @PostMapping("/driver/register/save")
    String saveRegisteredCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest httpServletRequest) {
        String driverThatTriedToRegisterLiteral = "driverThatTriedToRegister";

        IUserDao userDao = driverDaoFactory.getDriverDao();

        try {
            driver.registerUser(userDao);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute(driverThatTriedToRegisterLiteral, e.getMessage());
            return "redirect:/driver/register";
        }

        return "redirect:/driver/login";
    }

    @GetMapping("/driver/view_profile")
    public String viewProfile(Model model,
                              HttpServletRequest httpServletRequest) {
        String driverProfileLiteral = "driverProfile";
        String loggedInDriverLiteral = "loggedInDriver";
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        model.addAttribute(driverProfileLiteral, driver);
        return DRIVER_PROFILE;
    }

    @GetMapping("/driver/update_profile")
    public String updateProfile(Model model, HttpServletRequest httpServletRequest) {
        String driverProfileLiteral = "driverProfile";
        String loggedInDriverLiteral = "loggedInDriver";

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        model.addAttribute(driverProfileLiteral, new Driver());
        return DRIVER_PROFILE;
    }

    @PostMapping("/driver/update_profile")
    public String updateDriverProfile(@ModelAttribute("driverProfile") Driver driverProfile,
                                      HttpServletRequest httpServletRequest) {
        String loggedInDriverLiteral = "loggedInDriver";

        Driver currentDriverProfile = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);
        driverProfile.setDriverId(currentDriverProfile.getDriverId());
        driverProfile.setDriverPassword(currentDriverProfile.getDriverPassword());

        IUserDao userDao = driverDaoFactory.getDriverDao();
        driverProfile.updateUser(userDao);

        httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, driverProfile);
        return "redirect:/driver/view_rides";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest httpServletRequest) {
        String ridesAttribute = "rides";
        String loggedInDriverLiteral = "loggedInDriver";

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        List<Ride> rideList = ride.viewRides(driver.getDriverId(), ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return VIEW_RIDES_UI_FILE;
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam("rideId") int rideId, HttpServletRequest httpServletRequest) {

        String loggedInDriverLiteral = "loggedInDriver";
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        ride.cancelRide(rideId, ridesDao);
        return "redirect:/driver/view_rides";
    }

    @GetMapping("/driver/view_received_requests")
    String viewReceivedRequests(@RequestParam("rideId") int rideId,
                                Model model, HttpServletRequest httpServletRequest) {
        String receivedRideRequestsAttribute = "receivedRideRequests";
        String loggedInDriverLiteral = "loggedInDriver";

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRidetoRequestMapperDao();

        List<RideRequest> receivedRideRequests =
                rideToRequestMapper.viewReceivedRequest(rideId, rideToRequestMapperDao);

        model.addAttribute(receivedRideRequestsAttribute, receivedRideRequests);
        return VIEW_RECEIVED_REQUESTS;
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model, HttpServletRequest httpServletRequest) {
        String rideLiteral = "ride";
        String loggedInDriverLiteral = "loggedInDriver";

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        model.addAttribute(rideLiteral, new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute("ride") Ride ride,
                                HttpServletRequest httpServletRequest) {
        String loggedInDriverLiteral = "loggedInDriver";

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);
        ride.setDriverId(driver.getDriverId());

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRideNode rideNode = driverModelFactory.getRideNode();
        IRideNodeDao rideNodeDao = customerDaoFactory.createRideNodeDao();

        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();

        boolean isRideCreated = ride.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
        if (isRideCreated) {
            return "redirect:/driver/view_rides";
        } else {
            return CREATE_NEW_RIDE_PAGE;
        }
    }
}
