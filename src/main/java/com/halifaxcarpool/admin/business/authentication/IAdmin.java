package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;
import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;

public interface IAdmin {

    Admin login(String userName, String password, IAdminAuthentication adminAuthentication,
                IAdminAuthenticationDao adminAuthenticationDao);

}
