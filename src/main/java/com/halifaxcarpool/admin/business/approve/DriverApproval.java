package com.halifaxcarpool.admin.business.approve;

import com.halifaxcarpool.admin.database.dao.DriverApprovalDao;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DriverApproval implements UserApproval{

    private DriverApprovalDao driverApprovalDao;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final Date currentDate;

    public DriverApproval(DriverApprovalDao driverApprovalDao){

        this.driverApprovalDao = driverApprovalDao;
        try {
            this.currentDate = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getValidUserRequests() {
        List<User> drivers = driverApprovalDao.getPendingApprovalDrivers();
        drivers = filterExpiredLicense(drivers);
        return drivers;
    }

    private List<User> filterExpiredLicense(List<User> drivers){
        List<User> filteredDrivers = new ArrayList<>();
        Iterator<User> itr = drivers.iterator();
        while(itr.hasNext()){
            Driver filterDriver = (Driver) itr.next();
            if(null != filterDriver.getLicense_expiry_date()){
                Date driverLicenseExpireDate = parseDate(filterDriver.getLicense_expiry_date());
                if(driverLicenseExpireDate.compareTo(currentDate)>0){
                    filteredDrivers.add(filterDriver);
                }
            }

        }
        return filteredDrivers;
    }

    private Date parseDate(String date){
        Date newDate = null;
        try {
            newDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newDate;
    }

}
