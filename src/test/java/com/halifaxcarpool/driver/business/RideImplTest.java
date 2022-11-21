package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RideImplTest {

    @Test
    public void testViewRides() {
        IRidesDao ridesDao = new RidesDaoMockImpl();
        IRide ride = new RideImpl();
        int driverId = 1;
        List<Ride> rideList = ride.viewRides(driverId, ridesDao);
        assert 2 == rideList.size();
        for (Ride currentRide: rideList) {
            assert driverId == currentRide.driverId;
        }
    }


}
