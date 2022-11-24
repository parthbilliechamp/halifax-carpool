package com.halifaxcarpool.driver.database.dao;


import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverRegistrationDaoMockImpl implements IDriverRegistrationDao {

    private static Map<Integer, Driver> mockDriverData = new HashMap<>();

    static {
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

    @Override
    public void registerDriver(Driver driver) {
        int driver_id;

        driver_id = 20;
        mockDriverData.put(driver_id, driver);
    }

    public Driver findDriverDetailsFromHashMap(int driver_id) {

        if (mockDriverData.containsKey(driver_id)) {
            return mockDriverData.get(driver_id);
        }
        return new Driver();
    }
}
