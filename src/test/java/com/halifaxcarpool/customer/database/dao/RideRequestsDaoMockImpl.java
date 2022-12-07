package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RideRequestsDaoMockImpl implements IRideRequestsDao {

    private static Map<Integer, List<RideRequest>> mockData = new HashMap<>();

    static {
        populateMockData();
    }

    @Override
    public void insertRideRequest(RideRequest rideRequest) {
        insertRideRequestMockData(rideRequest);
    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId) {
        return mockData.get(customerId);
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

    private static void insertRideRequestMockData(RideRequest rideRequest){
        int customerId = 2;
        List<RideRequest> rideRequests = new ArrayList<>();
        rideRequests.add(rideRequest);
        mockData.put(customerId, rideRequests);
    }


    public void cancelRideRequest(RideRequest rideRequest) {
        int customerId = rideRequest.customerId;
        for(int i = 0; i < mockData.get(customerId).size(); i++) {
            if(mockData.get(customerId).get(i).rideRequestId == rideRequest.rideRequestId) {
                mockData.get(customerId).remove(i);
            }
        }
    }

}
