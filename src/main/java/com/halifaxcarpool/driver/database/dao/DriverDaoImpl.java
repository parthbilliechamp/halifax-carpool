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
    public boolean updateDriverProfile(Driver driver) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL update_driver_profile(?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, driver.getDriver_id());
            statement.setString(2, driver.getDriver_email());
            statement.setInt(3, driver.getDriver_approval_status());
            statement.setString(4, driver.getDriver_license());
            statement.setString(5, driver.getDriver_name());
            statement.setString(6, driver.getVehicle_name());
            statement.setString(7, driver.getVehicle_model());
            statement.setString(8, driver.getVehicle_color());
            statement.setString(9, driver.getRegistered_vehicle_number());
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

}
