package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.customer.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class CustomerTest {

    @Test
    public void customerRegistrationSuccessTest() {
        int customerId = 5;
        //TODO use builder
        Customer customer = new Customer(customerId, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");
        IUserDao customerDao = new CustomerDaoMockImpl();
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
        //TODO use builder
        Customer customer = new Customer(customerId, "Jake Firl", "8856041798", "jakeacceptsall@gmail.com", "rocker233<!>");
        IUserDao customerDao = new CustomerDaoMockImpl();
        try {
            customer.registerUser(customerDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void customerLoginSuccessTest() {
        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expectedCustomerId = 4;
        Customer customer = new Customer();

        IUserAuthentication customerAuthentication = new UserAuthenticationImpl();
        IUserAuthenticationDao customerAuthenticationDao = new CustomerAuthenticationDaoMockImpl();

        Customer validCustomer =
                (Customer) customer.loginUser(username, password, customerAuthentication, customerAuthenticationDao);
        assert expectedCustomerId == validCustomer.getCustomerId();
    }

    @Test
    public void customerLoginFailureTest() {
        String username = "klkl@gmail.ca";
        String password = "kllk9009@3";
        Customer customer = new Customer();

        IUserAuthentication customerAuthentication = new UserAuthenticationImpl();
        IUserAuthenticationDao driverAuthenticationDao = new CustomerAuthenticationDaoImpl();

        User invalidCustomer = customer.loginUser(username, password, customerAuthentication, driverAuthenticationDao);
        assert null == invalidCustomer;
    }

    @Test
    public void modifyCustomerProfileSuccessTest() {
        //TODO implement this test case
    }

    @Test
    public void modifyCustomerProfileFailureTest() {
        //TODO implement this test case
    }


}
