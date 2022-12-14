package com.halifaxcarpool.admin.controller;
import java.util.List;

import com.halifaxcarpool.admin.business.*;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.ICouponDao;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import com.halifaxcarpool.driver.business.DriverDaoFactory;
import com.halifaxcarpool.driver.business.IDriverDaoFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.halifaxcarpool.admin.business.approve.IUserApproval;
import com.halifaxcarpool.admin.business.authentication.*;
import com.halifaxcarpool.admin.business.popular.ILocationPopularity;
import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.commons.business.beans.User;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AdminController {

    private static final String VIEW_COUPONS_UI = "view_coupon_codes";
    private static final String ADMIN_LOGIN_FORM = "login_admin_form";
    private static final String ADMIN_HOME_PAGE = "admin_home_page";
    private static final String DRIVER_STATISTICS = "view_driver_stats";
    private static final String CUSTOMER_STATISTICS = "view_customer_stats";
    private static final String DRIVER_APPROVAL_REQUESTS = "view_driver_approval_requests";
    private static final String ADMIN_CREATE_NEW_COUPON = "create_coupon";
    private static final String LOGGED_IN_ADMIN = "loggedInAdmin";
    private static final String COUPONS = "coupons";
    private static final String COUPON = "coupon";
    private static final String ADMIN = "admin";
    private static final String LOGGED_IN_ERROR = "loggedInError";
    private static final String NO_ERROR = "noError";
    private static final String ERROR = "Error";
    private static final  String USER_STATS = "userStats";
    private static final String APPROVAL_REQUESTS = "approvalRequests";
    private static final String MAX_OCCURRENCE = "maxOccurrence";
    private static final String STREET_NAMES = "streetNames";
    private final IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();
    private final ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();
    private final IAdminDaoFactory adminDaoFactory = new AdminDaoFactory();
    private final IAdminModelFactory adminModelFactory = new AdminModelFactory();


    @GetMapping("/admin/view_discounts")
    String viewCoupons(HttpServletRequest httpServletRequest, Model model) {

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }


        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        List<Coupon> coupons = coupon.viewCoupons(couponDao);
        model.addAttribute(COUPONS, coupons);
        return VIEW_COUPONS_UI;
    }

    @GetMapping("/admin/create_new_coupon")
    String addNewCoupon(HttpServletRequest httpServletRequest, Model model){
        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        model.addAttribute(COUPON,new Coupon());
        return ADMIN_CREATE_NEW_COUPON;
    }

    @PostMapping("/admin/create_new_coupon")
    String insertNewCoupon(@ModelAttribute("coupon")Coupon coupon){
        ICoupon coupon1 = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        coupon1.createCoupon(coupon, couponDao);
        return "redirect:/admin/view_discounts";
    }

    @GetMapping("/admin/delete_coupon")
    String deleteCoupon(@RequestParam("couponId")int couponId, HttpServletRequest httpServletRequest){
        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }
        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        coupon.deleteCoupon(couponId, couponDao);
        return "redirect:/admin/view_discounts";
    }

    @GetMapping("/admin/login")
    String adminLogin(Model model, HttpServletRequest httpServletRequest) {


        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        model.addAttribute(ADMIN, new Admin());
        if (adminAttribute == (Object) 1) {
            model.addAttribute(LOGGED_IN_ERROR, NO_ERROR);
        } else if (adminAttribute == (Object) 0) {
            model.addAttribute(LOGGED_IN_ERROR, ERROR);
            httpServletRequest.getSession().setAttribute(LOGGED_IN_ADMIN, 1);
        }
        return ADMIN_LOGIN_FORM;

    }

    @PostMapping("/admin/login/check")
    String authenticateLoggedInAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {

        IAdmin adminImpl = adminModelFactory.getAdmin();
        IAdminAuthentication adminAuthentication = adminModelFactory.getAdminAuthentication();
        IAdminAuthenticationDao adminAuthenticationDao = adminDaoFactory.getAdminAuthenticationDao();

        Admin validAdmin = adminImpl.login(admin.getUserName(), admin.getPassword(), adminAuthentication, adminAuthenticationDao);

        model.addAttribute(ADMIN, admin);

        if (validAdmin == null) {
            httpServletRequest.getSession().setAttribute(LOGGED_IN_ADMIN, 0);
            return "redirect:/admin/login";
        }
        httpServletRequest.getSession().setAttribute(LOGGED_IN_ADMIN, validAdmin);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/logout")
    String logoutAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {
        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if (adminAttribute != (Object) 0) {
            httpServletRequest.getSession().setAttribute(LOGGED_IN_ADMIN, 1);
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/home")
    String adminHome(Model model, HttpServletRequest httpServletRequest) {

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);
        return ADMIN_HOME_PAGE;
    }

    @GetMapping("/admin/view_driver_stats")
    public String showDriverStatistic(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IUserDetails driverDetails = driverDaoFactory.getDriverDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getDriverStatisticsBuilder(driverDetails);
        UserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(USER_STATS, userStatistics);

        return DRIVER_STATISTICS;
    }

    @GetMapping("/admin/view_customer_stats")
    public String showCustomerStatistic(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IUserDetails customerDetails = customerDaoFactory.getCustomerDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getCustomerStatisticsBuilder(customerDetails);
        UserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(USER_STATS, userStatistics);

        return CUSTOMER_STATISTICS;
    }

    @GetMapping("/admin/view_driver_approval_requests")
    public String showDriverApprovalRequests(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IDriverApprovalDao driverApprovalDao = adminDaoFactory.getDriverApprovalDao();
        IUserApproval userApproval = adminModelFactory.getDriverApproval(driverApprovalDao);
        List<User> drivers = userApproval.getValidUserRequests();

        model.addAttribute(APPROVAL_REQUESTS, drivers);

        return DRIVER_APPROVAL_REQUESTS;
    }

    @GetMapping("/admin/updateApprovalStatus")
    public String updateDriverApprovalStatus(@RequestParam("license_id") String licenseNumber,
                                             @RequestParam("status") String status, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IDriverApprovalDao driverApprovalDao = adminDaoFactory.getDriverApprovalDao();
        IUserApproval userApproval = adminModelFactory.getDriverApproval(driverApprovalDao);

        if(status.equals("accept")){
            userApproval.acceptUserRequest(licenseNumber);
        }
        else {
            userApproval.rejectUserRequest(licenseNumber);
        }
        return "redirect:/admin/view_driver_approval_requests";
    }

    @GetMapping("/admin/view_popular_locations")
    public String viewPopularLocations(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(LOGGED_IN_ADMIN);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        ILocationPopularityDao locationPopularityDao = adminDaoFactory.getLocationPopularityDao();
        ILocationPopularity locationPopularity = adminModelFactory.getLocationPopularity(locationPopularityDao);

        Map<Integer, List<String>> popularStreetNames = locationPopularity.getPopularPickUpLocations();
        Map.Entry<Integer,List<String>> entry = popularStreetNames.entrySet().iterator().next();

        int occurrences = entry.getKey();
        List<String> streetNames = entry.getValue();

        model.addAttribute(MAX_OCCURRENCE, occurrences);
        model.addAttribute(STREET_NAMES, streetNames);

        return "view_popular_locations";
    }

}
