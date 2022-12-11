package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;

public class AuthenticationFacade {

    public Admin authenticate(String userName, String password) {

        IAdmin admin = new AdminImpl();
        IAdminAuthentication adminAuthentication = new AdminAuthenticationImpl();

        return admin.login(userName, password, adminAuthentication);
    }

}
