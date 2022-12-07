package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;

public interface ICustomer {

    Customer login(String userName, String password, ICustomerAuthentication customerAuthentication);

    void updateCustomer(Customer customer, ICustomerDao customerDao);

}
