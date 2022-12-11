package com.halifaxcarpool.driver.business;

import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public interface IDriverDaoObjectFactory {

    IDriverDao getDriverDao();

    IDriverAuthenticationDao getDriverAuthenticationDao();

}
