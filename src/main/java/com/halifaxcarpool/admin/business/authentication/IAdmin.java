package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;

public interface IAdmin {

    Admin login(String userName, String password, IAdminAuthentication adminAuthentication);

}
