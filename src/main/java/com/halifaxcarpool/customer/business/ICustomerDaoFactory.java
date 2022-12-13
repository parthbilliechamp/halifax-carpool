package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.admin.database.dao.IUserDetails;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.customer.database.dao.IPaymentDao;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface ICustomerDaoFactory {

    IUserDao getCustomerDao();

    IUserAuthenticationDao getCustomerAuthenticationDao();

    IRideRequestsDao getRideRequestsDao();

    IRideToRequestMapperDao getRideToRequestMapperDao();

    IRideNodeDao getRideNodeDao();

    IRidesDao getRidesDao();

    IUserDetails getCustomerDetailsDao();

    IPaymentDao getPaymentDao();

}
