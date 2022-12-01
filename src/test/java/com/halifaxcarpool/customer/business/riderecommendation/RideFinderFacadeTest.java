package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.geocoding.GeoCodingMockImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.RideFinderFacade;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
public class RideFinderFacadeTest {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
    IGeoCoding geoCoding = new GeoCodingMockImpl();
    
    IRidesDao ridesDao = new RidesDaoMockImpl();

    @Test
    public void findDirectRouteRidesSameStartPointSameEndPointTest() {
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, startLocation);
        assert Objects.equals(ride.endLocation, endLocation);
    }

    @Test
    public void findDirectRouteRidesInWayStartPointSameEndPointTest() {
        String startLocation = "Halifax Infirmary @ QEII - Robie Street Entrance, Robie Street, Halifax, NS";
        String endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        String rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, rideStartsFrom);
        assert Objects.equals(ride.endLocation, endLocation);
    }

    @Test
    public void findDirectRouteRidesSameStartPointInWayEndPointTest() {
        String startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocation = "Camp Hill Veterans Memorial Building @ QEII Health Sciences Centre";
        String rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, startLocation);
        assert Objects.equals(ride.endLocation, rideEndsAt);
    }

    @Test
    public void findDirectRouteRidesInWayStartPointInWayEndPointTest() {
        String startLocation = "Halifax Infirmary @ QEII - Robie Street Entrance, Robie Street, Halifax, NS";
        String endLocation = "Camp Hill Veterans Memorial Building @ QEII Health Sciences Centre";
        String rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, rideStartsFrom);
        assert Objects.equals(ride.endLocation, rideEndsAt);
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointSameEndPointTest() {
        String startLocation = "Ardmore Tea Room, Quinpool Road, Halifax, NS";
        String endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        /**
         * rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
         * rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
         * This is the ride present in mock data which matches the customer end location but since the start
         * point is far off the ride start location, the algorithm does not recommend this ride.
         */
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSameStartPointDifferentEndPointTest() {
        String startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocation = "Gorsebrook Park, Lundys Lane, Halifax, NS";
        /**
         * rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
         * rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
         * This is the ride present in mock data which matches the customer end location but since the end
         * point is far off the ride start location, the algorithm does not recommend this ride.
         */
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointDifferentEndPointTest() {
        String startLocation = "Ardmore Tea Room, Quinpool Road, Halifax, NS";
        String endLocation = "Gorsebrook Park, Lundys Lane, Halifax, NS";
        /**
         * rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
         * rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
         * This is the ride present in mock data which matches the customer end location but since the start
         * point and the end point is far off the ride start location, the algorithm does not recommend this ride.
         */
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDoNotSelectRidesInOppositeDirectionTest() {
        String startLocation = "Dalplex, 6260 South St, Halifax, NS B3H 4R2";;
        String endLocation = "Saint Mary's University, Robie Street, Halifax, NS";
        RideRequest rideRequest = new RideRequest(10, 6, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSelectSameDirectionAndDoNotSelectRideInOppositeDirectionTest() {
        String startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocation = "6056 University Ave, Halifax, NS B3H";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, startLocation);
        assert Objects.equals(ride.endLocation, endLocation);
    }

}
