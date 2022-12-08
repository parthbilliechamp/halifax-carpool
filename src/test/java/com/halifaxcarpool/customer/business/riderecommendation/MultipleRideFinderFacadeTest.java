package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderImpl;
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

@SpringBootTest
@ActiveProfiles("test")
public class MultipleRideFinderFacadeTest {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
    IRidesDao ridesDao = new RidesDaoMockImpl();

    IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderMockImpl();

    @Test
    public void findMultipleRouteRideSameStartPointSameEndPoint() {

        String startLocation = "THE TEN SPOT halifax, South Street, Halifax, NS";
        String endLocation = "Dalplex, South Street, Halifax, Nova Scotia";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<List<Ride>> rides = rideFinderFacade.findMultipleRouteRides(rideRequest, directionPointsProvider, rideNodeDao, ridesDao);
        System.out.println(rides);
    }

}
