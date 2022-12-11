package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.*;

public class RideToRequestMapperDaoMockImpl implements IRideToRequestMapperDao {

    public static Map<Integer, Integer> rideToRequestMapMockData = new HashMap<>();
    public static Map<Integer, List<RideRequest>> rideRequestMockData = new HashMap<>();

    static {
        populateMockData();
    }
    @Override
    public void insertRideToRequestMapper(int rideId, int rideRequestId, String status) {
        rideToRequestMapMockData.put(rideId, rideRequestId);
    }

    @Override
    public List<RideRequest> viewReceivedRequests(int rideId) {
        return rideRequestMockData.get(rideId);
    }

    private static void populateMockData() {
        int rideId = 1;
        int rideRequestId = 5;
        int customerId = 1;
        String startLocation = "Lane 1";
        String endLocation = "Lane 2";
        rideToRequestMapMockData.put(rideId, rideRequestId);
        RideRequest rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
        List<RideRequest> list = new ArrayList<>();
        list.add(rideRequest);
        rideRequestMockData.put(rideRequestId, new ArrayList<>(list));
        rideId = 2;
        rideRequestId = 7;
        rideToRequestMapMockData.put(rideId, rideRequestId);
        startLocation = "Lane 3";
        endLocation = "Lane 4";
        rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
        list = new ArrayList<>();
        list.add(rideRequest);
        rideRequestMockData.put(rideRequestId, new ArrayList<>(list));
    }

}
