package com.halifaxcarpool.customer.business.authentication;

public class AuthenticationFacade {

    public boolean authenticate(String userName, String password) {

        ICustomerLogin customerLogin = new CustomerLoginImpl();
        ICustomerAuthentication customerAuthentication = new CustomerAuthenticationImpl();
        return customerLogin.login(userName, password, customerAuthentication);
    }

}
