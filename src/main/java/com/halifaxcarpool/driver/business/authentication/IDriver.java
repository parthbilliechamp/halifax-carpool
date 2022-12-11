package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public interface IDriver {

    Driver login(String userName, String password, IDriverAuthentication driverAuthentication, IDriverAuthenticationDao driverAuthenticationDao);

    boolean update(Driver driver, IDriverDao driverDao);

    void registerDriver(Driver driver, IDriverDao driverDao) throws Exception;

}
