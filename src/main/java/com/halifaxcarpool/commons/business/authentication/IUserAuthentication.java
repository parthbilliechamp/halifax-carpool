package com.halifaxcarpool.commons.business.authentication;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;

public interface IUserAuthentication {

    User authenticateUser(String userName, String password, IUserAuthenticationDao userAuthenticationDao);

}
