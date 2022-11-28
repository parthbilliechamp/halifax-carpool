package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.Customer;
import org.junit.jupiter.api.Test;

public class CustomerAuthenticationDaoImplTest {

    ICustomerAuthenticationDao customerAuthenticationDaoMockObject;

    @Test
    void authenticateCustomerTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expected_customer_id = 4;
        Customer extractedCustomer;

        customerAuthenticationDaoMockObject = new CustomerAuthenticationDaoMockImpl();

        extractedCustomer = customerAuthenticationDaoMockObject.authenticate(username, password);

        assert expected_customer_id == extractedCustomer.getCustomerId();
    }

    @Test
    void authenticateCustomerInvalidUsernameTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";

        String expectedUsername = "kl2000@gmail.ca";
        String expectedPassword = "kllk9009@3";
        Customer extractedCustomer;

        customerAuthenticationDaoMockObject = new CustomerAuthenticationDaoMockImpl();

        extractedCustomer = customerAuthenticationDaoMockObject.authenticate(username, password);

        assert (expectedUsername != extractedCustomer.getCustomerEmail() && expectedPassword == extractedCustomer.getCustomerPassword());
    }

    @Test
    void authenticateCustomerInvalidPasswordTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";

        String expectedUsername = "klklkl@gmail.ca";
        String expectedPassword = "kllk9009//";
        Customer extractedCustomer;

        customerAuthenticationDaoMockObject = new CustomerAuthenticationDaoMockImpl();

        extractedCustomer = customerAuthenticationDaoMockObject.authenticate(username, password);

        assert (expectedUsername == extractedCustomer.getCustomerEmail() && expectedPassword != extractedCustomer.getCustomerPassword());
    }

}
