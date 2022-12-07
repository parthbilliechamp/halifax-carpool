package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public class DriverMockImpl implements IDriver {
    @Override
    public Driver login(String userName, String password, IDriverAuthentication driverAuthentication) {
        return driverAuthentication.authenticate(userName, password);
    }

    @Override
    public void update(Driver driver, IDriverDao driverDao) {

    }

}
