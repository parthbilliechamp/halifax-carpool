package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;

public interface ICustomerAuthentication {

    Customer authenticate(String userName, String password, ICustomerAuthenticationDao customerAuthenticationDao);

}
