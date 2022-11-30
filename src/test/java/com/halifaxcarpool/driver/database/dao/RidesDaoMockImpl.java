package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RidesDaoMockImpl implements IRidesDao {

    private static Map<Integer, List<Ride>> driverIdToRideListMap = new HashMap<>();
    private static Map<Integer, Ride> rideIdToRideMap = new HashMap<>();

    static {
        populateMockData();
    }

    @Override
    public void createNewRide(Ride ride) {
        insertRideMockData(ride);
    }

    @Override
    public List<Ride> getRides(int driverId) {
        return driverIdToRideListMap.get(driverId);
    }

    @Override
    public Ride getRide(int rideId) {
        return rideIdToRideMap.get(rideId);
    }

    private static void populateMockData() {
        int driverId = 1;
        List<Ride> rides = new ArrayList<>();
        Ride ride = new Ride(1, driverId, "6056 University Ave, Halifax, NS B3H 1W5",
                "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5", 3, 1, "");
        rides.add(ride);
        rideIdToRideMap.put(1, ride);
        ride = new Ride(2, driverId, "Park Lane", "Bayers rd.", 1, 0, "");
        rides.add(ride);
        driverIdToRideListMap.put(driverId, rides);
        driverId = 2;
        rides = new ArrayList<>();
        rides.add(new Ride(3, 3, "Citadel", "Gottingen st.", 3, 1, ""));
        rides.add(new Ride(4, 4, "Halifax Park", "Cunard st.", 4, 1, ""));
        driverIdToRideListMap.put(driverId, rides);
    }

    private static void insertRideMockData(Ride ride){
        int driverId = ride.getDriverId();
        List<Ride> rides = new ArrayList<>();
        rides.add(ride);
        driverIdToRideListMap.put(driverId, rides);
    }

    public Ride findRide(int rideId, int driverId){
        List<Ride> rides = driverIdToRideListMap.get(driverId);
        for(Ride ride : rides){
            if(ride.getRideId() == rideId){
                return ride;
            }
        }
        return new Ride();
    }

}
