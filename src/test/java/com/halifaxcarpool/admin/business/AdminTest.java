package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.authentication.IAdmin;
import com.halifaxcarpool.admin.business.authentication.IAdminAuthentication;
import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;
import org.junit.jupiter.api.Test;

public class AdminTest {

    private final IAdminModelFactory adminModelFactory = new AdminModelFactory();
    private final IAdminDaoFactory adminDaoFactory = new AdminDaoTestFactory();

    @Test
    public void adminLoginSuccessTest() {
        String userName = "admin";
        String password = "admin";
        int expectedAdminId = 1;

        IAdmin admin = adminModelFactory.getAdmin();
        IAdminAuthentication adminAuthentication = adminModelFactory.getAdminAuthentication();
        IAdminAuthenticationDao adminAuthenticationDao = adminDaoFactory.getAdminAuthenticationDao();

        Admin validAdmin = admin.login(userName, password, adminAuthentication, adminAuthenticationDao);
        assert expectedAdminId == validAdmin.getAdminId();
    }

    @Test
    public void adminLoginFailureTest() {
        String userName = "admin1";
        String password = "admin";

        IAdmin admin = adminModelFactory.getAdmin();
        IAdminAuthentication adminAuthentication = adminModelFactory.getAdminAuthentication();
        IAdminAuthenticationDao adminAuthenticationDao = adminDaoFactory.getAdminAuthenticationDao();

        Admin validAdmin = admin.login(userName, password, adminAuthentication, adminAuthenticationDao);
        assert null == validAdmin;
    }

}
