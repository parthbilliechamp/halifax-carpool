package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.authentication.IDriver;
import com.halifaxcarpool.driver.business.authentication.IDriverAuthentication;

public interface IDriverBusinessObjectFactory {

    IDriver getDriver();

    IDriverAuthentication getDriverAuthentication();

}
