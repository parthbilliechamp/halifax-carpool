package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverDaoImpl implements IDriverDao {

    private final IDatabase database;
    private Connection connection;

    public DriverDaoImpl() {
        database = new DatabaseImpl();
    }

    private Date getLicenseDate(Driver driver) {
        String licenseExpiryDate;
        licenseExpiryDate = driver.getLicenseExpiryDate();
        Date date;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(licenseExpiryDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public void registerDriver(Driver driver) throws Exception {

        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            String SQL_STRING = "{CALL insert_driver_details(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, driver.getDriverEmail());
            stmt.setString(2, driver.getDriverPassword());
            stmt.setString(3, driver.getDriverLicense());
            stmt.setString(4, driver.getDriverName());
            stmt.setString(5, driver.getRegisteredVehicleNumber());
            stmt.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(getLicenseDate(driver)));
            stmt.setString(7, driver.getVehicleName());
            stmt.setString(8, driver.getVehicleModel());
            stmt.setString(9, driver.getVehicleColor());
            stmt.setInt(10, driver.getDriverApprovalStatus());

            stmt.execute();

        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        } finally {
            database.closeDatabaseConnection();
        }

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
