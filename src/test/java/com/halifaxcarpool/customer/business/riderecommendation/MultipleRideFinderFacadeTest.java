package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderMockImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.recommendation.RideFinderFacade;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class MultipleRideFinderFacadeTest {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
    IRidesDao ridesDao = new RidesDaoMockImpl();
    IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderMockImpl();

    @Test
    public void findMultipleRouteRideSameStartPointSameEndPointTest() {
        String startLocationOfRideRequest = "THE TEN SPOT halifax, South Street, Halifax, NS";
        String endLocationOfRideRequest = "Dalplex, South Street, Halifax, Nova Scotia";
        RideRequest rideRequest = new RideRequest(1, 1, startLocationOfRideRequest, endLocationOfRideRequest);
        List<List<Ride>> listOfRidesList = rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
        for (List<Ride> rideList : listOfRidesList) {
            assert 2 == rideList.size();
            assert Objects.equals(rideList.get(0).getStartLocation(), startLocationOfRideRequest);
            assert Objects.equals(rideList.get(1).getEndLocation(), endLocationOfRideRequest);
        }
    }

    @Test
    public void findMultipleRouteRideRoute1RecommendedRoute2NotRecommendedTest() {
        String startLocationOfRideRequest = "Halifax Backpackers Hostel, Gottingen Street, Halifax, NS";
        String endLocationOfRideRequest = "CFB Halifax Curling Club, Hawk Terrace, Halifax, NS";
        RideRequest rideRequest = new RideRequest(2, 1, startLocationOfRideRequest, endLocationOfRideRequest);
        List<List<Ride>> listOfRidesList = rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
        assert 0 == listOfRidesList.size();
    }

    @Test
    public void findMultipleRouteRideRoute1NotRecommendedRoute2RecommendedTest() {
        String startLocationOfRideRequest = "Maplestone Enhanced Care, Main Avenue, Halifax, NS";
        String endLocationOfRideRequest = "BIGS Brothers Grocery Store, Boss Plaza, Supreme Court, Halifax, Nova Scotia";
        RideRequest rideRequest = new RideRequest(1, 31, startLocationOfRideRequest, endLocationOfRideRequest);
        List<List<Ride>> listOfRidesList = rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
        assert 0 == listOfRidesList.size();
    }

    @Test
    public void findMultipleRouteRideRoute1NotRecommendedRoute2NotRecommendedTest() {
        String startLocationOfRideRequest = "McDonald's, Quinpool Road, Halifax, Nova Scotia";
        String endLocationOfRideRequest = "Scotiabank Centre, Argyle Street, Halifax, Nova Scotia";
        RideRequest rideRequest = new RideRequest(1, 52, startLocationOfRideRequest, endLocationOfRideRequest);
        List<List<Ride>> listOfRidesList = rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
        assert 0 == listOfRidesList.size();
    }

}
