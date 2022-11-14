package com.halifaxcarpool.customer.database.dao;

public interface ICustomerAuthenticationDao {

    boolean authenticate(String username, String password);

}
