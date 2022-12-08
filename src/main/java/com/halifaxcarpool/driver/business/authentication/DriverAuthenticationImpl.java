package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoImpl;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;

public class DriverAuthenticationImpl implements IDriverAuthentication {

    IDriverAuthenticationDao driverAuthenticationDao;

    public DriverAuthenticationImpl() {
        driverAuthenticationDao = new DriverAuthenticationDaoImpl();
    }
    @Override
    public Driver authenticate(String userName, String password) {
        return driverAuthenticationDao.authenticate(userName, password);
    }

}
