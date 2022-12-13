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
import com.halifaxcarpool.commons.business.CommonsObjectFactoryImpl;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final CommonsObjectFactoryImpl commonsObjectFactory = new CommonsObjectFactoryImpl();
    private final CustomerModelFactory customerObjectFactory = new CustomerModelMainFactory();
    private final CustomerDaoFactory customerObjectDaoFactory = new CustomerDaoMainFactory();
    private final DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    private final DriverDaoFactory driverDaoFactory = new DriverDaoMainFactory();

    private static final Map<String, String> redirectPageStorage = new HashMap<String, String>() {
        {
            put("login", CUSTOMER_LOGIN_FROM);
        }
    };

    @GetMapping("/customer/login")
    String login(Model model, HttpServletRequest httpServletRequest) {
        String customerLiteral = "customer";
        String loggedInCustomerLiteral = "loggedInCustomer";
        String loggedInErrorLiteral = "loggedInError";
        Object customerAttribute = httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

        model.addAttribute(customerLiteral, new Customer());
        if (customerAttribute == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, "noError");
        } else if (customerAttribute == (Object) 0) {
            model.addAttribute(loggedInErrorLiteral, "error");
            httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, 1);
        }

        return redirectPageStorage.get("login");
    }

    @PostMapping("/customer/login/check")
    String authenticateLoggedInCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest, Model model) {
        String customerLiteral = "customer";
        String loggedInCustomerLiteral = "loggedInCustomer";

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
            System.out.println(httpServletRequest.getSession().getAttribute("loggedInCustomer"));
            return "redirect:/customer/login";
        }

        httpServletRequest.getSession().setAttribute(loggedInCustomerLiteral, validCustomer);
        return "redirect:/customer/create_ride_request";
    }

    @GetMapping("/customer/logout")
    String logoutCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest
            httpServletRequest) {
        Customer customerUser = (Customer) httpServletRequest.getSession().getAttribute("loggedInCustomer");

        if (customerUser != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInCustomer", 1);
        }

        return "redirect:/";
    }
    @GetMapping("/customer/register")
    String registerCustomer(Model model, HttpServletRequest httpServletRequest) {
        String customerLiteral = "customer";
        String customerThatTriedToRegisterLiteral = "customerThatTriedToRegister";
        String registrationErrorLiteral = "registrationError";

        User customer = customerObjectFactory.getCustomer();
        Object registrationSessionErrorAttribute = httpServletRequest.getSession().getAttribute(customerThatTriedToRegisterLiteral);

        model.addAttribute(customerLiteral, customer);
        if (registrationSessionErrorAttribute != null) {
            model.addAttribute(registrationErrorLiteral, registrationSessionErrorAttribute.toString());
            httpServletRequest.getSession().setAttribute(customerThatTriedToRegisterLiteral, null);
        }
        return CUSTOMER_REGISTRATION_FORM;
    }

    @PostMapping("/customer/register/save")
    String saveRegisteredCustomer(@ModelAttribute("customer") Customer customer, HttpServletRequest httpServletRequest) {
        String customerThatTriedToRegisterLiteral = "customerThatTriedToRegister";
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
        String loggedInCustomerLiteral = "loggedInCustomer";
        String customerProfileLiteral = "customerProfile";

        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        model.addAttribute(customerProfileLiteral, customer);
        return CUSTOMER_PROFILE_FORM;
    }

    @GetMapping("/customer/update_profile")
    String updateProfile(Model model) {
        String customerProfileLiteral = "customerProfile";

        model.addAttribute(customerProfileLiteral, new Customer());
        return CUSTOMER_PROFILE_FORM;
    }

    @PostMapping("/customer/update_profile")
    String updateCustomerProfile(@ModelAttribute("customerProfile") Customer customerProfile, HttpServletRequest request) {
        String loggedInCustomerLiteral = "loggedInCustomer";

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
        String loggedInCustomerLiteral = "loggedInCustomer";
        String rideRequestsAttribute = "rideRequests";
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;

        IRideRequest viewRideRequests = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.createRideRequestsDao();

        List<RideRequest> rideRequests = viewRideRequests.viewRideRequests(customer.getCustomerId(), rideRequestsDao);

        model.addAttribute(rideRequestsAttribute, rideRequests);
        return VIEW_RIDE_REQUESTS;
    }

    @GetMapping("/customer/view_recommended_rides")
    String viewRecommendedRides(@RequestParam("rideRequestId") int rideRequestId,
                                @RequestParam("startLocation") String startLocation,
                                @RequestParam("endLocation") String endLocation,
                                HttpServletRequest httpServletRequest,
                                Model model) {
        String loggedInCustomerLiteral = "loggedInCustomer";
        List<List<Ride>> singleRidesList = new ArrayList<>();
        List<List<Ride>> multiRidesList = new ArrayList<>();
        String rideRequestIdLiteral = "rideRequestId";
        String recommendedSingleRidesAttribute = "recommendedSingleRides";
        String recommendedMultiRidesAttribute = "recommendedMultiRides";
        Object customerAttribute = httpServletRequest.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null ||
                customerAttribute == (Object) 1) {
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

        return VIEW_RECOMMENDED_RIDES;
    }

    @GetMapping("/customer/send_ride_request")
    String sendRideRequest(@RequestParam("rideId") int rideId,
                           @RequestParam("rideRequestId") int rideRequestId, HttpServletRequest httpServletRequest) {
        String loggedInCustomerLiteral = "loggedInCustomer";
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
        String loggedInCustomerLiteral = "loggedInCustomer";
        String activeRidesLiteral = "activeRides";
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

        String loggedInCustomerLiteral = "loggedInCustomer";
        String rideRequestLiteral = "rideRequest";
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
    public String createRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest,
                                    HttpServletRequest request) {

        String loggedInCustomerLiteral = "loggedInCustomer";

        Customer customer = (Customer) request.getSession().getAttribute(loggedInCustomerLiteral);

        System.out.println(customer);

        rideRequest.setCustomerId(customer.getCustomerId());
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.createRideRequestsDao();
        rideRequest.createRideRequest(rideRequestsDao);

        return "redirect:/customer/view_ride_requests";
    }

    @GetMapping ("/customer/cancel_ride_request")
    public String cancelRideRequest(@ModelAttribute("rideRequest") RideRequest rideRequest, HttpServletRequest request) {
        String loggedInCustomerLiteral = "loggedInCustomer";
        Object customerAttribute = request.getSession().getAttribute(loggedInCustomerLiteral);

        if (customerAttribute == null || customerAttribute == (Object) 1) {
            return "redirect:/customer/login";
        }

        Customer customer = (Customer) customerAttribute;
        rideRequest.setCustomerId(customer.customerId);

        IRideRequest rideRequestObj = customerObjectFactory.getRideRequest();
        IRideRequestsDao rideRequestsDao = customerObjectDaoFactory.createRideRequestsDao();

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
