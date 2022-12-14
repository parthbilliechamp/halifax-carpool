package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;

public class AdminImpl implements IAdmin {

    @Override
    public Admin login(String userName, String password,IAdminAuthentication adminAuthentication,
                       IAdminAuthenticationDao adminAuthenticationDao) {
        return adminAuthentication.authenticate(userName, password, adminAuthenticationDao);
    }

}
