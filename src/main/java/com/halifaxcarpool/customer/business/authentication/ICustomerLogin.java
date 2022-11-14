package com.halifaxcarpool.customer.business.authentication;

public interface ICustomerLogin {

    boolean login(String userName, String password, ICustomerAuthentication customerAuthentication);

}
