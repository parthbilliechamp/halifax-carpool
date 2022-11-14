package com.halifaxcarpool.customer.business.authentication;

public interface ICustomerAuthentication {

    boolean authenticate(String userName, String password);

}
