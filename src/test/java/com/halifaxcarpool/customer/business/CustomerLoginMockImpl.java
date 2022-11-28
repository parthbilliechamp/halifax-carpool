package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomerLogin;
import com.halifaxcarpool.customer.business.beans.Customer;

public class CustomerLoginMockImpl implements ICustomerLogin {

    @Override
    public Customer login(String userName, String password, ICustomerAuthentication customerAuthenticationMockObj) {
        return customerAuthenticationMockObj.authenticate(userName, password);
    }
}
