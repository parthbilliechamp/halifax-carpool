package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.CommonsTestFactory;
import com.halifaxcarpool.commons.business.ICommonsFactory;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.CustomerDaoTestFactory;
import com.halifaxcarpool.customer.business.ICustomerDaoFactory;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.RideFinderFacade;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class RideFinderFacadeTest {

    ICustomerDaoFactory customerDaoObjectFactory = new CustomerDaoTestFactory();
    ICommonsFactory commonsObjectFactory = new CommonsTestFactory();

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = customerDaoObjectFactory.getRideNodeDao();
    IGeoCoding geoCoding = commonsObjectFactory.getGeoCoding();
    IRidesDao ridesDao = customerDaoObjectFactory.getRidesDao();

    @Test
    public void findDirectRouteRidesSameStartPointSameEndPointTest() {
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao).get(0);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.getStartLocation(), startLocation);
        assert Objects.equals(ride.getEndLocation(), endLocation);
    }

    @Test
    public void findDirectRouteRidesInWayStartPointSameEndPointTest() {
        String startLocation = "Halifax Infirmary @ QEII - Robie Street Entrance, Robie Street, Halifax, NS";
        String endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        String rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao).get(0);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.getStartLocation(), rideStartsFrom);
        assert Objects.equals(ride.getEndLocation(), endLocation);
    }

    @Test
    public void findDirectRouteRidesSameStartPointInWayEndPointTest() {
        String startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocation = "Camp Hill Veterans Memorial Building @ QEII Health Sciences Centre";
        String rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao).get(0);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.getStartLocation(), startLocation);
        assert Objects.equals(ride.getEndLocation(), rideEndsAt);
    }

    @Test
    public void findDirectRouteRidesInWayStartPointInWayEndPointTest() {
        String startLocation = "Halifax Infirmary @ QEII - Robie Street Entrance, Robie Street, Halifax, NS";
        String endLocation = "Camp Hill Veterans Memorial Building @ QEII Health Sciences Centre";
        String rideStartsFrom = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String rideEndsAt = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao).get(0);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.getStartLocation(), rideStartsFrom);
        assert Objects.equals(ride.getEndLocation(), rideEndsAt);
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointSameEndPointTest() {
        String startLocation = "Ardmore Tea Room, Quinpool Road, Halifax, NS";
        String endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<List<Ride>> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSameStartPointDifferentEndPointTest() {
        String startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocation = "Gorsebrook Park, Lundys Lane, Halifax, NS";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<List<Ride>> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointDifferentEndPointTest() {
        String startLocation = "Ardmore Tea Room, Quinpool Road, Halifax, NS";
        String endLocation = "Gorsebrook Park, Lundys Lane, Halifax, NS";
        RideRequest rideRequest = new RideRequest(2, 5, startLocation, endLocation);
        List<List<Ride>> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDoNotSelectRidesInOppositeDirectionTest() {
        String startLocation = "Dalplex, 6260 South St, Halifax, NS B3H 4R2";;
        String endLocation = "Saint Mary's University, Robie Street, Halifax, NS";
        RideRequest rideRequest = new RideRequest(10, 6, startLocation, endLocation);
        List<List<Ride>> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 0 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSelectSameDirectionAndDoNotSelectRideInOppositeDirectionTest() {
        String startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocation = "6056 University Ave, Halifax, NS B3H";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRidesInvoker(rideRequest, rideNodeDao, geoCoding, ridesDao).get(0);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.getStartLocation(), startLocation);
        assert Objects.equals(ride.getEndLocation(), endLocation);
    }

}
