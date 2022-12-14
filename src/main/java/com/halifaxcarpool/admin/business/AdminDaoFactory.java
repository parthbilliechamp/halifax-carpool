package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.admin.database.dao.CouponDaoImpl;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

public class AdminDaoFactory implements IAdminDaoFactory {

    @Override
    public IDriverApprovalDao getDriverApprovalDao() {
        return new DriverApprovalDaoImpl();
    }

    @Override
    public ILocationPopularityDao getLocationPopularityDao() {
        return new LocationPopularityDaoImpl();
    }

    @Override
    public IAdminAuthenticationDao getAdminAuthenticationDao() {
        return new AdminAuthenticationDaoImpl();
    }

    @Override
    public ICouponDao getCouponDao() {
        return new CouponDaoImpl();
    }

}
