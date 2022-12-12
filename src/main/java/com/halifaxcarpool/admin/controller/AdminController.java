package com.halifaxcarpool.admin.controller;
import java.util.List;

import com.halifaxcarpool.admin.business.*;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.database.dao.dao.ICouponDao;
import com.halifaxcarpool.customer.business.CustomerDaoFactory;
import com.halifaxcarpool.customer.business.CustomerDaoMainFactory;
import com.halifaxcarpool.driver.business.DriverDaoFactory;
import com.halifaxcarpool.driver.business.DriverDaoMainFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.IUserApproval;
import com.halifaxcarpool.admin.business.authentication.*;
import com.halifaxcarpool.admin.business.popular.ILocationPopularity;
import com.halifaxcarpool.admin.business.popular.LocationPopularityImpl;
import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
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
    private DriverDaoFactory driverDaoFactory = new DriverDaoMainFactory();
    private CustomerDaoFactory customerDaoFactory = new CustomerDaoMainFactory();
    private IAdminDaoFactory adminDaoFactory = new AdminDaoFactory();
    private IAdminModelFactory adminModelFactory = new AdminModelFactory();


    @GetMapping("/admin/view_discounts")
    String viewCoupons(HttpServletRequest request, Model model) {

        if(request.getSession().getAttribute("loggedInAdmin")== null
                || request.getSession().getAttribute("loggedInAdmin") == (Object)1){
            return "redirect:/admin/login";
        }
        String couponAttribute = "coupons";
        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        List<Coupon> coupons = coupon.viewCoupons(couponDao);
        model.addAttribute(couponAttribute, coupons);
        return VIEW_COUPONS_UI;
    }

    @GetMapping("/admin/create_new_coupon")
    String addNewCoupon(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("loggedInAdmin")== null
                || request.getSession().getAttribute("loggedInAdmin") == (Object)1){
            return "redirect:/admin/login";
        }
        model.addAttribute("coupon",new Coupon());
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
    String deleteCoupon(@RequestParam("couponId")int couponId, HttpServletRequest request){
        if(request.getSession().getAttribute("loggedInAdmin")== null
                || request.getSession().getAttribute("loggedInAdmin") == (Object)1){
            return "redirect:/admin/login";
        }
        ICoupon coupon = adminModelFactory.getCoupon();
        ICouponDao couponDao = adminDaoFactory.getCouponDao();
        coupon.deleteCoupon(couponId, couponDao);
        return "redirect:/admin/view_discounts";
    }

    @GetMapping("/admin/login")
    String adminLogin(Model model, HttpServletRequest httpServletRequest) {
        String adminLiteral = "admin";
        String loggedInAdminLiteral = "loggedInAdmin";
        String loggedInErrorLiteral = "loggedInError";

        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        model.addAttribute(adminLiteral, new Admin());
        if (adminAttribute == (Object) 1) {
            model.addAttribute(loggedInErrorLiteral, "noError");
        } else if (adminAttribute == (Object) 0) {
            model.addAttribute(loggedInErrorLiteral, "error");
            httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, 1);
        }
        return ADMIN_LOGIN_FORM;

    }

    @PostMapping("/admin/login/check")
    String authenticateLoggedInAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {
        String adminLiteral = "admin";
        String loggedInAdminLiteral = "loggedInAdmin";

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
        String loggedInAdminLiteral = "loggedInAdmin";
        Object adminAttribute = httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);

        if (adminAttribute != (Object) 0) {
            httpServletRequest.getSession().setAttribute(loggedInAdminLiteral, 1);
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/home")
    String adminHome(Model model, HttpServletRequest httpServletRequest) {
        String loggedInAdminLiteral = "loggedInAdmin";

        httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);
        return ADMIN_HOME_PAGE;
    }

    @GetMapping("/admin/view_driver_stats")
    public String showDriverStatistic(Model model){
        String userStatsAttribute = "userStats";

        IUserDetails driverDetails = driverDaoFactory.getDriverDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getDriverStatisticsBuilder(driverDetails);
        IUserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return DRIVER_STATISTICS;
    }

    @GetMapping("/admin/view_customer_stats")
    public String showCustomerStatistic(Model model){
        String userStatsAttribute = "userStats";

        IUserDetails customerDetails = customerDaoFactory.getCustomerDetailsDao();
        IUserStatisticsBuilder userStatisticsBuilder = adminModelFactory.getCustomerStatisticsBuilder(customerDetails);
        UserStatistics userStatistics =
                adminModelFactory.getAnalysisBluePrint(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return CUSTOMER_STATISTICS;
    }

    @GetMapping("/admin/view_driver_approval_requests")
    public String showDriverApprovalRequests(Model model){
        String approvalRequestsAttribute = "approvalRequests";

        IDriverApprovalDao driverApprovalDao = adminDaoFactory.getDriverApprovalDao();
        IUserApproval userApproval = adminModelFactory.getDriverApproval(driverApprovalDao);
        List<User> drivers = userApproval.getValidUserRequests();

        model.addAttribute(approvalRequestsAttribute, drivers);

        return DRIVER_APPROVAL_REQUESTS;
    }

    @GetMapping("/admin/updateApprovalStatus")
    public String updateDriverApprovalStatus(@RequestParam("license_id") String licenseNumber,
                                             @RequestParam("status") String status){
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
    public String viewPopularLocations(Model model){
        String maxOccurrenceAttribute = "maxOccurrence";
        String streetNamesAttribute = "streetNames";

        ILocationPopularityDao locationPopularityDao = adminDaoFactory.getLocationPopularityDao();
        ILocationPopularity locationPopularity = adminModelFactory.getLocationPopularity(locationPopularityDao);

        Map<Integer, List<String>> popularStreetNames = locationPopularity.getPopularPickUpLocations();
        Map.Entry<Integer,List<String>> entry = popularStreetNames.entrySet().iterator().next();

        int occurrences = entry.getKey().intValue();
        List<String> streetNames = entry.getValue();

        model.addAttribute(maxOccurrenceAttribute, occurrences);
        model.addAttribute(streetNamesAttribute, streetNames);

        return "view_popular_locations";

    }
}
