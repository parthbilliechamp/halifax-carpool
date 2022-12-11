package com.halifaxcarpool.driver.controller;

import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.business.payment.PaymentImpl;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideNodeImpl;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.driver.business.beans.RideToRequestMapper;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.customer.business.beans.RideRequest;
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
import java.util.Locale;

@Controller
public class DriverController {

    private static final String VIEW_RIDES_UI_FILE = "view_rides";
    private static final String DRIVER_REGISTRATION_FORM = "register_driver_form";
    private static final String VIEW_RECEIVED_REQUESTS = "view_received_requests";

    private static final String DRIVER_LOGIN_FROM = "login_driver_form";

    private static final String DRIVER_VIEW_RIDE_HISTORY = "view_ride_history";

    private static final String DRIVER_VIEW_MY_RIDES = "view_driver_my_rides";

    private static final String VIEW_PAYMENT_DETAILS = "view_driver_payment_status";

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
        return "redirect:/driver/create_new_ride";
    }

    @GetMapping ("/driver/logout")
    String logoutCustomer(@ModelAttribute("driver") Driver driver, HttpServletRequest
            httpServletRequest, Model model) {
        if(httpServletRequest.getSession().getAttribute("loggedInDriver") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInDriver", 1);
        }
        return "redirect:/";
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
        return "redirect:/driver/login";
    }

    @GetMapping("/driver/view_rides")
    String viewRides(Model model,
                     HttpServletRequest request) {
        String ridesAttribute = "rides";
        Driver driver = (Driver) request.getSession().getAttribute("loggedInDriver");
        IRidesDao ridesDao = new RidesDaoImpl();
        IRide ride = new RideImpl();
        List<Ride> rideList = ride.viewRides(7, ridesDao);
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
        model.addAttribute("rideId", rideId);
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
    @GetMapping("/driver/update_ride_request_status")
    public String updateRequestStatus(@RequestParam("status")String status,
        @RequestParam("rideId") int rideId, @RequestParam("rideRequestId") int rideRequestId){
        if((status.toUpperCase()).equals("ACCEPTED")){
            //customer module payment table
            IPaymentDao paymentDao = new PaymentDaoImpl();
            IRidesDao ridesDao = new RidesDaoImpl();
            IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
            IPayment payment = new PaymentImpl();

            IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
            payment.insertPaymentDetails(rideId, rideRequestId, paymentDao, ridesDao,
                    rideRequestsDao, rideToRequestMapperDao);

        }
        //ride to request mapping  status change.
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
    public String updatePaymentStatus(@RequestParam("paymentId") int paymentId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IPaymentDao paymentDao = new PaymentDaoImpl();
        IPayment payment = new PaymentImpl();
        payment.driverUpdatePaymentStatus(paymentId, paymentDao);
        return "redirect: /driver/view_ride_history";
    }

    @GetMapping("/driver/view_payment_status")
    String viewPaymentStatus(@RequestParam("customerId") int customerId, @RequestParam("rideId") int rideId, HttpServletRequest request,  Model model){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IPaymentDao paymentDao = new PaymentDaoImpl();
        IPayment payment = new PaymentImpl();
        Payment paymentDetails= payment.fetchPaymentDetails(customerId,rideId,7, paymentDao);
        model.addAttribute("Payment",paymentDetails);
        return VIEW_PAYMENT_DETAILS;
    }

    @GetMapping("/driver/my_rides")
    public String viewMyRides(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        Driver driver = (Driver)request.getSession().getAttribute("loggedInDriver");
        IRide ride =  new RideImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        List<Ride> rides = ride.viewRides(driver.driver_id, ridesDao);
        model.addAttribute("rides", rides);
        return DRIVER_VIEW_MY_RIDES;

    }
    @GetMapping("/driver/start_ride")
    public String changeRideStatusToStart(@RequestParam("rideId") int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = new RideImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        ride.startRide(rideId, ridesDao);
        return "redirect: /driver/my_rides";
    }

    @GetMapping("/driver/stop_ride")
    public String changeRideStatusToStop(@RequestParam("rideId") int rideId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInDriver")== null || request.getSession().getAttribute("loggedInDriver") == (Object)1){
            return "redirect:/driver/login";
        }
        IRide ride = new RideImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        ride.stopRide(rideId, ridesDao);
        return "redirect: /driver/my_rides";
    }
}
