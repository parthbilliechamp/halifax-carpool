package com.halifaxcarpool.customer.business.authentication;

public class CustomerLoginImpl implements ICustomerLogin {
    @Override
    public boolean login(String userName, String password, ICustomerAuthentication customerAuthentication) {
        return customerAuthentication.authenticate(userName, password);
    }
}
