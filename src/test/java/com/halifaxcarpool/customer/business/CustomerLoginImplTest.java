package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.CustomerAuthenticationImpl;
import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.business.authentication.ICustomer;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.customer.database.dao.ICustomerAuthenticationDao;
import org.junit.jupiter.api.Test;

public class CustomerLoginImplTest {

    ICustomerBusinessObjectFactory customerBusinessObjectFactory = new CustomerBusinessObjectFactoryMain();
    ICustomerDaoObjectFactory customerDaoObjectMockFactory = new CustomerDaoObjectFactoryImplTest();
    ICustomer customerMock;
    ICustomerAuthentication customerAuthenticationMock;

    ICustomerAuthenticationDao customerAuthenticationDao;

    @Test
    public void loginSuccessTest() {

        String username = "klklkl@gmail.ca";
        String password = "kllk9009@3";
        int expected_customer_id = 4;
        Customer extractedCustomer;

        customerMock = customerBusinessObjectFactory.getCustomer();
        customerAuthenticationMock = customerBusinessObjectFactory.getCustomerAuthentication();
        customerAuthenticationDao = customerDaoObjectMockFactory.getCustomerAuthenticationDao();


        extractedCustomer = customerMock.login(username, password, customerAuthenticationMock, customerAuthenticationDao);

        assert expected_customer_id == extractedCustomer.getCustomerId();

    }

    @Test
    public void loginFailureTest() {

        String username = "klklkl1@gmail.ca";
        String password = "kllk90@3";
        Customer extractedCustomer;

        customerMock = customerBusinessObjectFactory.getCustomer();
        customerAuthenticationMock = customerBusinessObjectFactory.getCustomerAuthentication();
        customerAuthenticationDao = customerDaoObjectMockFactory.getCustomerAuthenticationDao();

        extractedCustomer = customerMock.login(username, password, customerAuthenticationMock, customerAuthenticationDao);

        assert extractedCustomer == null;

    }

}
