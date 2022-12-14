package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderMockImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@SpringBootTest
@ActiveProfiles("test")
public class RideTest {
    private final IRidesDao ridesDao = new RidesDaoMockImpl();
    IRide ride = new Ride();

    @Test
    public void viewRidesTest() {
        int driverId = 1;
        List<Ride> rideList = ride.viewAllRides(driverId, ridesDao);
        assert 2 == rideList.size();
        for (Ride currentRide: rideList) {
            assert driverId == currentRide.getDriverId();
        }
    }

    @Test
    public void viewRidesEmptySetTest() {
        int driverId = 43;
        List<Ride> rideList = ride.viewAllRides(driverId, ridesDao);
        assert rideList.isEmpty();
    }

    @Test
    void createNewRideSuccessTest() {
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
        IRideNode rideNode = new RideNodeImpl();
        assert rideObject.createNewRide(ridesDao, rideNodeDao, directionPointsProvider, rideNode);
    }

    @Test
    void createNewRideFailureTest() {
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
        IRideNode rideNode = new RideNodeImpl();
        boolean isRideCreated = rideObject.createNewRide(ridesDao, rideNodeDao,
                directionPointsProvider, rideNode);
        assert Boolean.FALSE.equals(isRideCreated);
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

    @Test
    public void viewOngoingRidesTest() {
        int customerId = 1;
        List<Ride> rideList = ride.viewOngoingRides(customerId, ridesDao);
        assert 2 == rideList.size();
        assert 15 == rideList.get(0).getRideId();
        assert 16 == rideList.get(1).getRideId();
    }

    @Test
    public void viewOngoingRidesNoActiveRidesTest() {
        int customerId = 3;
        List<Ride> rideList = ride.viewOngoingRides(customerId, ridesDao);
        assert 0 == rideList.size();
    }

    @Test
    public void startRideTest(){
        int rideId=1;
        IRide ride = new Ride();
        assert (ride.startRide(rideId,ridesDao));
    }

    @Test
    public void stopRideTest(){
        int rideId=2;
        IRide ride = new Ride();
        assert (ride.stopRide(rideId, ridesDao));
    }

}
