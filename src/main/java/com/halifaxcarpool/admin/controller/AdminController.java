package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @GetMapping("/admin/viewDriverStatistics")
    public void showDriverStatistic(){

    }

}
