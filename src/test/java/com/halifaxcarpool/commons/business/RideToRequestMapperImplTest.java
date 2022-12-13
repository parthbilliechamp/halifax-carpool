package com.halifaxcarpool.commons.business;

import com.halifaxcarpool.driver.business.IRideToRequestMapper;
import com.halifaxcarpool.driver.business.RideToRequestMapperImpl;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl.rideToRequestMapMockData;

public class RideToRequestMapperImplTest {
    IRideToRequestMapperDao rideToRequestMapperDao = new RideToRequestMapperDaoMockImpl();

    @Test
    public void sendRideRequestTest() {
        rideToRequestMapperDao.insertRideToRequestMapper(4, 6, "",0.0);
        assert 3 == rideToRequestMapMockData.size();
    }

    @Test
    public void viewReceivedRequestTest() {
        int rideId = 5;
        List<RideRequest> rideRequests = rideToRequestMapperDao.viewReceivedRequests(rideId);
        assert 1 == rideRequests.size();
        assert 5 == rideRequests.get(0).getRideRequestId();
    }

    @Test
    public void viewApprovedRequestTest(){
        int rideId = 1;
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        List<RideRequest> rideRequests = rideToRequestMapper.viewApprovedRequest(rideId,rideToRequestMapperDao);
        System.out.println(rideRequests.size());
        assert 0 < rideRequests.size();
    }

    @Test
    public void updateRideRequestStatusTest() {
        int rideId =2;
        int rideRequestId = 3;
        String status = "ACCEPTED";
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        assert rideToRequestMapper.updateRideRequestStatus(rideId,rideRequestId,status,rideToRequestMapperDao);
    }

    @Test
    public void getPaymentAmountTest() {
        int rideId =1;
        int rideRequestId = 4;
        IRideToRequestMapper rideToRequestMapper = new RideToRequestMapperImpl();
        rideToRequestMapperDao.getPaymentAmount(rideId, rideRequestId);
        assert 0 == Double.compare(33.33, rideToRequestMapper.getPaymentAmount(rideId, rideRequestId, rideToRequestMapperDao));

    }

}
