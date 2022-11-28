package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;

public interface ICustomerLogin {

    Customer login(String userName, String password, ICustomerAuthentication customerAuthentication);

}
