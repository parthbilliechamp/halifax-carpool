package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DriverApprovalDaoMock implements IDriverApprovalDao {

    private List<User> drivers = new ArrayList<>();

    public DriverApprovalDaoMock(){
        Driver driver1 = new Driver();
        driver1.setLicenseExpiryDate("2022-12-31");
        driver1.setDriverLicense("1");
        drivers.add(driver1);

        Driver driver2 = new Driver();
        driver2.setLicenseExpiryDate("2002-11-11");
        driver2.setDriverLicense("2");
        drivers.add(driver2);

        Driver driver3 = new Driver();
        driver3.setLicenseExpiryDate(null);
        driver3.setDriverLicense("3");
        drivers.add(driver3);
    }

    @Override
    public List<User> getPendingApprovalDrivers() {
        return drivers;
    }

    @Override
    public boolean acceptDriverRequest(String id) {
        Iterator<User> itr = drivers.iterator();
        System.out.println(drivers.size());
        while(itr.hasNext()){
            Driver driverTemp = (Driver) itr.next();
            System.out.println(driverTemp.getDriverLicense());
            if(driverTemp.getDriverLicense().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean rejectDriverRequest(String id) {
        Iterator<User> itr = drivers.iterator();
        while(itr.hasNext()){
            Driver driverTemp = (Driver) itr.next();
            if(driverTemp.getDriverLicense().equals(id)){
                return true;
            }
        }
        return false;
    }
}
