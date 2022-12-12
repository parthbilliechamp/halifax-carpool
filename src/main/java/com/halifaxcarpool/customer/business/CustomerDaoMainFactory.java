package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

public class CustomerDaoMainFactory implements CustomerDaoFactory {

    @Override
    public IUserDao getCustomerDao() {
        return new CustomerDaoImpl();
    }

    @Override
    public IUserAuthenticationDao getCustomerAuthenticationDao() {
        return new CustomerAuthenticationDaoImpl();
    }

    @Override
    public IRidesDao createRidesDao() {
        return new RidesDaoImpl();
    }

    @Override
    public IRideRequestsDao createRideRequestsDao() {
        return new RideRequestsDaoImpl();
    }

    @Override
    public IRideToRequestMapperDao createRideToRequestMapperDao() {
        return new RideToRequestMapperDaoImpl();
    }

    @Override
    public IRideNodeDao createRideNodeDao() {
        return new RideNodeDaoImpl();
    }

    @Override
    public IUserDetails getCustomerDetailsDao() {
        return new CustomerDetailsDaoImpl();
    }

}
