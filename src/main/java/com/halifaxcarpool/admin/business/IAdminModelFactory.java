package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.business.approve.IUserApproval;
import com.halifaxcarpool.admin.business.authentication.IAdmin;
import com.halifaxcarpool.admin.business.authentication.IAdminAuthentication;
import com.halifaxcarpool.admin.business.popular.ILocationPopularity;
import com.halifaxcarpool.admin.business.statistics.IUserStatisticsBuilder;
import com.halifaxcarpool.admin.business.statistics.UserAnalysis;
import com.halifaxcarpool.admin.database.dao.IDriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.ILocationPopularityDao;
import com.halifaxcarpool.admin.database.dao.IUserDetails;

public interface IAdminModelFactory {
    ICoupon getCoupon();

    IAdmin getAdmin();

    IAdminAuthentication getAdminAuthentication();

    IUserStatisticsBuilder getDriverStatisticsBuilder(IUserDetails userDetails);

    IUserStatisticsBuilder getCustomerStatisticsBuilder(IUserDetails userDetails);

    UserAnalysis getAnalysisBluePrint(IUserStatisticsBuilder userStatisticsBuilder);

    IUserApproval getDriverApproval(IDriverApprovalDao driverApprovalDao);

    ILocationPopularity getLocationPopularity(ILocationPopularityDao locationPopularityDao);

}
