package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;

public interface IDriverDao {

    boolean updateDriverProfile(Driver driver);
}
