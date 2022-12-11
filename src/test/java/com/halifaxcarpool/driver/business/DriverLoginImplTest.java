package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import org.junit.jupiter.api.Test;

public class DriverLoginImplTest {

    IDriverBusinessObjectFactory driverBusinessObjectFactory = new DriverBusinessObjectFactoryMain();
    IDriverDaoObjectFactory driverDaoObjectFactory = new DriverDaoObjectFactoryImplTest();
    IDriver driverMock;
    IDriverAuthentication driverAuthenticationMock;
    IDriverAuthenticationDao driverAuthenticationDao;

    @Test
    public void loginSuccessTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverMock = driverBusinessObjectFactory.getDriver();
        driverAuthenticationMock = driverBusinessObjectFactory.getDriverAuthentication();
        driverAuthenticationDao = driverDaoObjectFactory.getDriverAuthenticationDao();

        extractedDriver = driverMock.login(username, password, driverAuthenticationMock, driverAuthenticationDao);

        assert expected_driver_id == extractedDriver.getDriverId();

    }

    @Test
    public void loginFailureTest() {

        String username = "simone@gmail.com";
        String password = "?isSimone?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverMock = driverBusinessObjectFactory.getDriver();
        driverAuthenticationMock = driverBusinessObjectFactory.getDriverAuthentication();
        driverAuthenticationDao = driverDaoObjectFactory.getDriverAuthenticationDao();

        extractedDriver = driverMock.login(username, password, driverAuthenticationMock, driverAuthenticationDao);

        assert extractedDriver == null;

    }



}
