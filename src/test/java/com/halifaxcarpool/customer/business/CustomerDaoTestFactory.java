package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.admin.database.dao.CustomerDetailsDaoMock;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.*;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;

public class CustomerDaoTestFactory implements ICustomerDaoFactory {

    @Override
    public IUserDao getCustomerDao() {
        return new CustomerDaoMockImpl();
    }

    @Override
    public IUserAuthenticationDao getCustomerAuthenticationDao() {
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

    @Override
    public IUserDetails getCustomerDetailsDao() {
        return new CustomerDetailsDaoMock();
    }

    @Override
    public IPaymentDao getPaymentDao() {
        return new PaymentDaoMockImpl();
    }

}
