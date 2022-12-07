package com.halifaxcarpool.customer.database.dao;

import org.junit.jupiter.api.Test;

public class RideRequestsDaoImplTest {

    @Test
    public void cancelRideRequestSuccessTest() {

        int rideRequestId = 4;
        IRideRequestsDao rideRequestsDaoTestObj = new RideRequestsDaoMockImpl();
        rideRequestsDaoTestObj.cancelRideRequest(rideRequestId);

        assert rideRequestsDaoTestObj.viewRideRequests(2).size() == 1;
    }

}
