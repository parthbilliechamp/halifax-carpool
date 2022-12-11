package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.CustomerImpl;
import com.halifaxcarpool.customer.business.authentication.ICustomer;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CustomerImplTest {

    ICustomerDaoObjectFactory customerDaoObjectFactoryImplTest = new CustomerDaoObjectFactoryImplTest();

    ICustomerBusinessObjectFactory customerBusinessObjectFactory = new CustomerBusinessObjectFactoryMain();

    ICustomer customerObj;

    ICustomerDao customerDaoMockObj;

    @Test
    public void registerCustomerSuccessTest() {
        int customerId;

        customerId = 5;
        Customer customer = new Customer(customerId, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");

        customerObj = customerBusinessObjectFactory.getCustomer();
        customerDaoMockObj = customerDaoObjectFactoryImplTest.getCustomerDao();

        try {
            customerObj.registerCustomer(customer, customerDaoMockObj);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void registerCustomerFailureTest() {
        int customerId;

        customerId = 4;
        Customer customer = new Customer(customerId, "Kane Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3");
        customerObj = customerBusinessObjectFactory.getCustomer();
        customerDaoMockObj = customerDaoObjectFactoryImplTest.getCustomerDao();

        try {
            customerObj.registerCustomer(customer, customerDaoMockObj);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void updateCustomerProfileSuccessTest() {
        int customerId;

        customerId = 4;
        Customer customer = new Customer(customerId, "Kanely Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3");

        customerObj = new CustomerImpl();
        customerDaoMockObj = customerDaoObjectFactoryImplTest.getCustomerDao();

        try {
            customerObj.updateCustomer(customer, customerDaoMockObj);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void updateCustomerProfileFailureTest() {
        int customerId;

        customerId = 11;
        Customer customer = new Customer(customerId, "Kanely Lart", "9532120333", "klklkl@gmail.ca", "kllk9009@3");

        customerObj = new CustomerImpl();
        customerDaoMockObj = customerDaoObjectFactoryImplTest.getCustomerDao();

        try {
            customerObj.updateCustomer(customer, customerDaoMockObj);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

}
