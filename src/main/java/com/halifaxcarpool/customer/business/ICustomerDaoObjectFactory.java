package com.halifaxcarpool.customer.business;

import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.customer.database.dao.IRideRequestsDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface ICustomerDaoObjectFactory {

    IRidesDao getRidesDao();

    IRideRequestsDao getRideRequestsDao();

    IRideToRequestMapperDao getRideToRequestMapperDao();

    IRideNodeDao getRideNodeDao();

}
