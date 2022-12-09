package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.customer.database.dao.CustomerDetailsDaoImpl;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private static final String DRIVER_STATISTICS = "view_driver_stats";
    private static final String CUSTOMER_STATISTICS = "view_customer_stats";


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

}
