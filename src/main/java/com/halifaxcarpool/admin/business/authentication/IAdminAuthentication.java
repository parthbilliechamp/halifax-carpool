package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;

public interface IAdminAuthentication {

    Admin authenticate(String userName, String password, IAdminAuthenticationDao adminAuthenticationDao);

}
