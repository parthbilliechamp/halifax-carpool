package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverApprovalDaoMock implements DriverApprovalDao{
    @Override
    public List<User> getPendingApprovalDrivers() {
        List<User> drivers = new ArrayList<>();

        Driver driver1 = new Driver();
        driver1.setLicense_expiry_date("2022-12-31");
        drivers.add(driver1);

        Driver driver2 = new Driver();
        driver2.setLicense_expiry_date("2002-11-11");
        drivers.add(driver2);

        Driver driver3 = new Driver();
        driver3.setLicense_expiry_date(null);
        drivers.add(driver3);

        return drivers;
    }
}
