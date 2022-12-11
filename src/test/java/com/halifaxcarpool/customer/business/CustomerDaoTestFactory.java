package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;

public class CustomerDaoTestFactory implements CustomerDaoFactory {

    @Override
    public IUserDao getCustomerDao() {
        return new CustomerDaoMockImpl();
    }

    @Override
    public IUserAuthenticationDao getCustomerAuthenticationDao() {
        return new CustomerAuthenticationDaoMockImpl();
    }

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
