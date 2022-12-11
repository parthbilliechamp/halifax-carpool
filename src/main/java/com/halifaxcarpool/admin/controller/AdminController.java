package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.UserApproval;
import com.halifaxcarpool.admin.business.authentication.*;
import com.halifaxcarpool.admin.business.popular.LocationPopularity;
import com.halifaxcarpool.admin.business.popular.LocationPopularityImpl;
import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.database.dao.CustomerDetailsDaoImpl;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private static final String ADMIN_LOGIN_FORM = "login_admin_form";
    private static final String ADMIN_HOME_PAGE = "admin_home_page";
    private static final String DRIVER_STATISTICS = "view_driver_stats";
    private static final String CUSTOMER_STATISTICS = "view_customer_stats";
    private static final String DRIVER_APPROVAL_REQUESTS = "view_driver_approval_requests";


    //TODO: Add Factories if possible

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

        IAdmin adminImplObj = new AdminImpl();
        IAdminAuthentication adminAuthentication = new AdminAuthenticationImpl();
        IAdminAuthenticationDao adminAuthenticationDao = new AdminAuthenticationDaoImpl();

        Admin validAdmin = adminImplObj.login(admin.getUserName(), admin.getPassword(), adminAuthentication, adminAuthenticationDao);

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

        Admin admin = (Admin) httpServletRequest.getSession().getAttribute(loggedInAdminLiteral);
        return ADMIN_HOME_PAGE;
    }

    @GetMapping("/admin/view_driver_stats")
    public String showDriverStatistic(Model model){
        String userStatsAttribute = "userStats";

        IUserDetails driverDetails = new DriverDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new DriverStatistics(driverDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return DRIVER_STATISTICS;
    }

    @GetMapping("/admin/view_customer_stats")
    public String showCustomerStatistic(Model model){
        String userStatsAttribute = "userStats";

        IUserDetails customerDetails = new CustomerDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new CustomerStatistics(customerDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute(userStatsAttribute, userStatistics);

        return CUSTOMER_STATISTICS;
    }

    @GetMapping("/admin/view_driver_approval_requests")
    public String showDriverApprovalRequests(Model model){
        String approvalRequestsAttribute = "approvalRequests";

        DriverApprovalDao driverApprovalDao = new DriverApprovalDaoImpl();
        UserApproval userApproval = new DriverApproval(driverApprovalDao);
        List<User> drivers = userApproval.getValidUserRequests();

        model.addAttribute(approvalRequestsAttribute, drivers);

        return DRIVER_APPROVAL_REQUESTS;
    }

    @GetMapping("/admin/updateApprovalStatus")
    public String updateDriverApprovalStatus(@RequestParam("license_id") String licenseNumber,
                                             @RequestParam("status") String status){
        DriverApprovalDao driverApprovalDao = new DriverApprovalDaoImpl();
        UserApproval userApproval = new DriverApproval(driverApprovalDao);

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

        LocationPopularityDao locationPopularityDao = new LocationPopularityDaoImpl();
        LocationPopularity locationPopularity = new LocationPopularityImpl(locationPopularityDao);

        Map<Integer, List<String>> popularStreetNames = locationPopularity.getPopularPickUpLocations();
        Map.Entry<Integer,List<String>> entry = popularStreetNames.entrySet().iterator().next();

        int occurrences = entry.getKey().intValue();
        List<String> streetNames = entry.getValue();

        model.addAttribute(maxOccurrenceAttribute, occurrences);
        model.addAttribute(streetNamesAttribute, streetNames);

        return "view_popular_locations";

    }
}
