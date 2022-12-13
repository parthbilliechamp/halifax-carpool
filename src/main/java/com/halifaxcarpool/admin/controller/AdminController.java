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
import com.halifaxcarpool.admin.business.statistics.*;
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
    private static final String loggedInAdminLiteral = "loggedInAdmin";
    private static final String couponsAttribute = "coupons";
    private static final String couponAttribute = "coupon";
    private static final String adminLiteral = "admin";
    private static final String loggedInErrorLiteral = "loggedInError";
    private static final String noErrorLiteral = "noError";
    private static final String errorLiteral = "Error";
    private static final  String userStatsAttribute = "userStats";
    private static final String approvalRequestsAttribute = "approvalRequests";
    private static final String maxOccurrenceAttribute = "maxOccurrence";
    private static final String streetNamesAttribute = "streetNames";
    private final IDriverDaoFactory driverDaoFactory = new DriverDaoFactory();
    private final ICustomerDaoFactory customerDaoFactory = new CustomerDaoFactory();
    private final IAdminDaoFactory adminDaoFactory = new AdminDaoFactory();
    private final IAdminModelFactory adminModelFactory = new AdminModelFactory();


    @GetMapping("/admin/view_discounts")
    String viewCoupons(HttpServletRequest httpServletRequest, Model model) {

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }


        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        List<Coupon> coupons = coupon.viewCoupons(couponDao);
        model.addAttribute(couponsAttribute, coupons);
        return VIEW_COUPONS_UI;
    }

    @GetMapping("/admin/create_new_coupon")
    String addNewCoupon(HttpServletRequest httpServletRequest, Model model){
        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        model.addAttribute(couponAttribute,new Coupon());
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
        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

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


        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        model.addAttribute(adminLiteral, new Admin());
        if (adminAttribute == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, noErrorLiteral);
        } else if (adminAttribute == (Object) 0) {
            model.addAttribute(loggedInErrorLiteral, errorLiteral);
            httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, 1);
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

        model.addAttribute(adminLiteral, admin);

        if (validAdmin == null) {
            httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, 0);
            return "redirect:/admin/login";
        }
        httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, validAdmin);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/logout")
    String logoutAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {
        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if (adminAttribute != (Object) 0) {
            httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, 1);
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/home")
    String adminHome(Model model, HttpServletRequest httpServletRequest) {

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);
        return ADMIN_HOME_PAGE;
    }

    @GetMapping("/admin/view_driver_stats")
    public String showDriverStatistic(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IUserDetails driverDetails = driverDaoFactory.getDriverDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getDriverStatisticsBuilder(driverDetails);
        IUserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return DRIVER_STATISTICS;
    }

    @GetMapping("/admin/view_customer_stats")
    public String showCustomerStatistic(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IUserDetails customerDetails = customerDaoFactory.getCustomerDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getCustomerStatisticsBuilder(customerDetails);
        UserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return CUSTOMER_STATISTICS;
    }

    @GetMapping("/admin/view_driver_approval_requests")
    public String showDriverApprovalRequests(Model model, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if(adminAttribute == null
                || adminAttribute == (Object)1){
            return "redirect:/admin/login";
        }

        IDriverApprovalDao driverApprovalDao = adminDaoFactory.getDriverApprovalDao();
        IUserApproval userApproval = adminModelFactory.getDriverApproval(driverApprovalDao);
        List<User> drivers = userApproval.getValidUserRequests();

        model.addAttribute(approvalRequestsAttribute, drivers);

        return DRIVER_APPROVAL_REQUESTS;
    }

    @GetMapping("/admin/updateApprovalStatus")
    public String updateDriverApprovalStatus(@RequestParam("license_id") String licenseNumber,
                                             @RequestParam("status") String status, HttpServletRequest httpServletRequest){

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

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

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

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

        model.addAttribute(maxOccurrenceAttribute, occurrences);
        model.addAttribute(streetNamesAttribute, streetNames);

        return "view_popular_locations";
    }

}
