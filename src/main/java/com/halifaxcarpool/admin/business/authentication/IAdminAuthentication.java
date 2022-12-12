package com.halifaxcarpool.admin.business.authentication;

import com.halifaxcarpool.admin.business.beans.Admin;

public interface IAdminAuthentication {

    Admin authenticate(String userName, String password);

}
