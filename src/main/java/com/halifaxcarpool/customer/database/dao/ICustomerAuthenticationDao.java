package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Customer;

public interface ICustomerAuthenticationDao {

    Customer authenticate(String username, String password);

}
