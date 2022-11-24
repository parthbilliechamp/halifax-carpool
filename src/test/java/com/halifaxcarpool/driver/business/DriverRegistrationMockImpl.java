package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.registration.IDriverRegistration;
import com.halifaxcarpool.driver.database.dao.DriverRegistrationDaoMockImpl;
import com.halifaxcarpool.driver.database.dao.IDriverRegistrationDao;

public class DriverRegistrationMockImpl implements IDriverRegistration {

    IDriverRegistrationDao driverRegistrationDao =  new DriverRegistrationDaoMockImpl();

    public void registerDriver(Driver driver) {

        driverRegistrationDao.registerDriver(driver);

    }

}
