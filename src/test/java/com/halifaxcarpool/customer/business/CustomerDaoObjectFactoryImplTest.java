package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;

public class CustomerDaoObjectFactoryImplTest implements ICustomerDaoObjectFactory {

    @Override
    public ICustomerDao getCustomerDao() {
        return new CustomerDaoMockImpl();
    }

    @Override
    public ICustomerAuthenticationDao getCustomerAuthenticationDao() {
        return new CustomerAuthenticationDaoMockImpl();
    }

    @Override
    public IRidesDao getRidesDao() {
        return new RidesDaoMockImpl();
    }

    @Override
    public IRideRequestsDao getRideRequestsDao() {
        return new RideRequestsDaoMockImpl();
    }

    @Override
    public IRideToRequestMapperDao getRideToRequestMapperDao() {
        return new RideToRequestMapperDaoMockImpl();
    }

    @Override
    public IRideNodeDao getRideNodeDao() {
        return new RideNodeDaoMockImpl();
    }

}
