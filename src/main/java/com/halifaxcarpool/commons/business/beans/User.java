package com.halifaxcarpool.commons.business.beans;

import com.halifaxcarpool.customer.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.database.dao.CustomerAuthenticationDaoImpl;
import com.halifaxcarpool.customer.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.IUserDao;

public abstract class User {

    public abstract void registerUser(IUserDao userDao);

    public abstract boolean updateUser(IUserDao userDao);

    public User loginUser(String userName, String password,
                          IUserAuthentication userAuthentication,
                          IUserAuthenticationDao userAuthenticationDao) {
        return userAuthentication.authenticateUser(userName, password, userAuthenticationDao);
    }

}
