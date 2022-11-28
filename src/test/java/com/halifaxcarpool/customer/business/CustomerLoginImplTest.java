package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomerLogin;
import com.halifaxcarpool.customer.business.beans.Customer;
import org.junit.jupiter.api.Test;

public class CustomerLoginImplTest {

    ICustomerLogin customerLoginMock;
    ICustomerAuthentication customerAuthenticationMock;

    @Test
    public void loginSuccessTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expected_customer_id = 4;
        Customer extractedCustomer;

        customerLoginMock = new CustomerLoginMockImpl();
        customerAuthenticationMock = new CustomerAuthenticationMockImpl();

        extractedCustomer = customerLoginMock.login(username, password, customerAuthenticationMock);

        assert expected_customer_id == extractedCustomer.getCustomerId();

    }

    @Test
    public void loginFailureTest() {

        String username = "klklkl1@gmail.ca";
        String password = "kllk90@3";
        Customer extractedCustomer;

        customerLoginMock = new CustomerLoginMockImpl();
        customerAuthenticationMock = new CustomerAuthenticationMockImpl();

        extractedCustomer = customerLoginMock.login(username, password, customerAuthenticationMock);

        assert extractedCustomer == null;

    }

}
