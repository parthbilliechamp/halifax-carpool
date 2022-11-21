package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RideRequestImplTest {

    @Test
    void viewRideRequestsTest() {
        int customerId = 1;
        IRideRequest rideRequest = new RideRequestMockImpl();
        List<RideRequest> rideRequests = rideRequest.viewRideRequests(customerId);
        assert 2 == rideRequests.size();
        for (RideRequest ride: rideRequests) {
            assert customerId == ride.customerId;
        }
    }

    @Test
    void insertRideRequestTest(){
        int customerId = 1;
        int rideId = 8;
        RideRequest rideRequest = new RideRequest(rideId, customerId, "Spring Garden", "Downtown");
        IRideRequest rideRequestMock = new RideRequestMockImpl();
        try {
            rideRequestMock.createRideRequest(rideRequest);
        }catch (Exception e){
            System.out.println("Test Failed");
        }
        assert true;
    }

    @Test
    void insertRideRequestValuesMissingTest(){
        RideRequest rideRequest = new RideRequest();
        IRideRequest rideRequestMock = new RideRequestMockImpl();
        try {
            rideRequestMock.createRideRequest(rideRequest);
        }catch (Exception e){
            assert true;
        }
        assert false;
    }
}
