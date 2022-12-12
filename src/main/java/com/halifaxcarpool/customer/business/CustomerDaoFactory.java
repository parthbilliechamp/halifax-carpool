package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface CustomerDaoFactory {

    IUserDao getCustomerDao();

    IUserAuthenticationDao getCustomerAuthenticationDao();

    IRidesDao createRidesDao();

    IRideRequestsDao createRideRequestsDao();

    IRideToRequestMapperDao createRideToRequestMapperDao();

    IRideNodeDao createRideNodeDao();

}
