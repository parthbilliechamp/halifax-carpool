package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.beans.RideRequest;
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

}
