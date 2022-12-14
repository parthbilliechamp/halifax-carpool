package com.halifaxcarpool.admin.business.approve;

import com.halifaxcarpool.admin.database.dao.IDriverApprovalDao;
import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DriverApproval implements IUserApproval {

    private final IDriverApprovalDao driverApprovalDao;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final Date currentDate;

    public DriverApproval(IDriverApprovalDao driverApprovalDao){
        this.driverApprovalDao = driverApprovalDao;
        try {
            this.currentDate = SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format(new Date()));
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

    @Override
    public boolean acceptUserRequest(String id) {
        return driverApprovalDao.acceptDriverRequest(id);
    }

    @Override
    public boolean rejectUserRequest(String id) {
        return driverApprovalDao.rejectDriverRequest(id);
    }

    private List<User> filterExpiredLicense(List<User> drivers){
        List<User> filteredDrivers = new ArrayList<>();
        Iterator<User> itr = drivers.iterator();
        while(itr.hasNext()){
            Driver filterDriver = (Driver) itr.next();
            if(null != filterDriver.getLicenseExpiryDate()){
                Date driverLicenseExpireDate = parseDate(filterDriver.getLicenseExpiryDate());
                if(driverLicenseExpireDate.compareTo(currentDate) > 0){
                    filteredDrivers.add(filterDriver);
                }
            }
        }
        return filteredDrivers;
    }

    private Date parseDate(String date){
        Date newDate;
        try {
            newDate = SIMPLE_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newDate;
    }

}
