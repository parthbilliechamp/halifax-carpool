package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;
import org.junit.jupiter.api.Test;

public class DriverAuthenticationDaoImplTest {

    IDriverAuthenticationDao driverAuthenticationDaoMockObj;

    @Test
    void authenticateDriverTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverAuthenticationDaoMockObj = new DriverAuthenticationDaoMockImpl();

        extractedDriver = driverAuthenticationDaoMockObj.authenticate(username, password);

        assert expected_driver_id == extractedDriver.getDriverId();

    }

    @Test
    void authenticateDriverInvalidUsernameTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";

        String expectedUsername = "simonehot1@gmail.com";
        String expectedPassword = "?isSimoneWell?@123";
        Driver extractedDriver;

        driverAuthenticationDaoMockObj = new DriverAuthenticationDaoMockImpl();

        extractedDriver = driverAuthenticationDaoMockObj.authenticate(username, password);

        assert (expectedUsername != extractedDriver.getDriverEmail() && expectedPassword == extractedDriver.getDriverPassword());
    }

    @Test
    void authenticateDriverInvalidPasswordTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";

        String expectedUsername = "simonehot@gmail.com";
        String expectedPassword = "?isSimone123";
        Driver extractedDriver;

        driverAuthenticationDaoMockObj = new DriverAuthenticationDaoMockImpl();

        extractedDriver = driverAuthenticationDaoMockObj.authenticate(username, password);

        assert (expectedUsername == extractedDriver.getDriverEmail() && expectedPassword != extractedDriver.getDriverPassword());
    }

}
