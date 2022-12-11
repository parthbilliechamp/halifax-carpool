package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoImpl;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;

public class DriverAuthenticationImpl implements IDriverAuthentication {

    @Override
    public Driver authenticate(String userName, String password, IDriverAuthenticationDao driverAuthenticationDao) {
        return driverAuthenticationDao.authenticate(userName, password);
    }

}
