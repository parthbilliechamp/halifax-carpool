package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomer;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;

public class CustomerMockImpl implements ICustomer {

    @Override
    public Customer login(String userName, String password, ICustomerAuthentication customerAuthenticationMockObj) {
        return customerAuthenticationMockObj.authenticate(userName, password);
    }

    @Override
    public void updateCustomer(Customer customer, ICustomerDao customerDao) {
        customerDao.updateCustomerProfile(customer);
    }
}
