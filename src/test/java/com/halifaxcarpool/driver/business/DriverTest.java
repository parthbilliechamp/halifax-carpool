package com.halifaxcarpool.driver.business;


import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.business.authentication.IUserAuthentication;
import com.halifaxcarpool.commons.business.authentication.UserAuthenticationImpl;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.DriverDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class DriverTest {

    @Test
    public void driverRegistrationSuccessTest() {
        User driverUser = new Driver();
        IUserDao driverDao = new DriverDaoMockImpl();
        driverUser.registerUser(driverDao);
    }

    @Test
    public void driverRegistrationFailureTest() {
        int driverId = 20;
        //TODO use builder
        Driver driver = new Driver(driverId, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);
        IUserDao driverDao = new DriverDaoMockImpl();
        try {
            driver.registerUser(driverDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void driverLoginSuccessTest() {
        Driver driver = new Driver();

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expectedDriverId = 21;

        IUserAuthentication driverAuthentication = new UserAuthenticationImpl();
        IUserAuthenticationDao driverAuthenticationDao = new DriverAuthenticationDaoMockImpl();

        Driver validDriver = (Driver) driver.loginUser(username, password, driverAuthentication, driverAuthenticationDao);
        assert expectedDriverId == validDriver.getDriverId();
    }

    @Test
    public void driverLoginFailureTest() {
        Driver driver = new Driver();

        String username = "simone@gmail.com";
        String password = "?isSimoneWell?@123";

        IUserAuthentication driverAuthentication = new UserAuthenticationImpl();
        IUserAuthenticationDao driverAuthenticationDao = new DriverAuthenticationDaoMockImpl();

        User invalidDriver = driver.loginUser(username, password, driverAuthentication, driverAuthenticationDao);
        assert null == invalidDriver;
    }

    @Test
    public void modifyDriverProfileSuccessTest() {
        IUserDao driverDao = new DriverDaoMockImpl();
        Driver driver = new Driver.Builder()
                .withDriverId(1)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();

        assert driver.updateUser(driverDao);
    }

    @Test
    public void modifyDriverProfileFailureTest() {
        IUserDao driverDao = new DriverDaoMockImpl();
        Driver driver = new Driver.Builder()
                .withDriverId(6)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();

        assert Boolean.FALSE.equals(driver.updateUser(driverDao));
    }

}
