package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.UserApproval;
import com.halifaxcarpool.admin.business.popular.LocationPopularity;
import com.halifaxcarpool.admin.business.popular.LocationPopularityImpl;
import com.halifaxcarpool.admin.business.statistics.*;
import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.CustomerDetailsDaoImpl;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/admin/view_popular_locations")
    public String viewPopularLocations(Model model){
        LocationPopularityDao locationPopularityDao = new LocationPopularityDaoImpl();
        LocationPopularity locationPopularity = new LocationPopularityImpl(locationPopularityDao);

        Map<Integer, List<String>> popularStreetNames = locationPopularity.getPopularPickUpLocations();
        Map.Entry<Integer,List<String>> entry = popularStreetNames.entrySet().iterator().next();

        int occurrences = entry.getKey().intValue();
        List<String> streetNames = entry.getValue();

        model.addAttribute("maxOccurrence", occurrences);
        model.addAttribute("streetNames", streetNames);

        return "view_popular_locations";

    }
}
