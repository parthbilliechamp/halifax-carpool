package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverDao;

public interface IDriver {

    Driver login(String userName, String password, IDriverAuthentication driverAuthentication);

    boolean update(Driver driver, IDriverDao driverDao);

}
