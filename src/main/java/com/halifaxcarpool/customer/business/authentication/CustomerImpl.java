package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;

public class CustomerImpl implements ICustomer {
    @Override
    public Customer login(String userName, String password, ICustomerAuthentication customerAuthentication) {
        return customerAuthentication.authenticate(userName, password);
    }

    @Override
    public void updateCustomer(Customer customer, ICustomerDao customerDao) {
        customerDao.updateCustomerProfile(customer);
    }
}
