package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverDaoMockImpl implements IDriverDao {

    private static final Map<Integer, Driver> driverIdToDriverMap = new HashMap<>();

    static {
        populateMockData();
    }

    @Override
    public boolean updateDriverProfile(Driver driver) {
        int driverId = driver.getDriverId();
        if (driverIdToDriverMap.containsKey(driverId)) {
            driverIdToDriverMap.put(driverId, driver);
            return true;
        }
        return false;
    }

    private static void populateMockData() {

        int driver1Id = 1;
        String driver1Email = "test1@gmail.com";
        String driver1Name = "John";
        Driver driver1 = new Driver.Builder()
                .withDriverId(driver1Id)
                .withDriverEmail(driver1Email)
                .withDriverName(driver1Name)
                .build();
        driverIdToDriverMap.put(driver1Id, driver1);

        int driver2Id = 2;
        String driver2Email = "test2@gmail.com";
        String driver2Name = "Kevin";
        Driver driver2 = new Driver.Builder()
                .withDriverId(driver2Id)
                .withDriverEmail(driver2Email)
                .withDriverName(driver2Name)
                .build();
        driverIdToDriverMap.put(driver2Id, driver2);

        int driver3Id = 3;
        String driver3Email = "test3@gmail.com";
        String driver3Name = "Henry";
        Driver driver3 = new Driver.Builder()
                .withDriverId(driver3Id)
                .withDriverEmail(driver3Email)
                .withDriverName(driver3Name)
                .build();
        driverIdToDriverMap.put(driver3Id, driver3);
    }
}
