package com.halifaxcarpool.driver.business.registration;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.DriverRegistrationDaoImpl;
import com.halifaxcarpool.driver.database.dao.IDriverRegistrationDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverRegistrationImpl implements IDriverRegistration{

    IDriverRegistrationDao driverRegistrationDao;

    public DriverRegistrationImpl() {

        driverRegistrationDao = new DriverRegistrationDaoImpl();

    }

    public void registerDriver(Driver driver) {

        driverRegistrationDao.registerDriver(driver);

    }

}
