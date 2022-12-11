package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.driver.business.beans.Driver;

public interface IDriverDao {

    public void registerDriver(Driver driver) throws Exception;

    boolean updateDriverProfile(Driver driver);
}
