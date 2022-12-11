package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.database.dao.IUserAuthenticationDao;

public interface IUserAuthentication {

    User authenticateUser(String userName, String password, IUserAuthenticationDao userAuthenticationDao);

}
