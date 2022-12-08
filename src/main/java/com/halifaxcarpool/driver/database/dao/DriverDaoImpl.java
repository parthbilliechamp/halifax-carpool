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
            statement.setInt(1, driver.getDriverId());
            statement.setString(2, driver.getDriverEmail());
            statement.setInt(3, driver.getDriverApprovalStatus());
            statement.setString(4, driver.getDriverLicense());
            statement.setString(5, driver.getDriverName());
            statement.setString(6, driver.getVehicleName());
            statement.setString(7, driver.getVehicleModel());
            statement.setString(8, driver.getVehicleColor());
            statement.setString(9, driver.getRegisteredVehicleNumber());
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
