package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;

public class AuthenticationFacade {

    public Customer authenticate(String userName, String password) {

        ICustomer customerLogin = new CustomerImpl();
        ICustomerAuthentication customerAuthentication = new CustomerAuthenticationImpl();
        return customerLogin.login(userName, password, customerAuthentication);
    }

}
