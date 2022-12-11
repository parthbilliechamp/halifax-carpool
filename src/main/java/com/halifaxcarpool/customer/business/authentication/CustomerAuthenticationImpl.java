package com.halifaxcarpool.customer.business.authentication;


import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;

import java.sql.Connection;

public class CustomerAuthenticationImpl implements ICustomerAuthentication {

    @Override
    public Customer authenticate(String userName, String password, ICustomerAuthenticationDao customerAuthenticationDao) {
        return customerAuthenticationDao.authenticate(userName, password);
    }

}
