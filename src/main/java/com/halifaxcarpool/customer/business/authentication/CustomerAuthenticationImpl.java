package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.database.dao.CustomerAuthenticationDaoImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;

import java.sql.Connection;

public class CustomerAuthenticationImpl implements ICustomerAuthentication {

    ICustomerAuthenticationDao customerAuthenticationDao;

    public CustomerAuthenticationImpl() {
        customerAuthenticationDao = new CustomerAuthenticationDaoImpl();
    }
    @Override
    public boolean authenticate(String userName, String password) {
        return customerAuthenticationDao.authenticate(userName, password);
    }

}
