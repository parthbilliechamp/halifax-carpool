package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RidesDaoMockImpl implements IRidesDao {

    private static final Map<Integer, List<Ride>> driverIdToRideListMap = new HashMap<>();

    private static final Map<Integer, List<Ride>> activeCustomerRidesMap = new HashMap<>();
    private static final Map<Integer, Ride> rideIdToRideMap = new HashMap<>();

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
        if (driverIdToRideListMap.containsKey(driverId)) {
            return driverIdToRideListMap.get(driverId);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Ride> getActiveRides(int customerId) {
        if (activeCustomerRidesMap.containsKey(customerId)) {
            return activeCustomerRidesMap.get(customerId);
        }
        return new ArrayList<>();
    }

    @Override
    public Ride getRide(int rideId) {
        return rideIdToRideMap.get(rideId);
    }

    @Override
    public boolean startRide(int rideId) {
        rideIdToRideMap.get(rideId).setRideStatus(1);
        return true;
    }

    @Override
    public boolean stopRide(int rideId) {
        rideIdToRideMap.get(rideId).setRideStatus(2);
        return  true;
    }

    @Override
    public boolean cancelRide(int rideId) {
        if (rideIdToRideMap.containsKey(rideId)) {
            rideIdToRideMap.remove(rideId);
            return true;
        }
        return false;
    }

    private static void populateMockData() {
        populateDriverToListData();

        populateActiveRidesMapData();

        String startLocationRide1 = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocationRide1 = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        populateRide(1, 1, startLocationRide1, endLocationRide1);

        String startLocationRide2 = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocationRide2 = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        populateRide(2, 5, startLocationRide2, endLocationRide2);

        String startLocationRide3 = "Saint Mary's University, 923 Robie St, Halifax, NS B3H 3C3";
        String endLocationRide3 = "Dalplex, 6260 South St, Halifax, NS B3H 4R2";
        populateRide(12, 10, startLocationRide3, endLocationRide3);

        String startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocation = "6056 University Ave, Halifax, NS B3H";
        populateRide(11, 11, startLocation, endLocation);

        String startLocationRide4 = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocationRide4 = "6056 University Ave, Halifax, NS B3H";
        populateRide(11, 11, startLocationRide4, endLocationRide4);

        String startLocationRide5 = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocationRide5 = "6056 University Ave, Halifax, NS B3H";
        populateRide(13, 11, startLocationRide5, endLocationRide5);

        String startLocationRide6 = "THE TEN SPOT halifax, South Street, Halifax, NS";
        String endLocationRide6 = "The Vuze, Fenwick Tower, Fenwick Street, Halifax, Nova Scotia";
        populateRide(34, 42, startLocationRide6, endLocationRide6);

        String startLocationRide7 = "The Vuze, Fenwick Tower, Fenwick Street, Halifax, Nova Scotia";
        String endLocationRide7 = "Dalplex, South Street, Halifax, Nova Scotia";
        populateRide(36, 46, startLocationRide7, endLocationRide7);

        String startLocationRide8 = "THalifax Backpackers Hostel, Gottingen Street, Halifax, NS";
        String endLocationRide8 = "Fort Needham Memorial Park, Stairs Place, Halifax, NS";
        populateRide(101, 40, startLocationRide8, endLocationRide8);

        String startLocationRide9 = "Fairview Variety Quik-Way, 130 Main Av, Halifax, NS";
        String endLocationRide9 = "Tipico pasta restaurant, Dutch Village Road, Halifax, Nova Scotia";
        populateRide(102, 31, startLocationRide9, endLocationRide9);
    }

    private static void populateDriverToListData() {
        int driverId = 1;
        List<Ride> rides = new ArrayList<>();
        Ride ride = new Ride(1, driverId, "6056 University Ave, Halifax, NS B3H 1W5",
                "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5", 3, 1);
        rides.add(ride);
        ride = new Ride(2, driverId, "Park Lane", "Bayers rd.", 1, 0);
        rides.add(ride);
        driverIdToRideListMap.put(driverId, rides);
        driverId = 2;
        rides = new ArrayList<>();
        rides.add(new Ride(3, driverId, "Citadel", "Gottingen st.", 3, 1));
        rides.add(new Ride(4, driverId, "Halifax Park", "Cunard st.", 4, 1));
        driverIdToRideListMap.put(driverId, rides);
    }

    private static void populateRide(int rideId, int driverId, String startLocation, String endLocation) {
        Ride ride = new Ride(rideId, driverId, startLocation, endLocation, 3, 1);
        rideIdToRideMap.put(rideId, ride);
    }

    private static void insertRideMockData(Ride ride) {
        int driverId = ride.getDriverId();
        List<Ride> rides = new ArrayList<>();
        rides.add(ride);
        driverIdToRideListMap.put(driverId, rides);
    }

    private static void populateActiveRidesMapData() {
        List<Ride> rides = new ArrayList<>();
        Ride ride = new Ride(15, 15, "Citadel",
                "Halifax Park", 4, 1);
        Driver driver = new Driver();
        driver.setDriverName("Hakim");
        driver.setRegisteredVehicleNumber("132");
        ride.withDriver(driver);
        ride.withFare(2.0);

        Ride secondRide = new Ride(16, 16, "Brunswick st.",
                "Halifax Park", 2, 1);
        Driver secondDriver = new Driver();
        driver.setDriverName("Ben");
        driver.setRegisteredVehicleNumber("132");
        ride.withDriver(secondDriver);
        ride.withFare(4.0);
        rides.add(ride);
        rides.add(secondRide);
        activeCustomerRidesMap.put(1, rides);
    }

}
