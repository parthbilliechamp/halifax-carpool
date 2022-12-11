package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.AdminAuthenticationDaoImpl;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;

public class AdminAuthenticationImpl implements IAdminAuthentication {

    IAdminAuthenticationDao adminAuthenticationDao;

    public AdminAuthenticationImpl() {
        adminAuthenticationDao = new AdminAuthenticationDaoImpl();
    }

    @Override
    public Admin authenticate(String userName, String password) {
        return adminAuthenticationDao.authenticate(userName, password);
    }
}
