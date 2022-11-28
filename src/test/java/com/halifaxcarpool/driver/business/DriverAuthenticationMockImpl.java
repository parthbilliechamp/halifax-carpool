package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverAuthenticationDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;

public class DriverAuthenticationMockImpl implements IDriverAuthentication {

    IDriverAuthenticationDao driverAuthenticationDaoMockObj;

    public DriverAuthenticationMockImpl() {
        driverAuthenticationDaoMockObj = new DriverAuthenticationDaoMockImpl();
    }

    @Override
    public Driver authenticate(String userName, String password) {
        return driverAuthenticationDaoMockObj.authenticate(userName, password);
    }
}
