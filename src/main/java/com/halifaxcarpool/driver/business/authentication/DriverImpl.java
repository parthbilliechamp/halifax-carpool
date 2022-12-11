package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public class DriverImpl implements IDriver {

    @Override
    public Driver login(String userName, String password, IDriverAuthentication driverAuthentication, IDriverAuthenticationDao driverAuthenticationDao) {
        return driverAuthentication.authenticate(userName, password, driverAuthenticationDao);
    }

    public void registerDriver(Driver driver, IDriverDao driverDao) throws Exception {
        try {
            driverDao.registerDriver(driver);
        } catch (Exception e) {
            throw new Exception("Driver Already Exists");
        }
    }

    @Override
    public boolean update(Driver driver, IDriverDao driverDao) {
        return driverDao.updateDriverProfile(driver);
    }

}
