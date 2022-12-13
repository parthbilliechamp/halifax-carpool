package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.business.beans.RideToRequestMapper;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.util.*;

public class RideToRequestMapperDaoMockImpl implements IRideToRequestMapperDao {

    public static Map<Integer, Integer> rideToRequestMapMockData = new HashMap<>();
    public static Map<Integer, List<RideRequest>> rideRequestMockData = new HashMap<>();
    public static List<RideToRequestMapper> rideToRequestMapperList = new ArrayList<>();

    public static Map<Integer, List<RideRequest>> rideToApproveRequestMockData = new HashMap<>();

    static {
        populateMockData();
        populateApprovedMockData();
    }
    @Override
    public boolean insertRideToRequestMapper(int rideId, int rideRequestId, String status, double amount) {
        rideToRequestMapMockData.put(rideId, rideRequestId);
        if(rideToRequestMapMockData.get(rideId) != null){
            return true;
        }
        return false;
    }

    @Override
    public List<RideRequest> viewReceivedRequests(int rideId) {
        return rideRequestMockData.get(rideId);
    }

    @Override
    public List<RideRequest> viewRidePassengers(int rideId) {
        return rideToApproveRequestMockData.get(rideId);
    }

    @Override
    public boolean updateRideRequestStatus(int rideId, int rideRequestId, String status) {
        Iterator<RideToRequestMapper> iter = rideToRequestMapperList.iterator();
        while(iter.hasNext()){
            RideToRequestMapper rideToRequestMapper = (RideToRequestMapper) iter.next();
            if(rideToRequestMapper.getRideId() ==rideId && rideToRequestMapper.getRideRequestId()==rideRequestId){
                rideToRequestMapper.setStatus(status);
                return true;
            }
        }
        return false;
    }



    @Override
    public double getPaymentAmount(int rideId, int rideRequestId) {
        Iterator<RideToRequestMapper> iter = rideToRequestMapperList.iterator();
        while(iter.hasNext()){
            RideToRequestMapper data = (RideToRequestMapper) iter.next();
            if(data.getRideId() == rideId && data.getRideRequestId() == rideRequestId){
                return data.getFairPrice();
            }
        }
        return 0.0;
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
        List<RideRequest> rideRequestsList = new ArrayList<>();
        rideRequestsList.add(rideRequest);
        rideRequestMockData.put(rideRequestId, rideRequestsList);
    }

    private static void populateApprovedMockData(){

        RideToRequestMapper mapper = new RideToRequestMapper(1,1,"APPROVED",50.0);
        rideToRequestMapperList.add(mapper);
        RideToRequestMapper map = new RideToRequestMapper(1,4,"APPROVED",33.33);
        rideToRequestMapperList.add(map);
        RideRequest rideRequest = new RideRequest(1,2,"Lane1","Lane2");
        List<RideRequest> list = new ArrayList<>();
        list.add(rideRequest);
        rideToApproveRequestMockData.put(1,list);
        RideToRequestMapper mapper2 = new RideToRequestMapper(2,2,"PENDING");
        RideToRequestMapper mapper3 = new RideToRequestMapper(2,3,"PENDING");
        rideToRequestMapperList.add(mapper2);
        rideToRequestMapperList.add(mapper3);
    }

}
