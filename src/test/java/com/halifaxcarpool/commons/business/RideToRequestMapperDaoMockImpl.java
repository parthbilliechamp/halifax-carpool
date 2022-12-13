package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.driver.business.beans.RideToRequestMapper;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.*;

public class RideToRequestMapperDaoMockImpl implements IRideToRequestMapperDao {

    public static Map<Integer, Integer> rideToRequestMapMockData = new HashMap<>();
    public static Map<Integer, List<RideRequest>> rideRequestMockData = new HashMap<>();

    public static Map<Integer, List<RideToRequestMapper>> rideToRequestMockData = new HashMap<>();

    static {
        populateMockData();
        RideToRequestMapper ride1 = new RideToRequestMapper(1,5,"PENDING");
        RideToRequestMapper ride2 = new RideToRequestMapper(2,7,"APPROVED",50.0);
        List<RideToRequestMapper> rideToRequestMapperList = new ArrayList<>();
        rideToRequestMapperList.add(ride1);
        rideToRequestMapperList.add(ride2);
        rideToRequestMockData.put(1,rideToRequestMapperList);
    }
    @Override
    public void insertRideToRequestMapper(int rideId, int rideRequestId, String status, double amount) {
        rideToRequestMapMockData.put(rideId, rideRequestId);
    }

    @Override
    public List<RideRequest> viewReceivedRequests(int rideId) {
        return rideRequestMockData.get(rideId);
    }

    @Override
    public List<RideRequest> viewRidePassengers(int rideId) {
        List<RideRequest> result = new ArrayList<>();
        Iterator<RideToRequestMapper> iter = rideToRequestMockData.get(rideId).iterator();
        while(iter.hasNext()){
            RideToRequestMapper ride = (RideToRequestMapper) iter.next();
            if(ride.getStatus() == "ACCEPTED"){
                //result.add(rideToRequestMockData.get(ride.getRideRequestId()));
            }
        }
        return result;
    }

    @Override
    public void updateRideRequestStatus(int rideId, int rideRequestId, String status) {

    }



    @Override
    public void updatePaymentAmount(int rideId, int rideRequestId, double amount) {

    }

    @Override
    public double getPaymentAmount(int rideId, int rideRequestId) {
        return 0;
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
