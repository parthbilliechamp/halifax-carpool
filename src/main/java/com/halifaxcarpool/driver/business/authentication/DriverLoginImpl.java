package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;

public class DriverLoginImpl implements IDriverLogin{

    @Override
    public Driver login(String userName, String password, IDriverAuthentication driverAuthentication) {
        return driverAuthentication.authenticate(userName, password);
    }
}
