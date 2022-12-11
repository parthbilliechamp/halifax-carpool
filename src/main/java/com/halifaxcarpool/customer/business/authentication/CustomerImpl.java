package com.halifaxcarpool.customer.business.authentication;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;

public class CustomerImpl implements ICustomer {
    @Override
    public Customer login(String userName, String password, ICustomerAuthentication customerAuthentication, ICustomerAuthenticationDao customerAuthenticationDao) {
        return customerAuthentication.authenticate(userName, password, customerAuthenticationDao);
    }

    @Override
    public void registerCustomer(Customer customer, ICustomerDao customerDao) throws Exception {
        try {
            customerDao.registerCustomer(customer);
        } catch (Exception e) {
            throw new Exception("Customer Already Exists");
        }
    }

    @Override
    public void updateCustomer(Customer customer, ICustomerDao customerDao) {
        customerDao.updateCustomerProfile(customer);
    }
}
