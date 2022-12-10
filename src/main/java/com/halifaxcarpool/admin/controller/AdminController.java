package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.UserApproval;
import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.DriverApprovalDaoImpl;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.database.dao.CustomerDetailsDaoImpl;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminController {

    private static final String DRIVER_STATISTICS = "view_driver_stats";
    private static final String CUSTOMER_STATISTICS = "view_customer_stats";

    private static final String DRIVER_APPROVAL_REQUESTS = "view_driver_approval_requests";


    @GetMapping("/admin")
    @ResponseBody
    String home() {
        return "Hello World!";
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

}
