package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.DriverAuthenticationImpl;
import com.halifaxcarpool.driver.business.authentication.DriverImpl;
import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;

public class DriverBusinessObjectFactoryMain implements IDriverBusinessObjectFactory{

    @Override
    public IDriver getDriver() {
        return new DriverImpl();
    }

    @Override
    public IDriverAuthentication getDriverAuthentication() {
        return new DriverAuthenticationImpl();
    }
}
