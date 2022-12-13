package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.database.dao.*;

public class DriverDaoFactory implements IDriverDaoFactory {

    @Override
    public IUserDao getDriverDao() {
        return new DriverDaoImpl();
    }

    @Override
    public IUserAuthenticationDao getDriverAuthenticationDao() {
        return new DriverAuthenticationDaoImpl();
    }

    @Override
    public IRidesDao getDriverRidesDao() {
        return new RidesDaoImpl();
    }

    @Override
    public IRideToRequestMapperDao getRideToRequestMapperDao() {
        return new RideToRequestMapperDaoImpl();
    }

    public IUserDetails getDriverDetailsDao() {
        return new DriverDetailsDaoImpl();
    }

}
