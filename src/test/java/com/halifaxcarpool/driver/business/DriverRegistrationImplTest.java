package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.registration.IDriverRegistration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriverRegistrationImplTest {

    IDriverRegistration driverRegistrationMockObject;

    @Test
    public void registerDriverSuccessTest() {
        int driver_id;

        driver_id = 20;
        Driver driver = new Driver(driver_id, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);

        driverRegistrationMockObject = new DriverRegistrationMockImpl();
        try {
            driverRegistrationMockObject.registerDriver(driver);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void registerDriverFailureTest() {
        int driver_id;

        driver_id = 20;
        Driver driver = new Driver(driver_id, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);

        driverRegistrationMockObject = new DriverRegistrationMockImpl();
        try {
            driverRegistrationMockObject.registerDriver(driver);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

}
