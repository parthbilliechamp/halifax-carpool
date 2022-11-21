package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.Connection;

public class DriverRegistrationDaoImpl implements IDriverRegistrationDao{

    IDatabase database;
    Connection connection;

    public DriverRegistrationDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    public void registerDriver(Driver driver) {

    }

}
