package com.halifaxcarpool.driver.business;


import com.halifaxcarpool.commons.business.CommonsObjectFactoryImpl;
import com.halifaxcarpool.commons.business.ICommonsObjectFactory;
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

    DriverModelFactory driverModelFactory = new DriverModelMainFactory();
    DriverDaoFactory driverDaoTestFactory = new DriverDaoTestFactory();
    ICommonsObjectFactory commonsObjectFactory = new CommonsObjectFactoryImpl();

    User driver;

    @Test
    public void driverLoginSuccessTest() {
        Driver driver = new Driver();

        String username = "simonehot@gmail.com";
        String password = "?isSimoneWell?@123";
        int expectedDriverId = 21;

        IUserAuthentication driverAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao driverAuthenticationDao = driverDaoTestFactory.getDriverAuthenticationDao();

        Driver validDriver = (Driver) driver.loginUser(username, password, driverAuthentication, driverAuthenticationDao);
        assert expectedDriverId == validDriver.getDriverId();
    }

    @Test
    public void driverLoginFailureTest() {
        Driver driver = new Driver();

        String username = "simone@gmail.com";
        String password = "?isSimoneWell?@123";

        IUserAuthentication driverAuthentication = commonsObjectFactory.authenticateUser();
        IUserAuthenticationDao driverAuthenticationDao = driverDaoTestFactory.getDriverAuthenticationDao();

        User invalidDriver = driver.loginUser(username, password, driverAuthentication, driverAuthenticationDao);
        assert null == invalidDriver;
    }

    @Test
    public void driverRegistrationSuccessTest() {

        int driverId = 20;
        driver = new Driver(driverId, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);
        IUserDao driverDao = driverDaoTestFactory.getDriverDao();

        try {
            driver.registerUser(driverDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void driverRegistrationFailureTest() {
        int driver_id = 21;
        Driver driver = new Driver(driver_id, "simonehot@gmail.com", "?isSimoneWell?@123", "9665235146", "Simon Taylor", "KJK-9090", "2026-09-22", "Ford", "Ecosport", "White", 0);
        IUserDao driverDao = driverDaoTestFactory.getDriverDao();

        try {
            driver.registerUser(driverDao);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    public void modifyDriverProfileSuccessTest() {
        IUserDao driverDao = driverDaoTestFactory.getDriverDao();
        Driver driver = new Driver.Builder()
                .withDriverId(1)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();

        assert driver.updateUser(driverDao);
    }

    @Test
    public void modifyDriverProfileFailureTest() {
        IUserDao driverDao = driverDaoTestFactory.getDriverDao();
        Driver driver = new Driver.Builder()
                .withDriverId(6)
                .withDriverName("Lis")
                .withDriverEmail("main@gmail.com")
                .build();

        assert Boolean.FALSE.equals(driver.updateUser(driverDao));
    }

}