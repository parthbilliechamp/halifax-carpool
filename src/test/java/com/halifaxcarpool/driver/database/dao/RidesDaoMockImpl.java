package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;
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
    public void createNewRide(Ride ride) {
        insertRideMockData(ride);
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

    private static void insertRideMockData(Ride ride){
        int driverId = ride.getDriverId();
        List<Ride> rides = new ArrayList<>();
        rides.add(ride);
        mockData.put(driverId, rides);
    }

    public Ride findRide(int rideId, int driverId){
        List<Ride> rides = mockData.get(driverId);
        for(Ride ride : rides){
            if(ride.getRideId() == rideId){
                return ride;
            }
        }
        return new Ride();
    }

}
