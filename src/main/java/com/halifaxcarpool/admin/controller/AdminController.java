package com.halifaxcarpool.admin.controller;

import com.halifaxcarpool.admin.business.authentication.AuthenticationFacade;
import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.business.statistics.UserStatistics;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.driver.database.dao.DriverDetailsDaoImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    private static final String ADMIN_LOGIN_FORM = "login_admin_form";
    private static final String ADMIN_HOME_PAGE = "admin_home_page";

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

    @GetMapping("/admin/viewDriverStatistics")
    public void showDriverStatistic(){
        IUserDetails driverDetails = new DriverDetailsDaoImpl();
        IUserStatisticsBuilder userStatisticsBuilder = new DriverStatistics(driverDetails);
        UserStatistics userStatistics = new UserAnalysis(userStatisticsBuilder).deriveDriverStatistics();

    }

}
