package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.IRideNode;
import com.halifaxcarpool.commons.business.IRideToRequestMapper;
import com.halifaxcarpool.commons.business.RideNodeImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.commons.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.commons.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.RideNodeDaoImpl;
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
        return "redirect:/driver/create_new_ride";
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
    String viewRides(Model model,
                     HttpServletRequest request) {
        String ridesAttribute = "rides";
        Driver driver = (Driver) request.getSession().getAttribute("loggedInDriver");
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new RideImpl();
        List<Ride> rideList = ride.viewRides(driver.driver_id, ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return VIEW_RIDES_UI_FILE;
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam("rideId") int rideId) {
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new RideImpl();
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
    public String showNewRideCreation(Model model){
        model.addAttribute("ride", new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute("ride") Ride ride,
                              HttpServletRequest request) {
        Driver driver = (Driver) request.getSession().getAttribute("loggedInDriver");
        ride.setDriverId(driver.driver_id);
        IRide rideModel = new RideImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        boolean isRideCreated = rideModel.createNewRide(ride, ridesDao);
        if (Boolean.FALSE.equals(isRideCreated)) {
            return "create_new_ride";
            //TODO add error message to model and display to user
        }
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();
        IRideNode rideNode = new RideNodeImpl();
        IRideNodeDao rideNodeDao = new RideNodeDaoImpl();
        boolean isRideNodeInserted = rideNode.insertRideNodes(ride, rideNodeDao, directionPointsProvider);
        if (Boolean.FALSE.equals(isRideNodeInserted)) {
            return "create_new_ride";
            //TODO cancel the latest ride
        }
        return "redirect:/driver/view_rides";
    }

}
