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
    DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    DriverDaoFactory driverDaoFactory = new DriverDaoMainFactory();
    CustomerDaoFactory customerDaoFactory = new CustomerDaoMainFactory();
    ICommonsObjectFactory commonsObjectFactory = new CommonsObjectFactoryImpl();

    private static final String driverLiteral = "driver";
    private static final String loggedInDriverLiteral = "loggedInDriver";
    private static final String loggedInErrorLiteral = "loggedInError";
    private static final String noErrorLiteral = "noError";
    private static final String errorLiteral = "error";
    private static final String driverThatTriedToRegisterLiteral = "driverThatTriedToRegister";
    private static final String registrationErrorLiteral = "registrationError";
    private static final String driverProfileLiteral = "driverProfile";
    private static final String receivedRideRequestsAttribute = "receivedRideRequests";
    private static final String rideLiteral = "ride";
    private static final String rideIdLiteral = "rideId";

    private static final String ridesAttribute = "rides";

    @GetMapping("/driver/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        model.addAttribute(driverLiteral, new Driver());
        if (null != driverAttribute && 1 == (Integer) driverAttribute) {
            model.addAttribute(loggedInErrorLiteral, noErrorLiteral);
        } else if (null != driverAttribute && (Integer) driverAttribute == 0) {
            model.addAttribute(loggedInErrorLiteral, errorLiteral);
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        }
        return "login_driver_form";
    }

    @PostMapping("/driver/login/check")
    String authenticateLoggedInDriver(@ModelAttribute(driverLiteral) Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        String email = driver.getDriverEmail();
        String password = driver.getDriverPassword();

        User driverUser = driverModelFactory.getDriver();
        IUserAuthentication driverAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao driverAuthenticationDao = driverDaoFactory.getDriverAuthenticationDao();

        User validDriver =
                driverUser.loginUser(email, password, driverAuthentication, driverAuthenticationDao);
        model.addAttribute(driverLiteral, driver);

        if (null == validDriver) {
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 0);
            return "redirect:/driver/login";
        }
        httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, validDriver);
        return "redirect:/driver/create_new_ride";
    }

    @GetMapping("/driver/logout")
    String logoutDriver(@ModelAttribute(driverLiteral) Driver driver, HttpServletRequest
            httpServletRequest) {
        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (null != driverAttribute && (Integer) 0 != driverAttribute) {
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        }
        return "redirect:/";
    }

    @GetMapping("/driver/register")
    String registerDriver(Model model, HttpServletRequest httpServletRequest) {
        User driver = driverModelFactory.getDriver();
        Object registrationSessionErrorAttribute =
                httpServletRequest.getSession().getAttribute(driverThatTriedToRegisterLiteral);

        model.addAttribute(driverLiteral, driver);

        if (null != registrationSessionErrorAttribute) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(driverThatTriedToRegisterLiteral, null);
        }
        return "register_driver_form";
    }

    @PostMapping("/driver/register/save")
    String saveRegisteredCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest httpServletRequest) {
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
                              HttpServletRequest request) {
        Driver driver = (Driver) request.getSession().getAttribute(loggedInDriverLiteral);
        model.addAttribute(driverProfileLiteral, driver);
        return "driver_profile";
    }

    @GetMapping("/driver/update_profile")
    public String updateProfile(Model model) {
        model.addAttribute(driverProfileLiteral, new Driver());
        return "driver_profile";
    }

    @PostMapping("/driver/update_profile")
    public String updateDriverProfile(@ModelAttribute(driverProfileLiteral) Driver driverProfile,
                                      HttpServletRequest request) {
        Driver currentDriverProfile = (Driver) request.getSession().getAttribute(loggedInDriverLiteral);
        driverProfile.setDriverId(currentDriverProfile.getDriverId());
        driverProfile.setDriverPassword(currentDriverProfile.getDriverPassword());

        IUserDao userDao = driverDaoFactory.getDriverDao();
        driverProfile.updateUser(userDao);

        request.getSession().setAttribute(loggedInDriverLiteral, driverProfile);
        return "redirect:/driver/view_profile";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest request) {

        Driver driver = (Driver) request.getSession().getAttribute(loggedInDriverLiteral);

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        List<Ride> rideList = ride.viewRides(driver.getDriverId(), ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return "view_rides";
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam(rideIdLiteral) int rideId) {
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        ride.cancelRide(rideId, ridesDao);
        return "view_rides";
    }

    @GetMapping("/driver/view_received_requests")
    String viewReceivedRequests(@RequestParam(rideIdLiteral) int rideId,
                                Model model) {
        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRidetoRequestMapperDao();

        List<RideRequest> receivedRideRequests =
                rideToRequestMapper.viewReceivedRequest(rideId, rideToRequestMapperDao);

        model.addAttribute(receivedRideRequestsAttribute, receivedRideRequests);
        return "view_received_requests";
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model) {
        model.addAttribute(rideLiteral, new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute(rideLiteral) Ride ride,
                                HttpServletRequest request) {
        Driver driver = (Driver) request.getSession().getAttribute(loggedInDriverLiteral);
        ride.setDriverId(driver.getDriverId());

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRideNode rideNode = driverModelFactory.getRideNode();
        IRideNodeDao rideNodeDao = customerDaoFactory.createRideNodeDao();

        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();

        boolean isRideCreated = ride.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
        if (isRideCreated) {
            return "redirect:/driver/view_rides";
        } else {
            return "create_new_ride";
        }
    }

}
