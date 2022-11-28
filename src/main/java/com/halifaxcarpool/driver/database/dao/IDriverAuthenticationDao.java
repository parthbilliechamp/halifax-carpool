package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;

public interface IDriverAuthenticationDao {

    Driver authenticate(String username, String password);

}
