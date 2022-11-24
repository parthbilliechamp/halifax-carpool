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
    public List<Ride> getRides(int rideId) {
        return mockData.get(rideId);
    }

    @Override
    public Ride getRide(int rideId) {
        return null;
    }

    private static void populateMockData() {
        int rideId = 1;
        List<Ride> rides = new ArrayList<>();
        rides.add(new Ride(rideId, 1, "Barrington st", "Dalhousie", 3, 1, ""));
        rides.add(new Ride(rideId, 2, "Park Lane", "Bayers rd.", 1, 0, ""));
        mockData.put(rideId, rides);
        rideId = 2;
        rides = new ArrayList<>();
        rides.add(new Ride(rideId, 3, "Citadel", "Gottingen st.", 3, 1, ""));
        rides.add(new Ride(rideId, 4, "Halifax Park", "Cunard st.", 4, 1, ""));
        mockData.put(rideId, rides);
    }

}
