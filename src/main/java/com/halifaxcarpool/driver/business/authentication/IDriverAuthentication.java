package com.halifaxcarpool.driver.business.authentication;

import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.database.dao.IDriverAuthenticationDao;

public interface IDriverAuthentication {

    Driver authenticate(String userName, String password, IDriverAuthenticationDao driverAuthenticationDao);

}
