package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderMockImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.CustomerObjectFactoryTest;
import com.halifaxcarpool.customer.business.ICustomerObjectFactory;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
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
    void createNewRideTestSuccess() {
        int driverId = 3;
        int rideId = 8;

        Ride rideObject = new Ride();
        rideObject.setDriverId(driverId);
        rideObject.setRideId(rideId);
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        rideObject.setStartLocation(startLocation);
        rideObject.setEndLocation(endLocation);
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderMockImpl();
        IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
        assert ride.createNewRide(rideObject, ridesDao, rideNodeDao, directionPointsProvider);
    }

    @Test
    void createNewRideTestFailure() {
        int driverId = 3;
        int rideId = 8;

        Ride rideObject = new Ride();
        rideObject.setDriverId(driverId);
        rideObject.setRideId(rideId);
        String startLocation = "Invalid address";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        rideObject.setStartLocation(startLocation);
        rideObject.setEndLocation(endLocation);
        IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderMockImpl();
        IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
        boolean isRideCreated = ride.createNewRide(rideObject, ridesDao, rideNodeDao, directionPointsProvider);
        assert Boolean.FALSE.equals(isRideCreated);
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
