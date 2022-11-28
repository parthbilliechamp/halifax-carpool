package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.business.registration.ICustomerRegistration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CustomerRegistrationImplTest {

    ICustomerRegistration customerRegistrationMockObject;

    @Test
    public void registerCustomerSuccessTest() {
        int customer_id;

        customer_id = 5;
        Customer customer = new Customer(customer_id, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");

        customerRegistrationMockObject = new CustomerRegistrationMockImpl();
        try {
            customerRegistrationMockObject.registerCustomer(customer);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void registerCustomerFailureTest() {
        int customer_id;

        customer_id = 5;
        Customer customer = new Customer(customer_id, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");

        customerRegistrationMockObject = new CustomerRegistrationMockImpl();
        try {
            customerRegistrationMockObject.registerCustomer(customer);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
