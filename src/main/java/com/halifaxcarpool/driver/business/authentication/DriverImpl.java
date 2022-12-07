package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public class DriverImpl implements IDriver {

    @Override
    public Driver login(String userName, String password, IDriverAuthentication driverAuthentication) {
        return driverAuthentication.authenticate(userName, password);
    }

    @Override
    public void update(Driver driver, IDriverDao driverDao) {
        driverDao.updateDriverProfile(driver);
    }
}
