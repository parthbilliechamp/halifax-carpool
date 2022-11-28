package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;

public class CustomerLoginImpl implements ICustomerLogin {
    @Override
    public Customer login(String userName, String password, ICustomerAuthentication customerAuthentication) {
        return customerAuthentication.authenticate(userName, password);
    }
}
