package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Customer;
import org.junit.jupiter.api.Test;

public class CustomerRegistrationDaoImplTest {

    ICustomerRegistrationDao customerRegistrationDaoMockObject;

    @Test
    void registerCustomerTest() {
        int customer_id;
        Customer customerExtracted;

        customer_id = 5;
        Customer customer = new Customer(customer_id, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");

        customerRegistrationDaoMockObject = new CustomerRegistrationDaoMockImpl();
        customerRegistrationDaoMockObject.registerCustomer(customer);

        customerExtracted = ((CustomerRegistrationDaoMockImpl)customerRegistrationDaoMockObject).findCustomerDetailsFromHashMap(customer_id);

        assert customer_id == customerExtracted.getCustomerId();

    }

    @Test
    void registerCustomerInvalidInputTest() {

        int entered_customer_id;
        int expected_Customer_id;
        Customer customerExtracted;

        entered_customer_id = 5;
        expected_Customer_id = 2;
        Customer customer = new Customer(entered_customer_id, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");

        customerRegistrationDaoMockObject = new CustomerRegistrationDaoMockImpl();
        customerRegistrationDaoMockObject.registerCustomer(customer);

        customerExtracted = ((CustomerRegistrationDaoMockImpl)customerRegistrationDaoMockObject).findCustomerDetailsFromHashMap(entered_customer_id);

        assert expected_Customer_id != customerExtracted.getCustomerId();

    }

}
