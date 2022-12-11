package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.UserApproval;
import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDaoImpl;
import com.halifaxcarpool.admin.business.authentication.AuthenticationFacade;
import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.CustomerDetailsDaoImpl;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
import java.util.List;

@Controller
public class AdminController {

    private static final String ADMIN_LOGIN_FORM = "login_admin_form";
    private static final String ADMIN_HOME_PAGE = "admin_home_page";
    private static final String DRIVER_STATISTICS = "view_driver_stats";
    private static final String CUSTOMER_STATISTICS = "view_customer_stats";
    private static final String DRIVER_APPROVAL_REQUESTS = "view_driver_approval_requests";


    @GetMapping("/admin/login")
    String adminLogin(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("admin", new Admin());
        if (httpServletRequest.getSession().getAttribute("loggedInAdmin") == (Object) 1) {
            model.addAttribute("loggedInError", "noError");
        } else if (httpServletRequest.getSession().getAttribute("loggedInAdmin") == (Object) 0) {
            model.addAttribute("loggedInError", "error");
            httpServletRequest.getSession().setAttribute("loggedInAdmin", 1);
        }
        return ADMIN_LOGIN_FORM;
    }

    @PostMapping("/admin/login/check")
    String authenticateLoggedInAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {
        AuthenticationFacade authenticationFacade = new AuthenticationFacade();
        Admin validAdmin = authenticationFacade.authenticate(admin.getUserName(), admin.getPassword());
        model.addAttribute("admin", admin);
        if (validAdmin == null) {
            httpServletRequest.getSession().setAttribute("loggedInAdmin", 0);
            return "redirect:/admin/login";
        }
        httpServletRequest.getSession().setAttribute("loggedInAdmin", validAdmin);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/logout")
    String logoutAdmin(@ModelAttribute("admin") Admin admin, HttpServletRequest
            httpServletRequest, Model model) {
        if (httpServletRequest.getSession().getAttribute("loggedInAdmin") != (Object) 0) {
            httpServletRequest.getSession().setAttribute("loggedInAdmin", 1);
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/home")
    String adminHome(Model model, HttpServletRequest httpServletRequest) {
        Admin admin = (Admin) httpServletRequest.getSession().getAttribute("loggedInAdmin");
        return ADMIN_HOME_PAGE;
    }

    @GetMapping("/admin/view_driver_stats")
    public String showDriverStatistic(Model model){
        IUserDetails driverDetails = new DriverDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new DriverStatistics(driverDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute("userStats", userStatistics);

        return DRIVER_STATISTICS;
    }

    @GetMapping("/admin/view_customer_stats")
    public String showCustomerStatistic(Model model){
        IUserDetails customerDetails = new CustomerDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new CustomerStatistics(customerDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveUserStatistics();

        model.addAttribute("userStats", userStatistics);

        return CUSTOMER_STATISTICS;
    }

    @GetMapping("/admin/view_driver_approval_requests")
    public String showDriverApprovalRequests(Model model){
        DriverApprovalDao driverApprovalDao = new DriverApprovalDaoImpl();
        UserApproval userApproval = new DriverApproval(driverApprovalDao);
        List<User> drivers = userApproval.getValidUserRequests();

        model.addAttribute("approvalRequests", drivers);

        return DRIVER_APPROVAL_REQUESTS;
    }

    @GetMapping("/admin/updateApprovalStatus")
    public String updateDriverApprovalStatus(@RequestParam("license_id") String licenseNumber,
                                             @RequestParam("status") String status){
        System.out.println("JJJ");
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

}
