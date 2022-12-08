package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping("/admin/viewDriverStatistics")
    public void showDriverStatistic(){
        IUserDetails driverDetails = new DriverDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new DriverStatistics(driverDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveDriverStatistics();

    }

}
