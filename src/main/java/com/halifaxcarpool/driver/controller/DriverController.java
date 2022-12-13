package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DriverController {
    DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();
    ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();
    ICommonsFactory commonsObjectFactory = new CommonsFactory();

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

    private static final String DRIVER_VIEW_RIDE_HISTORY = "view_ride_history";

    private static final String DRIVER_VIEW_MY_RIDES = "view_driver_my_rides";

    private static final String VIEW_PAYMENT_DETAILS = "view_driver_payment_status";

    @GetMapping("/driver/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
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



        model.addAttribute(driverLiteral, new Driver());
        if (null != driverAttribute && 1 == (Integer) driverAttribute) {
            model.addAttribute(loggedInErrorLiteral, noErrorLiteral);
        } else if (null != driverAttribute && (Integer) driverAttribute == 0) {
            model.addAttribute(loggedInErrorLiteral, errorLiteral);
            httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, 1);
        }
        return "login_driver_form";

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
                              HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);
        model.addAttribute(driverProfileLiteral, driver);
        return "driver_profile";
    }

    @GetMapping("/driver/update_profile")
    public String updateProfile(Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        model.addAttribute(driverProfileLiteral, new Driver());
        return "driver_profile";
    }

    @PostMapping("/driver/update_profile")
    public String updateDriverProfile(@ModelAttribute(driverProfileLiteral) Driver driverProfile,
                                      HttpServletRequest httpServletRequest) {
        Driver currentDriverProfile = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);
        driverProfile.setDriverId(currentDriverProfile.getDriverId());
        driverProfile.setDriverPassword(currentDriverProfile.getDriverPassword());

        IUserDao userDao = driverDaoFactory.getDriverDao();
        driverProfile.updateUser(userDao);

        httpServletRequest.getSession().setAttribute(loggedInDriverLiteral, driverProfile);
        return "redirect:/driver/view_profile";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        List<Ride> rideList = ride.viewAllRides(driver.getDriverId(), ridesDao);
        model.addAttribute(ridesAttribute, rideList);
        return "view_rides";
    }

    @GetMapping("/driver/cancel_ride")
    String cancelRide(@RequestParam(rideIdLiteral) int rideId, HttpServletRequest httpServletRequest) {
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRide ride = driverModelFactory.getDriverRide();

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        ride.cancelRide(rideId, ridesDao);
        return "view_rides";
    }

    @GetMapping("/driver/view_received_requests")
    String viewReceivedRequests(@RequestParam(rideIdLiteral) int rideId,
                                Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }

        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();

        List<RideRequest> receivedRideRequests =
                rideToRequestMapper.viewReceivedRequest(rideId, rideToRequestMapperDao);

        model.addAttribute(receivedRideRequestsAttribute, receivedRideRequests);
        model.addAttribute("rideId", rideId);
        return "view_received_requests";
    }

    @GetMapping("/driver/create_new_ride")
    public String showNewRideCreation(Model model, HttpServletRequest httpServletRequest) {

        Object driverAttribute = httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);

        if (driverAttribute == null ||
                driverAttribute == (Object) 1) {
            return "redirect:/driver/login";
        }


        model.addAttribute(rideLiteral, new Ride());
        return "create_new_ride";
    }

    @PostMapping("/driver/create_new_ride")
    public String createNewRide(@ModelAttribute(rideLiteral) Ride ride,
                                HttpServletRequest httpServletRequest) {
        Driver driver = (Driver) httpServletRequest.getSession().getAttribute(loggedInDriverLiteral);
        ride.setDriverId(driver.getDriverId());

        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IRideNode rideNode = driverModelFactory.getRideNode();
        IRideNodeDao rideNodeDao = customerDaoFactory.getRideNodeDao();

        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderImpl();

        boolean isRideCreated = ride.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
        if (isRideCreated) {
            return "redirect:/driver/view_rides";
        } else {
            return "create_new_ride";
        }
    }
    @GetMapping("/driver/update_ride_request_status")
    public String updateRequestStatus(@RequestParam("status")String status,
        @RequestParam("rideId") int rideId, @RequestParam("rideRequestId") int rideRequestId){
        if((status.toUpperCase()).equals("ACCEPTED")){
            IPaymentDao paymentDao = new PaymentDaoImpl();
            IRidesDao ridesDao = new RidesDaoImpl();
            IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
            IPayment payment = new Payment();

            IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
            payment.insertPaymentDetails(rideId, rideRequestId, paymentDao, ridesDao,
                    rideRequestsDao, rideToRequestMapperDao);

        }
        IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
        rideToRequestMapperDao.updateRideRequestStatus(rideId, rideRequestId,status);
        return "redirect:/driver/view_rides";
    }
    @GetMapping("/driver/view_ride_history")
    public String getRideHistory(@RequestParam("rideId") int rideId, Model model){
        IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        List<RideRequest> rideRequests = rideToRequestMapper.viewApprovedRequest(rideId, rideToRequestMapperDao);
        model.addAttribute("approvedRequest", rideRequests);
        model.addAttribute("rideId", rideId);

        return DRIVER_VIEW_RIDE_HISTORY;
    }

    @GetMapping("/driver/update_payment_status")
    public String updatePaymentStatus(@RequestParam("paymentId") int paymentId, HttpServletRequest request, RedirectAttributes redirectAttributes){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IPaymentDao paymentDao = new PaymentDaoImpl();
        IPayment payment = new Payment();
        payment.driverUpdatePaymentStatus(paymentId, paymentDao);

        return "redirect:/driver/my_rides";
    }

    @GetMapping("/driver/view_payment_status")
    String viewPaymentStatus(@RequestParam("customerId") int customerId, @RequestParam("rideId") int rideId, HttpServletRequest request,  Model model){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IPaymentDao paymentDao = new PaymentDaoImpl();
        IPayment payment = new Payment();
        Payment paymentDetails= payment.fetchPaymentDetails(customerId,rideId,driver.getDriverId(), paymentDao);
        model.addAttribute("payment",paymentDetails);

        return VIEW_PAYMENT_DETAILS;
    }

    @GetMapping("/driver/my_rides")
    public String viewMyRides(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IRide ride =  new Ride();
        IRidesDao ridesDao = new RidesDaoImpl();
        List<Ride> rides = ride.viewRidesHistory(driver.getDriverId(), ridesDao);
        model.addAttribute("rides", rides);
        return DRIVER_VIEW_MY_RIDES;

    }
    @GetMapping("/driver/start_ride")
    public String changeRideStatusToStart(@RequestParam("rideId") int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = new Ride();
        IRidesDao ridesDao = new RidesDaoImpl();
        ride.startRide(rideId, ridesDao);
        return "redirect:/driver/view_rides";
    }

    @GetMapping("/driver/stop_ride")
    public String changeRideStatusToStop(@RequestParam("rideId") int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = new Ride();
        IRidesDao ridesDao = new RidesDaoImpl();
        ride.stopRide(rideId, ridesDao);
        return "redirect:/driver/my_rides";
    }

}
