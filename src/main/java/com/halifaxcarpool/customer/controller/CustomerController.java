package com.halifaxcarpool.customer.controller;

import com.halifaxcarpool.admin.business.ICoupon;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.dao.ICouponDao;
import com.halifaxcarpool.admin.database.dao.dao.CouponDaoImpl;
import com.halifaxcarpool.customer.business.beans.Payment;
import com.halifaxcarpool.customer.business.payment.IPayment;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.PaymentDaoImpl;
import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;
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
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoImpl;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

    //TODO unused variable
    private static final String INDEX_PAGE = "index";
    private static final String VIEW_RIDE_REQUESTS = "view_ride_requests";
    private static final String VIEW_RECOMMENDED_RIDES = "view_recommended_rides";
    private static final String CUSTOMER_REGISTRATION_FORM = "register_customer_form";
    private static final String CUSTOMER_LOGIN_FROM = "login_customer_form";
    private static  final String VIEW_PAYMENT_FARE = "view_fare_price";

    private  static  final String CUSTOMER_VIEW_RIDES_PAYMENTS = "view_rides_payments_page";

    private static  final  String CUSTOMER_VIEW_BILL = "view_bill";

    private RideFinderFacade rideFinderFacade;
    private static final String CUSTOMER_PROFILE_FORM = "update_customer_profile";

    private final CommonsFactory commonsObjectFactory = new CommonsFactory();
    private final CustomerModelFactory customerObjectFactory = new CustomerModelMainFactory();
    private final ICustomerDaoFactory customerObjectDaoFactory = new CustomerDaoFactory();
    private final DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    private final IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();

    private static final String customerLiteral = "customer";
    private static final String activeRidesLiteral = "activeRides";
    private static final String loggedInCustomerLiteral = "loggedInCustomer";
    private static final String loggedInErrorLiteral = "loggedInError";
    private static final String customerThatTriedToRegisterLiteral = "customerThatTriedToRegister";
    private static final String registrationErrorLiteral = "registrationError";

    private static final String rideRequestLiteral = "rideRequest";
    private static final String rideRequestsAttribute = "rideRequests";
    private static final String rideIdLiteral = "rideId";
    private static final String rideRequestIdLiteral = "rideRequestId";
    private static final String customerProfileLiteral = "customerProfile";

    private static final String recommendedSingleRidesAttribute = "recommendedSingleRides";
    private static final String recommendedMultiRidesAttribute = "recommendedMultiRides";

    private static final String noErrorLiteral = "noError";
    private static final String errorLiteral = "error";
    private static final String startLocationLiteral = "startLocation";
    private static final String endLocationLiteral = "endLocation";

    @GetMapping("/customer/login")
    String login(Model model, HttpServletRequest httpServletRequest) {

        Object customerAttribute = httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

        model.addAttribute(customerLiteral, new Customer());
        if (customerAttribute == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, noErrorLiteral);
        } else if (customerAttribute == (Object) 0) {
            model.addAttribute(loggedInErrorLiteral, errorLiteral);
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 1);
        }
        return "login_customer_form";
    }

    @PostMapping("/customer/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute(customerLiteral) Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        String email = customer.getCustomerEmail();
        String password = customer.getCustomerPassword();

        User customerUser = customerObjectFactory.getCustomer();
        IUserAuthentication customerAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao customerAuthenticationDao = customerObjectDaoFactory.getCustomerAuthenticationDao();

        User validCustomer =
                customerUser.loginUser(email, password, customerAuthentication, customerAuthenticationDao);

        model.addAttribute(customerLiteral, customer);
        if (null == validCustomer) {
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 0);
            return "redirect:/customer/login";
        }

        httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, validCustomer);
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping("/customer/logout")
    String logoutCustomer(@ModelAttribute(customerLiteral) Customer customer, HttpServletRequest
            httpServletRequest) {
        Customer customerUser = (Customer) httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerUser != (Object) 0) {
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 1);
        }
        return "redirect:/";
    }
    @GetMapping("/customer/register")
    String registerCustomer(Model model, HttpServletRequest httpServletRequest) {
        User customer = customerObjectFactory.getCustomer();
        Object registrationSessionErrorAttribute =
                httpServletRequest.getSession().getAttribute(customerThatTriedToRegisterLiteral);

        model.addAttribute(customerLiteral, customer);
        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(customerThatTriedToRegisterLiteral, null);
        }
        return "register_customer_form";
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute(customerLiteral) Customer customer,
                                  HttpServletRequest httpServletRequest) {
        IUserDao userDao = customerObjectDaoFactory.getCustomerDao();

        try {
            customer.registerUser(userDao);
        } catch (Exception e) {
            httpServletRequest.getSession().setAttribute(customerThatTriedToRegisterLiteral, e.getMessage());
            return "redirect:/customer/register";
        }
        return "redirect:/customer/login";
    }

    @GetMapping("/customer/view_profile")
    String getCustomerProfile(Model model,
                              HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);
        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }
        Customer customer = (Customer) customerAttribute;
        model.addAttribute(customerProfileLiteral, customer);
        return "update_customer_profile";
    }

    @GetMapping("/customer/update_profile")
    String updateProfile(Model model) {
        model.addAttribute(customerProfileLiteral, new Customer());
        return "update_customer_profile";
    }

    @PostMapping("/customer/update_profile")
    String updateCustomerProfile(@ModelAttribute(customerProfileLiteral) Customer customerProfile,
                                 HttpServletRequest request) {
        Customer validCustomer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        customerProfile.setCustomerId(validCustomer.getCustomerId());
        customerProfile.setCustomerPassword(validCustomer.getCustomerPassword());

        IUserDao customerDao = customerObjectDaoFactory.getCustomerDao();
        customerProfile.updateUser(customerDao);

        request.getSession().setAttribute(loggedInCustomerLiteral, customerProfile);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_ride_requests")
    String viewRides(Model model,
                     HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        IRideRequest viewRideRequests = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();

        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customer.getCustomerId(), rideRequestsDao);

        model.addAttribute(rideRequestsAttribute, rideRequests);
        return "view_ride_requests";
    }

    @GetMapping("/customer/view_recommended_rides")
    String viewRecommendedRides(@RequestParam(rideRequestIdLiteral) int rideRequestId,
                                @RequestParam(startLocationLiteral) String startLocation,
                                @RequestParam(endLocationLiteral) String endLocation,
                                HttpServletRequest httpServletRequest,
                                Model model) {
        List<List<Ride>> singleRidesList = new ArrayList<>();
        List<List<Ride>> multiRidesList = new ArrayList<>();

        Object customerAttribute = httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

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

        model.addAttribute(recommendedSingleRidesAttribute, singleRidesList);
        model.addAttribute(recommendedMultiRidesAttribute, multiRidesList);
        model.addAttribute(rideRequestIdLiteral, rideRequestId);

        return "view_recommended_rides";
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam(rideIdLiteral) int rideId,
                           @RequestParam(rideRequestIdLiteral) int rideRequestId,
                           HttpServletRequest httpServletRequest) {
        Object customerAttribute = httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoImpl();
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        IFareCalculator fareCalculator = new FareCalculatorImpl();
        double fare = fareCalculator.calculateFair(rideId, rideRequestsDao, ridesDao);
        rideToRequestMapper.sendRideRequest(rideId, rideRequestId, fare, rideToRequestMapperDao);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_ongoing_rides")
    public String viewOngoingRides(HttpServletRequest request,
                                   Model model) {
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        IRide ride = driverModelFactory.getDriverRide();
        IRidesDao ridesDao = driverDaoFactory.getDriverRidesDao();

        List<Ride> ongoingRides = ride.viewOngoingRides(customer.getCustomerId(), ridesDao);

        model.addAttribute(activeRidesLiteral, ongoingRides);
        return "view_ongoing_rides";
    }

    @GetMapping("/customer/create_ride_request")
    public String showRideCreation(Model model,
                                   HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);

        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomerId(customer.getCustomerId());

        model.addAttribute(rideRequestLiteral, rideRequest);
        return "create_ride_request";
    }

    @PostMapping("/customer/create_ride_request")
    public String createRideRequest(@ModelAttribute(rideRequestLiteral) RideRequest rideRequest,
                                    HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);
        rideRequest.setCustomerId(customer.getCustomerId());
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();
        rideRequest.createRideRequest(rideRequestsDao);
        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping ("/customer/cancel_ride_request")
    public String cancelRideRequest(@ModelAttribute() RideRequest rideRequest,
                                    HttpServletRequest request) {
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;
        rideRequest.setCustomerId(customer.customerId);

        IRideRequest rideRequestObj = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.getRideRequestsDao();

        rideRequestObj.cancelRideRequest(rideRequest, rideRequestsDao);

        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping("/customer/view_payment_fare")
    String viewPaymentFare(@RequestParam("rideId")int rideId, @RequestParam("rideRequestId")int rideRequestId, Model model){
        //payment
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoImpl();
        IRidesDao ridesDao = new RidesDaoImpl();
        IFareCalculator fareCalculator = new FareCalculatorImpl();
        double fare = fareCalculator.calculateFair(rideId, rideRequestsDao, ridesDao);
        model.addAttribute("fare",fareCalculator);
        return VIEW_PAYMENT_FARE;
    }

    @GetMapping("/customer/view_payment_details")
    String viewCustomerRidesPayment(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInCustomer")== null || request.getSession().getAttribute("loggedInCustomer") == (Object)1){
            return "redirect:/customer/login";
        }
        Customer customer = (Customer)request.getSession().getAttribute("loggedInCustomer");
        IPayment payment = new Payment();
        IPaymentDao paymentDao = new PaymentDaoImpl();
        List<Payment> payments = payment.getCustomerRideHistory(customer.getCustomerId(),paymentDao);
        model.addAttribute("visitedRides", payments);
        return CUSTOMER_VIEW_RIDES_PAYMENTS;
    }

    @GetMapping("/customer/view_billing")
    String makePayment(@RequestParam("paymentId") int paymentId, Model model, HttpServletRequest request){

        if(request.getSession().getAttribute("loggedInCustomer")== null || request.getSession().getAttribute("loggedInCustomer") == (Object)1){
            return "redirect:/customer/login";
        }
        ICoupon coupon = new Coupon();
        ICouponDao couponDao = new CouponDaoImpl();
        double discountPercentage = coupon.getMaximumDiscountValidToday(couponDao);
        System.out.println("DiscountPercentage:"+ discountPercentage);
        IPayment payment = new Payment();
        IPaymentDao paymentDao = new PaymentDaoImpl();
        Double originalAmount = payment.getAmountDue(paymentId, paymentDao);
        FareCalculatorImpl fareCalculator = new FareCalculatorImpl(originalAmount, discountPercentage);
        fareCalculator.calculateFinalAmount();
        System.out.println("finalAmount: "+ fareCalculator.finalAmount);
        System.out.println("Deduction:"+ fareCalculator.deduction);
        System.out.println("DiscountPercentage:"+ fareCalculator.discountPercentage);
        model.addAttribute("fare",fareCalculator);
        model.addAttribute("paymentId",paymentId);
        return CUSTOMER_VIEW_BILL;
    }

    @GetMapping("/customer/payment_status_success")
    String changePaymentStatus(@RequestParam("paymentId") int paymentId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInCustomer")== null || request.getSession().getAttribute("loggedInCustomer") == (Object)1){
            return "redirect:/customer/login";
        }
        IPaymentDao paymentDao = new PaymentDaoImpl();
        IPayment payment = new Payment();
        payment.updatePaymentStatusToSuccess(paymentId, paymentDao);
        return "redirect:/customer/view_payment_details";
    }


}
