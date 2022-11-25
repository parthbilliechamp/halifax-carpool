package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import com.halifaxcarpool.customer.database.dao.CustomerRegistrationDaoMockImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerRegistrationDao;

public class CustomerRegistrationMockImpl implements ICustomerRegistration {

    ICustomerRegistrationDao customerRegistrationDao = new CustomerRegistrationDaoMockImpl();

    public void registerCustomer(Customer customer) {
        customerRegistrationDao.registerCustomer(customer);
    }

}
