package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RidesDaoMockImpl implements IRidesDao {

    private static Map<Integer, List<Ride>> mockData = new HashMap<>();

    static {
        populateMockData();
    }

    @Override
    public boolean createRide(Ride ride) {
        return false;
    }

    @Override
    public List<Ride> getRides(int driverId) {
        return mockData.get(driverId);
    }

    private static void populateMockData() {
        int driverId = 1;
        List<Ride> rides = new ArrayList<>();
        rides.add(new Ride(1, driverId, "Barrington st", "Dalhousie", 3, 1, ""));
        rides.add(new Ride(2, driverId, "Park Lane", "Bayers rd.", 1, 0, ""));
        mockData.put(driverId, rides);
        driverId = 2;
        rides = new ArrayList<>();
        rides.add(new Ride(3, driverId, "Citadel", "Gottingen st.", 3, 1, ""));
        rides.add(new Ride(4, driverId, "Halifax Park", "Cunard st.", 4, 1, ""));
        mockData.put(driverId, rides);
    }

}
