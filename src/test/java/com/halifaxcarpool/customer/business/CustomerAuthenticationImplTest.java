package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.beans.Customer;
import org.junit.jupiter.api.Test;

public class CustomerAuthenticationImplTest {

    ICustomerAuthentication customerAuthenticationMockObj;

    @Test
    public void authenticateCustomerSuccessTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expected_customer_id = 4;
        Customer extractedCustomer;

        customerAuthenticationMockObj = new CustomerAuthenticationMockImpl();

        extractedCustomer = customerAuthenticationMockObj.authenticate(username, password);

        assert expected_customer_id == extractedCustomer.getCustomerId();
    }

    @Test
    public void authenticateCustomerFailureTest() {

        String username = "klkl@gmail.ca";
        String password = "kllk90@3";
        Customer extractedCustomer;

        customerAuthenticationMockObj = new CustomerAuthenticationMockImpl();

        extractedCustomer = customerAuthenticationMockObj.authenticate(username, password);

        assert extractedCustomer == null;
    }

}
