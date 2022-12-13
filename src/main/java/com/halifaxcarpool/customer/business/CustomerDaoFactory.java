package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RideToRequestMapperDaoImpl;
import com.halifaxcarpool.driver.database.dao.RidesDaoImpl;

public class CustomerDaoFactory implements ICustomerDaoFactory {

    @Override
    public IUserDao getCustomerDao() {
        return new CustomerDaoImpl();
    }

    @Override
    public IUserAuthenticationDao getCustomerAuthenticationDao() {
        return new CustomerAuthenticationDaoImpl();
    }

    @Override
    public IRidesDao getRidesDao() {
        return new RidesDaoImpl();
    }

    @Override
    public IUserDetails getCustomerDetailsDao() {
        return new CustomerDetailsDaoImpl();
    }

    @Override
    public IPaymentDao getPaymentDao() {
        return new PaymentDaoImpl();
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
