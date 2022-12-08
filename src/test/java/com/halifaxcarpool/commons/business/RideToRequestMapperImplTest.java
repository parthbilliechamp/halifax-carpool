package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl.rideToRequestMapMockData;

public class RideToRequestMapperImplTest {
    IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoMockImpl();

    @Test
    public void sendRideRequestTest() {
        rideToRequestMapperDao.insertRideToRequestMapper(4, 6, "");
        assert 3 == rideToRequestMapMockData.size();
    }

    @Test
    public void viewReceivedRequest() {
        int rideId = 5;
        List<RideRequest> rideRequests = rideToRequestMapperDao.viewReceivedRequests(rideId);
        assert 1 == rideRequests.size();
        assert 5 == rideRequests.get(0).rideRequestId;
    }

}
