package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.driver.business.beans.RideToRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

public class RideRequestsDaoMockImpl implements IRideRequestsDao {

    private static final String PENDING = "PENDING";
    private static Map<Integer, List<RideRequest>> mockData = new HashMap<>();
    private static List<RideToRequestMapper> rideToRequestMappersList = new ArrayList<>();

    static {
        populateMockData();
        populateRideRequestMapperData();
    }

    @Override
    public void insertRideRequest(RideRequest rideRequest) {
        insertRideRequestMockData(rideRequest);
    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId) {
        return mockData.get(customerId);
    }

    
    @Override
    public int getRideRequestCount(int rideId) {
        int count = 0;
        Iterator<RideToRequestMapper> iter = rideToRequestMappersList.iterator();
        while(iter.hasNext()){
            RideToRequestMapper ride = (RideToRequestMapper) iter.next();
            if(ride.getRideId() == rideId){
                count +=1;
            }
        }

        return count;
    }

    @Override
    public int getCustomerId(int rideId) {
        int rideRequestId = 0;
        Iterator<RideToRequestMapper> iter = rideToRequestMappersList.iterator();
        while(iter.hasNext()){
            RideToRequestMapper ride = (RideToRequestMapper) iter.next();
            if(ride.getRideId() == rideId){
               rideRequestId = ride.getRideRequestId();
               break;
            }
        }
        for(int key:mockData.keySet()){
            List<RideRequest> rideRequests = mockData.get(key);
            for(int i=0;i<rideRequests.size();i++){
                if(rideRequests.get(i).getRideRequestId() == rideId){
                    return key;
                }
            }
        }

        return 0;

    }

    private static void populateMockData() {
        int customerId = 1;
        List<RideRequest> rideRequests = new ArrayList<>();
        rideRequests.add(new RideRequest(1, customerId, "Barrington st", "Dalhousie"));
        rideRequests.add(new RideRequest(2, customerId, "Oxford st", "Young st"));
        mockData.put(customerId, rideRequests);
        customerId = 2;
        rideRequests = new ArrayList<>();
        rideRequests.add(new RideRequest(3, customerId, "Citadel", "Gottingen st."));
        rideRequests.add(new RideRequest(4, customerId, "Dalhousie", "Park lane"));
        mockData.put(customerId, rideRequests);
        customerId = 5;
        mockData.put(customerId, new ArrayList<>());
    }

    private static  void populateRideRequestMapperData(){
        RideToRequestMapper r1 = new RideToRequestMapper(1,1,PENDING);
        rideToRequestMappersList.add(r1);
        RideToRequestMapper r2 = new RideToRequestMapper(1,3,PENDING);
        rideToRequestMappersList.add((r2));
        RideToRequestMapper r3 = new RideToRequestMapper(2,9,PENDING);
        rideToRequestMappersList.add((r3));
    }

    private static void insertRideRequestMockData(RideRequest rideRequest){
        int customerId = 2;
        List<RideRequest> rideRequests = new ArrayList<>();
        rideRequests.add(rideRequest);
        mockData.put(customerId, rideRequests);
    }

    public void cancelRideRequest(RideRequest rideRequest) {
        int customerId = rideRequest.getCustomerId();
        for(int i = 0; i < mockData.get(customerId).size(); i++) {
            if(mockData.get(customerId).get(i).getRideRequestId() == rideRequest.getRideRequestId()) {
                mockData.get(customerId).remove(i);
            }
        }
    }

    @Override
    public RideRequest getRideRequest(int rideRequestId) {
        RideRequest mockRideRequest = new RideRequest(rideRequestId,1,"Lane1","Lane2");
        return mockRideRequest;
    }

}
