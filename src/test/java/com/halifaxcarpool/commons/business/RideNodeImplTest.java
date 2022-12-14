package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.directions.DirectionPointsProviderMockImpl;
import com.halifaxcarpool.commons.business.directions.IDirectionPointsProvider;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.IRideNode;
import com.halifaxcarpool.driver.business.RideNodeImpl;
import com.halifaxcarpool.driver.business.beans.Ride;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class RideNodeImplTest {

IRideNode rideNode = new RideNodeImpl();
IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
IDirectionPointsProvider directionPointsProvider = new DirectionPointsProviderMockImpl();

    @Test
    void insertRideNodesTest() {
        String startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocation = "6056 University Ave, Halifax, NS B3H";
        Ride ride = new Ride(1, 1, startLocation, endLocation, 2, 1);
        rideNode.insertRideNodes(ride, rideNodeDao, directionPointsProvider);
    }

    @Test
    void getRideNodesTest() {
        LatLng latLng = new LatLng(1,2);
        rideNode.getRideNodes(latLng, rideNodeDao);
    }

}