package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Customer;

public interface ICustomerDao {

    void updateCustomerProfile(Customer customer);

}
