package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.DriverDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public class DriverDaoObjectFactoryImplTest implements IDriverDaoObjectFactory{
    @Override
    public IDriverDao getDriverDao() {
        return new DriverDaoMockImpl();
    }

    @Override
    public IDriverAuthenticationDao getDriverAuthenticationDao() {
        return new DriverAuthenticationDaoMockImpl();
    }
}
