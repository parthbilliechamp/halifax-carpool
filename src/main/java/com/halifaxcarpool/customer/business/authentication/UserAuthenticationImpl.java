package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.database.dao.IUserAuthenticationDao;

public class UserAuthenticationImpl implements IUserAuthentication {
    @Override
    public User authenticateUser(String userName, String password, IUserAuthenticationDao userAuthenticationDao) {
        return userAuthenticationDao.authenticate(userName, password);
    }

}
