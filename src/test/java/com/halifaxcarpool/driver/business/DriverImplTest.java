package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.DriverImpl;
import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IDriverDao;
import org.junit.jupiter.api.Test;

public class DriverImplTest {

    IDriver driverLoginMock;
    IDriverAuthentication driverAuthenticationMock;

    @Test
    public void loginSuccessTest() {

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverLoginMock = new DriverMockImpl();
        driverAuthenticationMock = new DriverAuthenticationMockImpl();

        extractedDriver = driverLoginMock.login(username, password, driverAuthenticationMock);

        assert expected_driver_id == extractedDriver.getDriverId();

    }

    @Test
    public void loginFailureTest() {

        String username = "simone@gmail.com";
        String password = "?isSimone?@123";
        int expected_driver_id = 21;
        Driver extractedDriver;

        driverLoginMock = new DriverMockImpl();
        driverAuthenticationMock = new DriverAuthenticationMockImpl();

        extractedDriver = driverLoginMock.login(username, password, driverAuthenticationMock);

        assert extractedDriver == null;

    }

    @Test
    public void modifyDriverProfileSuccessTest() {
        IDriver driver = new DriverImpl();
        IDriverDao driverDao = new DriverDaoMockImpl();
        Driver driverProfile = new Driver.Builder()
                .withDriverId(1)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();
        assert driver.update(driverProfile, driverDao);
    }

    @Test
    public void modifyDriverProfileFailureTest() {
        IDriver driver = new DriverImpl();
        IDriverDao driverDao = new DriverDaoMockImpl();
        Driver driverProfile = new Driver.Builder()
                .withDriverId(34)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();
        assert Boolean.FALSE.equals(driver.update(driverProfile, driverDao));
    }

}