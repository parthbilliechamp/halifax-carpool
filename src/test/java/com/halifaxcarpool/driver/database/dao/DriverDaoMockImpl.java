package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.business.beans.Customer;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverDaoMockImpl extends IUserDao {

    private static final Map<Integer, Driver> driverIdToDriverMap = new HashMap<>();

    private static final Map<Integer, Driver> mockDriverData = new HashMap<>();

    static {
        populateMockData();
        populateMockDriverData();
    }


    private static void populateMockDriverData() {

        int driver_id = 1;
        mockDriverData.put(driver_id, new Driver(driver_id, "tt2022@yahoo.com", "tabltennistoplay?", "7855423322", "Tyson Tale", "HNS-8796", "2023-11-28", "Accura", "G6", "Black", 0));

        driver_id = 21;
        mockDriverData.put(driver_id, new Driver(driver_id, "simonehot@gmail.com", "?isSimoneWell?@123", "9665235146", "Simon Taylor", "KJK-9090", "2026-09-22", "Ford", "Ecosport", "White", 0));

        driver_id = 22;
        mockDriverData.put(driver_id, new Driver(driver_id, "hevans.c@gmail.com", "gtShmc10097@.", "855265683", "Chris Hevans", "BUS-1040", "2026-01-31", "Honda", "CRV", "Silver", 0));

        driver_id = 15;
        mockDriverData.put(driver_id, new Driver(driver_id, "jrd.gil@gmail.com", "jrdmom199>/", "9633214531", "Jordan Gillis", "PIL-6165", "2027-05-19", "Toyota", "Corolla", "White", 0));

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

    @Override
    public void registerUser(User user) throws Exception {
        Driver driver = (Driver) user;
        int driverId;
        driverId = driver.getDriverId();
        if(mockDriverData.containsKey(driverId)) {
            throw new Exception("Driver Already Exists");
        }
        mockDriverData.put(driverId, driver);
        System.out.println(mockDriverData);
    }


    @Override
    public boolean updateUser(User user) {
        Driver driverUser = (Driver) user;
        int driverId = driverUser.getDriverId();

        if (driverIdToDriverMap.containsKey(driverId)) {
            driverIdToDriverMap.put(driverId, driverUser);
            return true;
        }
        return false;
    }

}
