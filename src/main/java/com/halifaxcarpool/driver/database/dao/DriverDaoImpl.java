package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.CallableStatement;
import java.sql.Connection;

public class DriverDaoImpl implements IDriverDao {

    private final IDatabase database;
    private Connection connection;

    public DriverDaoImpl() {
        database = new DatabaseImpl();
    }


    @Override
    public void updateDriverProfile(Driver driver) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL update_driver_profile(?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, driver.driver_id);
            statement.setString(2, driver.driver_email);
            statement.setInt(3, driver.driver_approval_status);
            statement.setString(4, driver.driver_license);
            statement.setString(5, driver.driver_name);
            statement.setString(6, driver.vehicle_name);
            statement.setString(7, driver.vehicle_model);
            statement.setString(8, driver.vehicle_color);
            statement.setString(9, driver.registered_vehicle_number);
            statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
    }

}
