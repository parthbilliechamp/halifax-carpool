package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.authentication.IDriverLogin;
import com.halifaxcarpool.driver.business.beans.Driver;
import org.junit.jupiter.api.Test;

public class DriverLoginImplTest {

    IDriverLogin driverLoginMock;
    IDriverAuthentication driverAuthenticationMock;

    @Test
    public void loginSuccessTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverLoginMock = new DriverLoginMockImpl();
        driverAuthenticationMock = new DriverAuthenticationMockImpl();

        extractedDriver = driverLoginMock.login(username, password, driverAuthenticationMock);

        assert expected_driver_id == extractedDriver.getDriver_id();

    }

    @Test
    public void loginFailureTest() {

        String username = "simone@gmail.com";
        String password = "?isSimone?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverLoginMock = new DriverLoginMockImpl();
        driverAuthenticationMock = new DriverAuthenticationMockImpl();

        extractedDriver = driverLoginMock.login(username, password, driverAuthenticationMock);

        assert extractedDriver == null;

    }

}
