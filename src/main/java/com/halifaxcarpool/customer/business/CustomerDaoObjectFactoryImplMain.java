package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.business.authentication.ICustomerAuthentication;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

public class CustomerDaoObjectFactoryImplMain implements ICustomerDaoObjectFactory {
    @Override
    public ICustomerDao getCustomerDao() {
        return new CustomerDaoImpl();
    }

    @Override
    public ICustomerAuthenticationDao getCustomerAuthenticationDao() {
        return new CustomerAuthenticationDaoImpl();
    }

    @Override
    public IRidesDao getRidesDao() {
        return new RidesDaoImpl();
    }

    @Override
    public IRideRequestsDao getRideRequestsDao() {
        return new RideRequestsDaoImpl();
    }

    @Override
    public IRideToRequestMapperDao getRideToRequestMapperDao() {
        return new RideToRequestMapperDaoImpl();
    }

    @Override
    public IRideNodeDao getRideNodeDao() {
        return new RideNodeDaoImpl();
    }

}
