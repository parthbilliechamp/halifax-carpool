package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;
import com.halifaxcarpool.driver.business.authentication.IDriverLogin;
import com.halifaxcarpool.driver.business.beans.Driver;

public class DriverLoginMockImpl implements IDriverLogin {
    @Override
    public Driver login(String userName, String password, IDriverAuthentication driverAuthentication) {
        return driverAuthentication.authenticate(userName, password);
    }
}
