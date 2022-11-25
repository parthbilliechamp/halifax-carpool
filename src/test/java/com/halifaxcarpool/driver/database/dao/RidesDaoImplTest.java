package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.customer.database.dao.RideRequestsDaoMockImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RidesDaoImplTest {

    @Test
    void viewRidesTest() {
        int driverId = 1;
        IRidesDao ridesDao = new RidesDaoMockImpl();
        List<Ride> rides = ridesDao.getRides(driverId);
        assert 2 == rides.size();
        for (Ride ride: rides) {
            assert driverId == ride.driverId;
        }
    }

    @Test
    void createNewRideTest(){
        int driverId = 3;
        int rideId = 8;

        Ride ride = new Ride();
        ride.setDriverId(driverId);
        ride.setRideId(rideId);

        IRidesDao rideMock = new RidesDaoMockImpl();

        rideMock.createNewRide(ride);
        Ride rideNew = ((RidesDaoMockImpl) rideMock).findRide(rideId, ride.getDriverId());

        assert rideNew.getDriverId() == driverId;
    }

    @Test
    void createNewRideValuesNotInsertedTest(){
        int driverId = 3;
        int rideId = 9;

        int expectedRideId = 4;

        Ride ride = new Ride();
        ride.setDriverId(driverId);
        ride.setRideId(rideId);

        IRidesDao rideMock = new RidesDaoMockImpl();
        rideMock.createNewRide(ride);

        Ride rideNew = ((RidesDaoMockImpl) rideMock).findRide(rideId, ride.getDriverId());

        assert rideNew.getRideId() != expectedRideId;
    }

}
