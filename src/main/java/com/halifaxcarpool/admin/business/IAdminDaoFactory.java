package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.database.dao.IAdminAuthenticationDao;
import com.halifaxcarpool.admin.database.dao.IDriverApprovalDao;
import com.halifaxcarpool.admin.database.dao.ILocationPopularityDao;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

public interface IAdminDaoFactory {

    IDriverApprovalDao getDriverApprovalDao();

    ILocationPopularityDao getLocationPopularityDao();

    IAdminAuthenticationDao getAdminAuthenticationDao();

    ICouponDao getCouponDao();

}
