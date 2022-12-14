package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.approve.DriverApproval;
import com.halifaxcarpool.admin.business.approve.IUserApproval;
import com.halifaxcarpool.admin.business.authentication.AdminAuthenticationImpl;
import com.halifaxcarpool.admin.business.authentication.AdminImpl;
import com.halifaxcarpool.admin.business.authentication.IAdmin;
import com.halifaxcarpool.admin.business.authentication.IAdminAuthentication;
import com.halifaxcarpool.admin.business.beans.Coupon;
import com.halifaxcarpool.admin.business.popular.ILocationPopularity;
import com.halifaxcarpool.admin.business.popular.LocationPopularityImpl;
import com.halifaxcarpool.admin.business.statistics.CustomerStatistics;
import com.halifaxcarpool.admin.business.statistics.DriverStatistics;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.database.dao.IDriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.ILocationPopularityDao;
import com.halifaxcarpool.admin.database.dao.IUserDetails;

public class AdminModelFactory implements IAdminModelFactory {

    @Override
    public ICoupon getCoupon() {
        return new Coupon();
    }

    @Override
    public IAdmin getAdmin() {
        return new AdminImpl();
    }

    @Override
    public IAdminAuthentication getAdminAuthentication() {
        return new AdminAuthenticationImpl();
    }

    @Override
    public IUserStatisticsBuilder getDriverStatisticsBuilder(IUserDetails userDetails) {
        return new DriverStatistics(userDetails);
    }

    @Override
    public IUserStatisticsBuilder getCustomerStatisticsBuilder(IUserDetails userDetails) {
        return new CustomerStatistics(userDetails);
    }

    @Override
    public UserAnalysis getAnalysisBluePrint(IUserStatisticsBuilder userStatisticsBuilder) {
        return new UserAnalysis(userStatisticsBuilder);
    }

    @Override
    public IUserApproval getDriverApproval(IDriverApprovalDao driverApprovalDao) {
        return new DriverApproval(driverApprovalDao);
    }

    @Override
    public ILocationPopularity getLocationPopularity(ILocationPopularityDao locationPopularityDao) {
        return new LocationPopularityImpl(locationPopularityDao);
    }

}
