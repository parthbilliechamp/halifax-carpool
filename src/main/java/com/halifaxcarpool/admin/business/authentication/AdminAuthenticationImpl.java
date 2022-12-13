package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;

public class AdminAuthenticationImpl implements IAdminAuthentication {

    @Override
    public Admin authenticate(String userName, String password, IAdminAuthenticationDao adminAuthenticationDao) {
        return adminAuthenticationDao.authenticate(userName, password);
    }
}
