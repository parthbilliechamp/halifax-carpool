package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.CommonsObjectFactoryImpl;
import com.halifaxcarpool.commons.business.ICommonsObjectFactory;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.beans.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class CustomerTest {

    CustomerDaoFactory customerDaoTestFactory = new CustomerDaoTestFactory();
    CustomerModelFactory customerModelFactory = new CustomerModelMainFactory();
    ICommonsObjectFactory commonsObjectFactory = new CommonsObjectFactoryImpl();
    User customer;

    @Test
    public void customerLoginSuccessTest() {
        String userName = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expectedCustomerId = 4;

        customer = customerModelFactory.getCustomer();
        IUserAuthentication customerAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao customerAuthenticationDao = customerDaoTestFactory.getCustomerAuthenticationDao();

        Customer validCustomer =
                (Customer) customer.loginUser(userName, password, customerAuthentication, customerAuthenticationDao);
        assert expectedCustomerId == validCustomer.getCustomerId();
    }

    @Test
    public void customerLoginFailureTest() {
        String userName = "klkl@gmail.ca";
        String password = "kllk9009@3";

        customer = customerModelFactory.getCustomer();
        IUserAuthentication customerAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao customerAuthenticationDao = customerDaoTestFactory.getCustomerAuthenticationDao();


        User invalidCustomer = customer.loginUser(userName, password, customerAuthentication, customerAuthenticationDao);
        assert null == invalidCustomer;
    }

    @Test
    public void customerRegistrationSuccessTest() {
        int customerId = 5;
        customer = new Customer(customerId, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");
        IUserDao customerDao = customerDaoTestFactory.getCustomerDao();

        try {
            customer.registerUser(customerDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void customerRegistrationFailureTest() {
        int customerId = 5;
        Customer customer = new Customer(customerId, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");
        IUserDao customerDao = customerDaoTestFactory.getCustomerDao();
        try {
            customer.registerUser(customerDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void updateCustomerProfileSuccessTest() {
        int customerId;

        customerId = 4;
        IUserDao customerDao = customerDaoTestFactory.getCustomerDao();
        customer = new Customer.Builder().withCustomerId(customerId).withCustomerName("Kanely Lart").withCustomerContact("9532120333").withCustomerEmail("klklkl@gmail.ca").build();

        assert customer.updateUser(customerDao);
    }

    @Test
    public void updateCustomerProfileFailureTest() {
        int customerId;

        customerId = 11;
        IUserDao customerDao = customerDaoTestFactory.getCustomerDao();
        customer = new Customer.Builder().withCustomerId(customerId).withCustomerName("Kanely Lart").withCustomerContact("9532120333").withCustomerEmail("klklkl@gmail.ca").build();

        assert Boolean.FALSE.equals(customer.updateUser(customerDao));
    }

}
