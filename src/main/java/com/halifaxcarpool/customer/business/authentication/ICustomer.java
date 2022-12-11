package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;

public interface ICustomer {

    Customer login(String userName, String password, ICustomerAuthentication customerAuthentication, ICustomerAuthenticationDao customerAuthenticationDao);

    void updateCustomer(Customer customer, ICustomerDao customerDao);

    void registerCustomer(Customer customer, ICustomerDao customerDao) throws Exception;

}
