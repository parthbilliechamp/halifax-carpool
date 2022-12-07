package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class RideRequestImplTest {

    IRideRequest rideRequest = new RideRequestImpl();
    IRideRequestsDao rideRequestsDao = new RideRequestsDaoMockImpl();

    @Test
    void viewRideRequestsTest() {
        int customerId = 1;
        List<RideRequest> rideRequests = rideRequest.viewRideRequests(customerId, rideRequestsDao);
        assert 2 == rideRequests.size();
        for (RideRequest ride : rideRequests) {
            assert customerId == ride.customerId;
        }
    }

    @Test
    void viewRideRequestsNoDataReturnedTest() {
        int customerId = 5;
        List<RideRequest> rideRequests = rideRequest.viewRideRequests(customerId, rideRequestsDao);
        assert 0 == rideRequests.size();
    }

    @Test
    void insertRideRequestTest() {
        int customerId = 1;
        int rideId = 8;
        RideRequest rideRequestObject = new RideRequest(rideId, customerId, "Spring Garden", "Downtown");

        try {
            rideRequest.createRideRequest(rideRequestObject, rideRequestsDao);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void insertRideRequestValuesMissingTest() {
        RideRequest rideRequestObject = new RideRequest();
        try {
            rideRequest.createRideRequest(rideRequestObject, rideRequestsDao);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void cancelRideRequestSuccessTest() {
        int rideRequestId = 4;
        int customerId = 2;
        RideRequest rideRequestObj = new RideRequest();
        rideRequestObj.setRideRequestId(rideRequestId);
        rideRequestObj.setCustomerId(customerId);
        try {
            rideRequest.cancelRideRequest(rideRequestObj, rideRequestsDao);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void cancelRideRequestFailureTest() {
        int rideRequestId = 9;
        int customerId = 6;
        RideRequest rideRequestObj = new RideRequest();
        rideRequestObj.setRideRequestId(rideRequestId);
        rideRequestObj.setCustomerId(customerId);
        try {
            rideRequest.cancelRideRequest(rideRequestObj, rideRequestsDao);
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(true);
        }
    }

}
