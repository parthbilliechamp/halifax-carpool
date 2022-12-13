package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverAuthenticationDaoMockImpl implements IUserAuthenticationDao {

    private static final Map<List<String>, Driver> mockDriverData = new HashMap<>();
    private static final List<String> emailAndPasswordList = new ArrayList<>();

    static {
        populateMockDriverData();
    }

    private static void populateMockDriverData() {

        int driver_id = 1;
        emailAndPasswordList.clear();
        emailAndPasswordList.add("tt2022@yahoo.com");
        emailAndPasswordList.add("8116ae645372e0406df2714ce9a86e7a");
        mockDriverData.put(emailAndPasswordList, new Driver(driver_id, "tt2022@yahoo.com", "tabltennistoplay?", "7855423322", "Tyson Tale", "HNS-8796", "2023-11-28", "Accura", "G6", "Black", 0));

        driver_id = 21;
        emailAndPasswordList.clear();
        emailAndPasswordList.add("simonehot@gmail.com");
        emailAndPasswordList.add("00281ad04fb4ef4213ed89661ecd9d35");
        mockDriverData.put(emailAndPasswordList, new Driver(driver_id, "simonehot@gmail.com", "?isSimoneWell?@123", "9665235146", "Simon Taylor", "KJK-9090", "2026-09-22", "Ford", "Ecosport", "White", 0));

        driver_id = 22;
        emailAndPasswordList.clear();
        emailAndPasswordList.add("hevans.c@gmail.com");
        emailAndPasswordList.add("46a290d7e6d1e697e2b8ee845ed29016");
        mockDriverData.put(emailAndPasswordList, new Driver(driver_id, "hevans.c@gmail.com", "gtShmc10097@.", "855265683", "Chris Hevans", "BUS-1040", "2026-01-31", "Honda", "CRV", "Silver", 0));

        driver_id = 15;
        emailAndPasswordList.clear();
        emailAndPasswordList.add("jrd.gil@gmail.com");
        emailAndPasswordList.add("4d8008b8c402b15147bbe98d54660f95");
        mockDriverData.put(emailAndPasswordList, new Driver(driver_id, "jrd.gil@gmail.com", "jrdmom199>/", "9633214531", "Jordan Gillis", "PIL-6165", "2027-05-19", "Toyota", "Corolla", "White", 0));
    }

    @Override
    public Driver authenticate(String username, String password) {
        emailAndPasswordList.clear();
        emailAndPasswordList.add(username);
        emailAndPasswordList.add(password);

        return mockDriverData.get(emailAndPasswordList);
    }

}
