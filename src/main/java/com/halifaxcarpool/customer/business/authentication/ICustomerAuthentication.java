package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;

public interface ICustomerAuthentication {

    Customer authenticate(String userName, String password);

}
