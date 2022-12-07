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
    public boolean createNewRide(Ride ride) {
        insertRideMockData(ride);
        return true;
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
        populateDriverToListData();

        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        populateRide(1, 1, startLocation, endLocation);

        startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        populateRide(2, 5, startLocation, endLocation);

        startLocation = "Saint Mary's University, 923 Robie St, Halifax, NS B3H 3C3";
        endLocation = "Dalplex, 6260 South St, Halifax, NS B3H 4R2";
        populateRide(12, 10, startLocation, endLocation);

        startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        endLocation = "6056 University Ave, Halifax, NS B3H";
        populateRide(11, 11, startLocation, endLocation);

        startLocation = "THE TEN SPOT halifax, South Street, Halifax, NS";
        endLocation = "The Vuze, Fenwick Tower, Fenwick Street, Halifax, Nova Scotia";
        populateRide(34, 42, startLocation, endLocation);

        startLocation = "The Vuze, Fenwick Tower, Fenwick Street, Halifax, Nova Scotia";
        endLocation = "Dalplex, South Street, Halifax, Nova Scotia";
        populateRide(36, 46, startLocation, endLocation);

        System.out.println("done");
    }

    private static void populateDriverToListData() {
        int driverId = 1;
        List<Ride> rides = new ArrayList<>();
        Ride ride = new Ride(1, driverId, "6056 University Ave, Halifax, NS B3H 1W5",
                "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5", 3, 1, "");
        rides.add(ride);
        ride = new Ride(2, driverId, "Park Lane", "Bayers rd.", 1, 0, "");
        rides.add(ride);
        driverIdToRideListMap.put(driverId, rides);
        driverId = 2;
        rides = new ArrayList<>();
        rides.add(new Ride(3, driverId, "Citadel", "Gottingen st.", 3, 1, ""));
        rides.add(new Ride(4, driverId, "Halifax Park", "Cunard st.", 4, 1, ""));
        driverIdToRideListMap.put(driverId, rides);
    }

    private static void populateRide(int rideId, int driverId, String startLocation, String endLocation) {
        Ride ride = new Ride(rideId, driverId, startLocation, endLocation, 3, 1, "");
        rideIdToRideMap.put(rideId, ride);
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
