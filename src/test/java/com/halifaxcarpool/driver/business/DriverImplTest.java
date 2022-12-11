package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.DriverImpl;
import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IDriverDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriverImplTest {

    IDriver driverObj;

    IDriverDao driverDaoMockObj;

    IDriverDaoObjectFactory driverDaoObjectFactory = new DriverDaoObjectFactoryImplTest();

    IDriverBusinessObjectFactory driverBusinessObjectFactory = new DriverBusinessObjectFactoryMain();


    @Test
    public void registerDriverSuccessTest() {
        int driver_id;

        driver_id = 20;
        Driver driver = new Driver(driver_id, "ab.love@gmail.com", "rekab.aL@654", "9631597562", "Alicia Baker", "OLK-9880", "2024-06-15", "Ford", "F9", "Red", 0);

        driverObj = driverBusinessObjectFactory.getDriver();
        driverDaoMockObj = driverDaoObjectFactory.getDriverDao();

        try {
            driverObj.registerDriver(driver,driverDaoMockObj);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    public void registerDriverFailureTest() {
        int driver_id;

        driver_id = 21;
        Driver driver = new Driver(driver_id, "simonehot@gmail.com", "?isSimoneWell?@123", "9665235146", "Simon Taylor", "KJK-9090", "2026-09-22", "Ford", "Ecosport", "White", 0);

        driverObj = driverBusinessObjectFactory.getDriver();
        driverDaoMockObj = driverDaoObjectFactory.getDriverDao();

        try {
            driverObj.registerDriver(driver,driverDaoMockObj);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }

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