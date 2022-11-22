package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.customer.business.IRideRequest;
import com.halifaxcarpool.customer.business.RideRequestMockImpl;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RideRequestsDaoImplTest {

    @Test
    void viewRideRequestsTest() {
        int customerId = 1;
        IRideRequestsDao rideRequestsDao = new RideRequestsDaoMockImpl();
        List<RideRequest> rideRequests = rideRequestsDao.viewRideRequests(customerId);
        assert 2 == rideRequests.size();
        for (RideRequest rideRequest: rideRequests) {
            assert customerId == rideRequest.customerId;
        }
    }

    @Test
    void insertRideRequestTest(){
        int customerId = 1;
        int rideId = 8;
        RideRequest rideRequest = new RideRequest(rideId, customerId, "Spring Garden", "Downtown");
        IRideRequestsDao rideRequestMock = new RideRequestsDaoMockImpl();

        rideRequestMock.insertRideRequest(rideRequest);
        RideRequest rideRequestNew = ((RideRequestsDaoMockImpl) rideRequestMock).findRideRequest(rideId);

        assert rideRequestNew.getCustomerId() == customerId;
    }

    @Test
    void insertRideRequestValuesNotInsertedTest(){
        int customerId = 1;
        int rideId = 9;
        int expectedCustomerId = 2;
        RideRequest rideRequest = new RideRequest(rideId, customerId, "Spring Garden", "Downtown");
        IRideRequestsDao rideRequestMock = new RideRequestsDaoMockImpl();

        rideRequestMock.insertRideRequest(rideRequest);
        RideRequest rideRequestNew = ((RideRequestsDaoMockImpl) rideRequestMock).findRideRequest(rideId);

        assert rideRequestNew.getCustomerId() == expectedCustomerId;
    }

}
