package com.halifaxcarpool.customer.business.registration;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.CustomerRegistrationDaoImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerRegistrationDao;

public class CustomerRegistrationImpl implements ICustomerRegistration {

    ICustomerRegistrationDao customerDao;

    public CustomerRegistrationImpl() {
        customerDao = new CustomerRegistrationDaoImpl();
    }

    public void registerCustomer(Customer customer) {
        customerDao.registerCustomer(customer);
    }

}
