package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoImpl;
import com.halifaxcarpool.driver.database.dao.DriverDaoImpl;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public class DriverDaoObjectFactoryImplMain implements IDriverDaoObjectFactory{
    @Override
    public IDriverDao getDriverDao() {
        return new DriverDaoImpl();
    }

    @Override
    public IDriverAuthenticationDao getDriverAuthenticationDao() {
        return new DriverAuthenticationDaoImpl();
    }
}
