package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DriverApprovalDaoMock implements DriverApprovalDao{

    private List<User> drivers = new ArrayList<>();

    public DriverApprovalDaoMock(){
        Driver driver1 = new Driver();
        driver1.setLicense_expiry_date("2022-12-31");
        driver1.setDriver_license("1");
        drivers.add(driver1);

        Driver driver2 = new Driver();
        driver2.setLicense_expiry_date("2002-11-11");
        driver2.setDriver_license("2");
        drivers.add(driver2);

        Driver driver3 = new Driver();
        driver3.setLicense_expiry_date(null);
        driver3.setDriver_license("3");
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
            System.out.println(driverTemp.getDriver_license());
            if(driverTemp.getDriver_license().equals(id)){
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
            if(driverTemp.getDriver_license().equals(id)){
                return true;
            }
        }
        return false;
    }
}
