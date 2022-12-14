package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.customer.business.ICustomerModelFactory;
import com.halifaxcarpool.customer.business.CustomerModelFactory;
import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.IRide;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.business.*;
import com.halifaxcarpool.driver.database.dao.*;
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
    private final IDriverModelFactory driverModelFactory = new DriverModelFactory();
    private final IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();
    private final ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();

    private final ICustomerModelFactory customerObjectFactory = new CustomerModelFactory();
    private final ICommonsFactory commonsObjectFactory = new CommonsFactory();

    private static final String DRIVER = "driver";
    private static final String LOGGED_IN_DRIVER = "loggedInDriver";
    private static final String LOGGED_IN_ERROR = "loggedInError";
    private static final String NO_ERROR = "noError";
    private static final String NOT_REGISTERED = "not registered";
    private static final String NOT_AUTHORIZED = "not authorized";
    private static final String DRIVER_THAT_TRIED_TO_REGISTER = "driverThatTriedToRegister";
    private static final String REGISTRATION_ERROR = "registrationError";
    private static final String DRIVER_PROFILE = "driverProfile";
    private static final String PAYMENT_ID = "paymentId";
    private static final String RECEIVED_RIDE_REQUESTS = "receivedRideRequests";
    private static final String STATUS = "status";
    private static final String RIDE_REQUEST_ID = "rideRequestId";
    private static final String RIDE = "ride";
    private static final String RIDE_ID = "rideId";
    private static final String CUSTOMER_ID = "customerId";
    private static final String PAYMENT = "payment";
    private static final String APPROVED_REQUEST = "approvedRequest";
    private static final String RIDES = "rides";
    private static final String DRIVER_VIEW_MY_RIDES = "view_driver_my_rides";
    private static final String VIEW_PAYMENT_DETAILS = "view_driver_payment_status";

    @GetMapping("/driver/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        model.addAttribute(DRIVER, new Driver());
        if (driverAttribute == (Object) 1) {
            model.addAttribute(LOGGED_IN_ERROR, NO_ERROR);
        } else if (driverAttribute == NOT_REGISTERED) {
            model.addAttribute(LOGGED_IN_ERROR, NOT_REGISTERED);
            httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, 1);
        } else if (driverAttribute == NOT_AUTHORIZED) {
            model.addAttribute(LOGGED_IN_ERROR, NOT_AUTHORIZED);
            httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, 1);
        }

        return "login_driver_form";
    }

    @PostMapping("/driver/login/check")
    String authenticateLoggedInDriver(@ModelAttribute(DRIVER) Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        String email = driver.getDriverEmail();
        String password = driver.getDriverPassword();

        User driverUser = driverModelFactory.getDriver();
        IUserAuthentication driverAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao driverAuthenticationDao = driverDaoFactory.getDriverAuthenticationDao();

        User validDriver =
                driverUser.loginUser(email, password, driverAuthentication, driverAuthenticationDao);
        model.addAttribute(DRIVER, driver);
        String attributeValue;
        if (null == validDriver) {
             attributeValue = "not registered";
            httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, attributeValue);
            return "redirect:/driver/login";
        }

        else if (0 == ((Driver) validDriver).getDriverApprovalStatus() ||
                1 == ((Driver) validDriver).getDriverApprovalStatus()) {
            attributeValue = "not authorized";
            httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER,attributeValue);
            return "redirect:/driver/login";
        }
        httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, validDriver);
        return "redirect:/driver/create_new_ride";
    }

    @GetMapping("/driver/logout")
    String logoutDriver(@ModelAttribute(DRIVER) Driver driver, HttpServletRequest
            httpServletRequest) {
        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (null != driverAttribute && (Integer) 0 != driverAttribute) {
            httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, 1);
        }
        return "redirect:/";
    }

    @GetMapping("/driver/register")
    String registerDriver(Model model, HttpServletRequest httpServletRequest) {
        User driver = driverModelFactory.getDriver();
        Object registrationSessionErrorAttribute =
                httpServletRequest.getSession().getAttribute(DRIVER_THAT_TRIED_TO_REGISTER);

        model.addAttribute(DRIVER, driver);

        if (null != registrationSessionErrorAttribute) {
            model.addAttribute(REGISTRATION_ERROR, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(DRIVER_THAT_TRIED_TO_REGISTER, null);
        }
        return "register_driver_form";
    }

    @PostMapping("/driver/register/save")
    String saveRegisteredCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest httpServletRequest) {
        IUserDao userDao = driverDaoFactory.getDriverDao();
        try {
            driver.registerUser(userDao);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute(DRIVER_THAT_TRIED_TO_REGISTER, e.getMessage());
            return "redirect:/driver/register";
        }
        return "redirect:/driver/login";
    }

    @GetMapping("/driver/view_profile")
    public String viewProfile(Model model,
                              HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);
        model.addAttribute(DRIVER_PROFILE, driver);
        return "driver_profile";
    }

    @GetMapping("/driver/update_profile")
    public String updateProfile(Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        model.addAttribute(DRIVER_PROFILE, new Driver());
        return "driver_profile";
    }

    @PostMapping("/driver/update_profile")
    public String updateDriverProfile(@ModelAttribute(DRIVER_PROFILE) Driver driverProfile,
                                      HttpServletRequest httpServletRequest) {
        Driver currentDriverProfile = (Driver) httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);
        driverProfile.setDriverId(currentDriverProfile.getDriverId());
        driverProfile.setDriverPassword(currentDriverProfile.getDriverPassword());

        IUserDao userDao = driverDaoFactory.getDriverDao();
        driverProfile.updateUser(userDao);

        httpServletRequest.getSession().setAttribute(LOGGED_IN_DRIVER, driverProfile);
        return "redirect:/driver/view_profile";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        List<Ride> rideList = ride.viewAllRides(driver.getDriverId(), ridesDao);
        model.addAttribute(RIDES, rideList);
        return "view_rides";
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam(RIDE_ID) int rideId, HttpServletRequest httpServletRequest) {
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }
        ride.cancelRide(rideId, ridesDao);
        return "view_rides";
    }

    @GetMapping("/driver/view_received_requests")
    String viewReceivedRequests(@RequestParam(RIDE_ID) int rideId,
                                Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();

        List<RideRequest> receivedRideRequests =
                rideToRequestMapper.viewReceivedRequest(rideId, rideToRequestMapperDao);

        model.addAttribute(RECEIVED_RIDE_REQUESTS, receivedRideRequests);
        model.addAttribute(RIDE_ID, rideId);
        return "view_received_requests";
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }


        model.addAttribute(RIDE, new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute(RIDE) Ride ride,
                                HttpServletRequest httpServletRequest) {
        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(LOGGED_IN_DRIVER);
        ride.setDriverId(driver.getDriverId());

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRideNode rideNode = driverModelFactory.getRideNode();
        IRideNodeDao rideNodeDao = customerDaoFactory.getRideNodeDao();

        IDirectionPointsProvider directionPointsProvider = commonsObjectFactory.getDirectionPointsProvider();

        boolean isRideCreated = ride.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
        if (isRideCreated) {
            return "redirect:/driver/view_rides";
        } else {
            return "create_new_ride";
        }
    }
    @GetMapping("/driver/update_ride_request_status")
    public String updateRequestStatus(@RequestParam(STATUS)String status,
                                      @RequestParam(RIDE_ID) int rideId,
                                      @RequestParam(RIDE_REQUEST_ID) int rideRequestId) {

        String acceptedStatus = "ACCEPTED";
        if((status).equalsIgnoreCase(acceptedStatus)){
            IPaymentDao paymentDao = customerDaoFactory.getPaymentDao();
            IRidesDao ridesDao = customerDaoFactory.getRidesDao();
            IRideRequestsDao rideRequestsDao = customerDaoFactory.getRideRequestsDao();
            IPayment payment = customerObjectFactory.getPayment();
            IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();
            payment.insertPaymentDetails(rideId, rideRequestId, paymentDao, ridesDao,
                    rideRequestsDao, rideToRequestMapperDao);

        }
        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();
        rideToRequestMapper.updateRideRequestStatus(rideId, rideRequestId, status,rideToRequestMapperDao);
        return "redirect:/driver/view_rides";
    }
    @GetMapping("/driver/view_ride_history")
    public String getRideHistory(@RequestParam(RIDE_ID) int rideId, Model model){
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();
        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        List<RideRequest> rideRequests = rideToRequestMapper.viewApprovedRequest(rideId, rideToRequestMapperDao);
        model.addAttribute(APPROVED_REQUEST, rideRequests);
        model.addAttribute(RIDE_ID, rideId);

        return "view_ride_history";
    }

    @GetMapping("/driver/update_payment_status")
    public String updatePaymentStatus(@RequestParam(PAYMENT_ID) int paymentId,
                                      HttpServletRequest request){
        if(request.getSession().getAttribute(LOGGED_IN_DRIVER)== null ||
                request.getSession().getAttribute(LOGGED_IN_DRIVER) == (Object)1){
            return "redirect:/";
        }
        IPaymentDao paymentDao = customerDaoFactory.getPaymentDao();
        IPayment payment = customerObjectFactory.getPayment();
        payment.driverUpdatePaymentStatus(paymentId, paymentDao);

        return "redirect:/driver/my_rides";
    }

    @GetMapping("/driver/view_payment_status")
    String viewPaymentStatus(@RequestParam(CUSTOMER_ID) int customerId,
                             @RequestParam(RIDE_ID) int rideId,
                             HttpServletRequest request,  Model model){
        if(request.getSession().getAttribute(LOGGED_IN_DRIVER)== null ||
                request.getSession().getAttribute(LOGGED_IN_DRIVER) == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute(LOGGED_IN_DRIVER);
        IPaymentDao paymentDao = customerDaoFactory.getPaymentDao();
        IPayment payment = customerObjectFactory.getPayment();
        Payment paymentDetails= payment.fetchPaymentDetails(customerId,rideId,driver.getDriverId(), paymentDao);
        model.addAttribute(PAYMENT,paymentDetails);

        return VIEW_PAYMENT_DETAILS;
    }

    @GetMapping("/driver/my_rides")
    public String viewMyRides(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute(LOGGED_IN_DRIVER)== null ||
                request.getSession().getAttribute(LOGGED_IN_DRIVER) == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute(LOGGED_IN_DRIVER);
        IRide ride =  driverModelFactory.getDriverRide();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        List<Ride> rides = ride.viewRidesHistory(driver.getDriverId(), ridesDao);
        model.addAttribute(RIDES, rides);
        return DRIVER_VIEW_MY_RIDES;
    }
    @GetMapping("/driver/start_ride")
    public String changeRideStatusToStart(@RequestParam(RIDE_ID) int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute(LOGGED_IN_DRIVER)== null ||
                request.getSession().getAttribute(LOGGED_IN_DRIVER) == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = driverModelFactory.getDriverRide();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        ride.startRide(rideId, ridesDao);
        return "redirect:/driver/view_rides";
    }

    @GetMapping("/driver/stop_ride")
    public String changeRideStatusToStop(@RequestParam(RIDE_ID) int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute(LOGGED_IN_DRIVER)== null ||
                request.getSession().getAttribute(LOGGED_IN_DRIVER) == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = driverModelFactory.getDriverRide();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        ride.stopRide(rideId, ridesDao);
        return "redirect:/driver/my_rides";
    }

}
