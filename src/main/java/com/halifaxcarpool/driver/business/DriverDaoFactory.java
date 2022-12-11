package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.database.dao.IRideToRequestMapperDao;
import com.halifaxcarpool.driver.database.dao.IRidesDao;

public interface DriverDaoFactory {

    IUserDao getDriverDao();
    IUserAuthenticationDao getDriverAuthenticationDao();
    IRidesDao getDriverRidesDao();
    IRideToRequestMapperDao getRidetoRequestMapperDao();

}
