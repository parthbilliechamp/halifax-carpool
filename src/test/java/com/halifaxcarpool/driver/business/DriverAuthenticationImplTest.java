package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.beans.Driver;
import org.junit.jupiter.api.Test;

public class DriverAuthenticationImplTest {

    IDriverAuthentication driverAuthenticationMockObj;

    @Test
    public void authenticateCustomerSuccessTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverAuthenticationMockObj = new DriverAuthenticationMockImpl();

        extractedDriver = driverAuthenticationMockObj.authenticate(username, password);

        assert expected_driver_id == extractedDriver.getDriver_id();

    }

    @Test
    public void authenticateCustomerFailureTest() {

        String username = "simone@gmail.com";
        String password = "?isSimoneWel?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverAuthenticationMockObj = new DriverAuthenticationMockImpl();

        extractedDriver = driverAuthenticationMockObj.authenticate(username, password);

        assert extractedDriver == null;

    }

}
