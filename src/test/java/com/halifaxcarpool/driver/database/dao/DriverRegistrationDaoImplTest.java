package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;
import org.junit.jupiter.api.Test;

public class DriverRegistrationDaoImplTest {

    @Test
    void registerDriverTest() {
        int driver_id;
        Driver driverExtracted;

        driver_id = 20;
        Driver driver = new Driver(driver_id, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);

        IDriverRegistrationDao driverRegistrationMockObject = new DriverRegistrationDaoMockImpl();

        driverRegistrationMockObject.registerDriver(driver);

        driverExtracted = ((DriverRegistrationDaoMockImpl) driverRegistrationMockObject).findDriverDetailsFromHashMap(driver_id);

        assert driver_id == driverExtracted.getDriverId();
    }

    @Test
    void registerDriverInvalidInputTest() {

        int entered_driver_id;
        int expected_driver_id;
        Driver driverExtracted;

        entered_driver_id = 20;
        expected_driver_id = 6;
        Driver driver = new Driver(entered_driver_id, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);

        IDriverRegistrationDao driverRegistrationMockObject = new DriverRegistrationDaoMockImpl();

        driverRegistrationMockObject.registerDriver(driver);

        driverExtracted = ((DriverRegistrationDaoMockImpl) driverRegistrationMockObject).findDriverDetailsFromHashMap(entered_driver_id);

        assert expected_driver_id != driverExtracted.getDriverId();

    }

}
