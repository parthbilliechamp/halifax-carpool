package com.halifaxcarpool.commons.business.beans;

import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;

public abstract class User {

    public abstract void registerUser(IUserDao userDao) throws Exception;

    public abstract boolean updateUser(IUserDao userDao);

    public User loginUser(String userName, String password,
                          IUserAuthentication userAuthentication,
                          IUserAuthenticationDao userAuthenticationDao) {
        return userAuthentication.authenticateUser(userName, password, userAuthenticationDao);
    }

}
