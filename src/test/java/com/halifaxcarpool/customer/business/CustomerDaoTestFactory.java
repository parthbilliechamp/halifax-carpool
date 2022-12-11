package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;

public class CustomerDaoTestFactory implements CustomerDaoFactory {

    @Override
    public IRidesDao createRidesDao() {
        return new RidesDaoMockImpl();
    }

    @Override
    public IRideRequestsDao createRideRequestsDao() {
        return new RideRequestsDaoMockImpl();
    }

    @Override
    public IRideToRequestMapperDao createRideToRequestMapperDao() {
        return new RideToRequestMapperDaoMockImpl();
    }

    @Override
    public IRideNodeDao createRideNodeDao() {
        return new RideNodeDaoMockImpl();
    }

}
