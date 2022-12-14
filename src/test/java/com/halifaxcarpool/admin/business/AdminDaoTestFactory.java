package com.halifaxcarpool.admin.business;

import com.halifaxcarpool.admin.database.dao.*;
import com.halifaxcarpool.admin.database.dao.ICouponDao;

public class AdminDaoTestFactory implements IAdminDaoFactory{

    @Override
    public IDriverApprovalDao getDriverApprovalDao() {
        return new DriverApprovalDaoMock();
    }

    @Override
    public ILocationPopularityDao getLocationPopularityDao() {
        return new LocationPopularityDaoMock();
    }

    @Override
    public IAdminAuthenticationDao getAdminAuthenticationDao() {
        return new AdminAuthenticationDaoMockImpl();
    }

    @Override
    public ICouponDao getCouponDao() {
        return new CouponDaoMock();
    }
}
