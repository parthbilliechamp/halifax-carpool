package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.customer.CustomerObjectFactoryTest;
import com.halifaxcarpool.customer.business.ICustomerObjectFactory;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RideImplTest {

    private final ICustomerObjectFactory customerObjectFactory = new CustomerObjectFactoryTest();
    private final IRidesDao ridesDao = customerObjectFactory.getRidesDao();
    IRide ride = new RideImpl();

    @Test
    public void viewRidesTest() {
        int driverId = 1;
        List<Ride> rideList = ride.viewRides(driverId, ridesDao);
        assert 2 == rideList.size();
        for (Ride currentRide: rideList) {
            assert driverId == currentRide.driverId;
        }
    }

    @Test
    public void viewRidesEmptySetTest() {
        int driverId = 43;
        List<Ride> rideList = ride.viewRides(driverId, ridesDao);
        assert rideList.isEmpty();
    }

    @Test
    public void cancelRideSuccessTest() {
        int rideId = 13;
        assert ride.cancelRide(rideId, ridesDao);
    }

    @Test
    public void cancelRideFailureTest() {
        int rideId = 84;
        assert Boolean.FALSE.equals(ride.cancelRide(rideId, ridesDao));
    }

}
