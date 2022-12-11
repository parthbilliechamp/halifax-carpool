package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;

public class AdminImpl implements IAdmin{

    @Override
    public Admin login(String userName, String password, IAdminAuthentication adminAuthentication) {
        return adminAuthentication.authenticate(userName, password);
    }

}
