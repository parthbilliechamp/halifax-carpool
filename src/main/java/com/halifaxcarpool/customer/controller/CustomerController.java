package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.admin.business.*;
import com.halifaxcarpool.admin.database.dao.ICouponDao;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.commons.business.CommonsFactory;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.*;
import com.halifaxcarpool.driver.business.*;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.payment.FareCalculatorImpl;
import com.halifaxcarpool.customer.business.payment.IFareCalculator;
import com.halifaxcarpool.customer.business.recommendation.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {
    private static  final String VIEW_PAYMENT_FARE = "view_fare_price";

    private  static  final String CUSTOMER_VIEW_RIDES_PAYMENTS = "view_rides_payments_page";

    private static  final  String CUSTOMER_VIEW_BILL = "view_bill";
    private static final String FARE = "fare";
    private static final String VISITED_RIDES = "visitedRides";
    private static final String PAYMENT_ID = "paymentId";

    private final CommonsFactory commonsObjectFactory = new CommonsFactory();
    private final ICustomerModelFactory customerObjectFactory = new CustomerModelFactory();
    private final ICustomerDaoFactory customerObjectDaoFactory = new CustomerDaoFactory();
    private final IDriverModelFactory driverModelFactory = new DriverModelFactory();
    private final IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();
    private final IAdminModelFactory adminModelFactory = new AdminModelFactory();
    private final IAdminDaoFactory adminDaoFactory = new AdminDaoFactory();

    private static final String CUSTOMER = "customer";
    private static final String ACTIVE_RIDES = "activeRides";
    private static final String LOGGED_IN_CUSTOMER = "loggedInCustomer";
    private static final String LOGGED_IN_ERROR = "loggedInError";
    private static final String CUSTOMER_THAT_TRIED_TO_REGISTER = "customerThatTriedToRegister";
    private static final String REGISTRATION_ERROR = "registrationError";

    private static final String RIDE_REQUEST = "rideRequest";
    private static final String RIDE_REQUESTS = "rideRequests";
    private static final String RIDE_ID = "rideId";
    private static final String RIDE_REQUEST_ID = "rideRequestId";
    private static final String CUSTOMER_PROFILE = "customerProfile";

    private static final String RECOMMENDED_SINGLE_RIDES = "recommendedSingleRides";
    private static final String RECOMMENDED_MULTI_RIDES = "recommendedMultiRides";

    private static final String NO_ERROR = "noError";
    private static final String ERROR = "error";
    private static final String START_LOCATION = "startLocation";
    private static final String END_LOCATION = "endLocation";

    @GetMapping("/customer/login")
    String login(Model model, HttpServletRequest httpServletRequest) {

        Object customerAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        model.addAttribute(CUSTOMER, new Customer());
        if (customerAttribute == (Object) 1) {
            model.addAttribute(LOGGED_IN_ERROR, NO_ERROR);
        } else if (customerAttribute == (Object) 0) {
            model.addAttribute(LOGGED_IN_ERROR, ERROR);
            httpServletRequest.getSession().setAttribute(LOGGED_IN_CUSTOMER, 1);
        }
        return "login_customer_form";
    }

    @PostMapping("/customer/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute(CUSTOMER) Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        String email = customer.getCustomerEmail();
        String password = customer.getCustomerPassword();

        User customerUser = customerObjectFactory.getCustomer();
        IUserAuthentication customerAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao customerAuthenticationDao = customerObjectDaoFactory.getCustomerAuthenticationDao();

        User validCustomer =
                customerUser.loginUser(email, password, customerAuthentication, customerAuthenticationDao);

        model.addAttribute(CUSTOMER, customer);
        if (null == validCustomer) {
            httpServletRequest.getSession().setAttribute(LOGGED_IN_CUSTOMER, 0);
            return "redirect:/customer/login";
        }

        httpServletRequest.getSession().setAttribute(LOGGED_IN_CUSTOMER, validCustomer);
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping("/customer/logout")
    String logoutCustomer(@ModelAttribute(CUSTOMER) Customer customer, HttpServletRequest
            httpServletRequest) {
        httpServletRequest.getSession().setAttribute(LOGGED_IN_CUSTOMER, 1);
        return "redirect:/";
    }
    @GetMapping("/customer/register")
    String registerCustomer(Model model, HttpServletRequest httpServletRequest) {
        User customer = customerObjectFactory.getCustomer();
        Object registrationSessionErrorAttribute =
                httpServletRequest.getSession().getAttribute(CUSTOMER_THAT_TRIED_TO_REGISTER);

        model.addAttribute(CUSTOMER, customer);
        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(REGISTRATION_ERROR, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(CUSTOMER_THAT_TRIED_TO_REGISTER, null);
        }
        return "register_customer_form";
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute(CUSTOMER) Customer customer,
                                  HttpServletRequest httpServletRequest) {
        IUserDao userDao = customerObjectDaoFactory.getCustomerDao();

        try {
            customer.registerUser(userDao);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute(CUSTOMER_THAT_TRIED_TO_REGISTER, e.getMessage());
            return "redirect:/customer/register";
        }
        return "redirect:/customer/login";
    }

    @GetMapping("/customer/view_profile")
    String getCustomerProfile(Model model,
                              HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(LOGGED_IN_CUSTOMER);
        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }
        Customer customer = (Customer) customerAttribute;
        model.addAttribute(CUSTOMER_PROFILE, customer);
        return "update_customer_profile";
    }

    @GetMapping("/customer/update_profile")
    String updateProfile(Model model) {
        model.addAttribute(CUSTOMER_PROFILE, new Customer());
        return "update_customer_profile";
    }

    @PostMapping("/customer/update_profile")
    String updateCustomerProfile(@ModelAttribute(CUSTOMER_PROFILE) Customer customerProfile,
                                 HttpServletRequest request) {
        Customer validCustomer = (Customer) request.getSession().getAttribute(LOGGED_IN_CUSTOMER);
        customerProfile.setCustomerId(validCustomer.getCustomerId());
        customerProfile.setCustomerPassword(validCustomer.getCustomerPassword());

        IUserDao customerDao = customerObjectDaoFactory.getCustomerDao();
        customerProfile.updateUser(customerDao);

        request.getSession().setAttribute(LOGGED_IN_CUSTOMER, customerProfile);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model,
                     HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        IRideRequest viewRideRequests = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();

        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customer.getCustomerId(), rideRequestsDao);

        model.addAttribute(RIDE_REQUESTS, rideRequests);
        return "view_ride_requests";
    }

    @GetMapping("/customer/view_recommended_rides")
    String viewRecommendedRides(@RequestParam(RIDE_REQUEST_ID) int rideRequestId,
                                @RequestParam(START_LOCATION) String startLocation,
                                @RequestParam(END_LOCATION) String endLocation,
                                HttpServletRequest httpServletRequest,
                                Model model) {
        List<List<Ride>> singleRidesList = new ArrayList<>();
        List<List<Ride>> multiRidesList = new ArrayList<>();

        Object customerAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (null == customerAttribute || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;
        RideRequest rideRequest = new RideRequest(rideRequestId, customer.getCustomerId(), startLocation, endLocation);

        BaseRideFinder rideFinder = customerObjectFactory.getDirectRouteRideFinder();
        rideFinder = new MultipleRouteRideFinderDecorator(rideFinder);

        List<List<Ride>> ListOfRideList = rideFinder.findMatchingRides(rideRequest);

        for (List<Ride> rideList : ListOfRideList) {
            if (1 == rideList.size()) {
                singleRidesList.add(rideList);
            } else {
                multiRidesList.add(rideList);
            }
        }

        model.addAttribute(RECOMMENDED_SINGLE_RIDES, singleRidesList);
        model.addAttribute(RECOMMENDED_MULTI_RIDES, multiRidesList);
        model.addAttribute(RIDE_REQUEST_ID, rideRequestId);

        return "view_recommended_rides";
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam(RIDE_ID) int rideId,
                           @RequestParam(RIDE_REQUEST_ID) int rideRequestId,
                           HttpServletRequest httpServletRequest) {
        Object customerAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }
        IRideToRequestMapper rideToRequestMapper = driverModelFactory.getRideToRequestMapper();
        IRideToRequestMapperDao rideToRequestMapperDao = driverDaoFactory.getRideToRequestMapperDao();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IFareCalculator fareCalculator = customerObjectFactory.getFareCalculator();
        IDirectionPointsProvider directionPointsProvider = commonsObjectFactory.getDirectionPointsProvider();
        double fare = fareCalculator.calculateFair(rideId, rideRequestId, rideRequestsDao, ridesDao, directionPointsProvider);
        rideToRequestMapper.sendRideRequest(rideId, rideRequestId, fare, rideToRequestMapperDao);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_ongoing_rides")
    public String viewOngoingRides(HttpServletRequest request,
                                   Model model) {
        Object customerAttribute = request.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        IRide ride = driverModelFactory.getDriverRide();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();

        List<Ride> ongoingRides = ride.viewOngoingRides(customer.getCustomerId(), ridesDao);

        model.addAttribute(ACTIVE_RIDES, ongoingRides);
        return "view_ongoing_rides";
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model,
                                   HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) request.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        IRideRequest rideRequest = customerObjectFactory.getRideRequest();

        RideRequest rideRequestToCreate = (RideRequest) rideRequest;
        rideRequestToCreate.setCustomerId(customer.getCustomerId());

        model.addAttribute(RIDE_REQUEST, rideRequestToCreate);
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public String createRideRequest(@ModelAttribute(RIDE_REQUEST) RideRequest rideRequest,
                                    HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute(LOGGED_IN_CUSTOMER);
        rideRequest.setCustomerId(customer.getCustomerId());
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        rideRequest.createRideRequest(rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping ("/customer/cancel_ride_request")
    public String cancelRideRequest(@ModelAttribute() RideRequest rideRequest,
                                    HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(LOGGED_IN_CUSTOMER);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;
        rideRequest.setCustomerId(customer.getCustomerId());

        IRideRequest rideRequestObj = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();

        rideRequestObj.cancelRideRequest(rideRequest, rideRequestsDao);

        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_payment_fare")
    String viewPaymentFare(@RequestParam(RIDE_ID) int rideId,
                           @RequestParam(RIDE_REQUEST_ID) int rideRequestId, Model model){
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();
        IDirectionPointsProvider directionPointsProvider = commonsObjectFactory.getDirectionPointsProvider();
        IFareCalculator fareCalculator = customerObjectFactory.getFareCalculator();
        double fare =
                fareCalculator.calculateFair(rideId,rideRequestId ,rideRequestsDao, ridesDao, directionPointsProvider);
        model.addAttribute(FARE,fare);
        return VIEW_PAYMENT_FARE;
    }

    @GetMapping("/customer/view_payment_details")
    String viewCustomerRidesPayment(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute(LOGGED_IN_CUSTOMER)== null ||
                request.getSession().getAttribute(LOGGED_IN_CUSTOMER) == (Object)1){
            return "redirect:/customer/login";
        }
        Customer customer = (Customer)request.getSession().getAttribute(LOGGED_IN_CUSTOMER);
        IPayment payment = customerObjectFactory.getPayment();
        IPaymentDao paymentDao = customerObjectDaoFactory.getPaymentDao();
        List<Payment> payments = payment.getCustomerRideHistory(customer.getCustomerId(),paymentDao);
        model.addAttribute(VISITED_RIDES, payments);
        return CUSTOMER_VIEW_RIDES_PAYMENTS;
    }

    @GetMapping("/customer/view_billing")
    String makePayment(@RequestParam(PAYMENT_ID) int paymentId, Model model,
                       HttpServletRequest request){

        if(request.getSession().getAttribute(LOGGED_IN_CUSTOMER)== null ||
                request.getSession().getAttribute(LOGGED_IN_CUSTOMER) == (Object)1){
            return "redirect:/customer/login";
        }

        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        double discountPercentage = coupon.getMaximumDiscountValidToday(couponDao);
        IPayment payment = customerObjectFactory.getPayment();
        IPaymentDao paymentDao = customerObjectDaoFactory.getPaymentDao();
        double originalAmount = payment.getAmountDue(paymentId, paymentDao);
        FareCalculatorImpl fareCalculator =
                customerObjectFactory.getFareCalculatorWithParameters(originalAmount, discountPercentage);
        fareCalculator.calculateFinalAmount();
        model.addAttribute(FARE,fareCalculator);
        model.addAttribute(PAYMENT_ID,paymentId);
        return CUSTOMER_VIEW_BILL;
    }

    @GetMapping("/customer/payment_status_success")
    String changePaymentStatus(@RequestParam("paymentId") int paymentId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInCustomer")== null || request.getSession().getAttribute("loggedInCustomer") == (Object)1){
            return "redirect:/customer/login";
        }
        IPaymentDao paymentDao = customerObjectDaoFactory.getPaymentDao();
        IPayment payment = customerObjectFactory.getPayment();
        payment.updatePaymentStatusToSuccess(paymentId, paymentDao);
        return "redirect:/customer/view_payment_details";
    }


}
