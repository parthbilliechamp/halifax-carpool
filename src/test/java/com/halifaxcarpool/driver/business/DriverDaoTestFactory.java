package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.admin.database.dao.DriverDetailsDaoMock;
import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.business.RideToRequestMapperDaoMockImpl;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.database.dao.*;

public class DriverDaoTestFactory implements IDriverDaoFactory {

    @Override
    public IUserDao getDriverDao() {
        return new DriverDaoMockImpl();
    }

    @Override
    public IUserAuthenticationDao getDriverAuthenticationDao() {
        return new DriverAuthenticationDaoMockImpl();
    }

    @Override
    public IRidesDao getDriverRidesDao() {
        return new RidesDaoMockImpl();
    }

    @Override
    public IRideToRequestMapperDao getRideToRequestMapperDao() {
        return new RideToRequestMapperDaoMockImpl();
    }

    @Override
    public IUserDetails getDriverDetailsDao() {
        return new DriverDetailsDaoMock();
    }
}
