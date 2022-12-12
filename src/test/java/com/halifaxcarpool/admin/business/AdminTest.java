package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.authentication.AdminAuthenticationImpl;
import com.halifaxcarpool.admin.business.authentication.AdminImpl;
import com.halifaxcarpool.admin.business.authentication.IAdmin;
import com.halifaxcarpool.admin.business.authentication.IAdminAuthentication;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.AdminAuthenticationDaoMockImpl;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;
import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    public void adminLoginSuccessTest() {
        String userName = "admin";
        String password = "admin";
        int expectedAdminId = 1;

        IAdmin admin = new AdminImpl();
        IAdminAuthentication adminAuthentication = new AdminAuthenticationImpl();
        IAdminAuthenticationDao adminAuthenticationDao = new AdminAuthenticationDaoMockImpl();

        Admin validAdmin = admin.login(userName, password, adminAuthentication, adminAuthenticationDao);
        assert expectedAdminId == validAdmin.getAdminId();
    }

    @Test
    public void adminLoginFailureTest() {
        String userName = "admin1";
        String password = "admin";

        IAdmin admin = new AdminImpl();
        IAdminAuthentication adminAuthentication = new AdminAuthenticationImpl();
        IAdminAuthenticationDao adminAuthenticationDao = new AdminAuthenticationDaoMockImpl();

        Admin validAdmin = admin.login(userName, password, adminAuthentication, adminAuthenticationDao);
        assert null == validAdmin;
    }

}