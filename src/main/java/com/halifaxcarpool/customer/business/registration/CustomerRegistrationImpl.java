package com.halifaxcarpool.customer.business.registration;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.CustomerRegistrationDaoImpl;
import com.halifaxcarpool.customer.database.dao.ICustomerRegistrationDao;

public class CustomerRegistrationImpl implements ICustomerRegistration{

    ICustomerRegistrationDao customerRegistrationDao;

    public CustomerRegistrationImpl() {
        customerRegistrationDao = new CustomerRegistrationDaoImpl();
    }

    public void registerCustomer(Customer customer) {
        customerRegistrationDao.registerCustomer(customer);
    }

}
