package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.CustomerAuthenticationDaoMockImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;

public class CustomerAuthenticationMockImpl implements ICustomerAuthentication {

    ICustomerAuthenticationDao customerAuthenticationDaoMockObj;

    public CustomerAuthenticationMockImpl() {
        customerAuthenticationDaoMockObj = new CustomerAuthenticationDaoMockImpl();
    }


    @Override
    public Customer authenticate(String userName, String password) {
        return customerAuthenticationDaoMockObj.authenticate(userName, password);
    }
}
