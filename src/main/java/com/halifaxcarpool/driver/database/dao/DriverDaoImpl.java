package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserDao;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverDaoImpl extends IUserDao {

    private final IDatabase database;
    private Connection connection;

    public DriverDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void registerUser(User user) throws Exception {
        Driver driver = (Driver) user;
        try {
            String pattern = "yyyy-MM-dd";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            String SQL_STRING = "{CALL insert_driver_details(?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, driver.getDriverEmail());
            stmt.setString(2, driver.getDriverPassword());
            stmt.setString(3, driver.getDriverLicense());
            stmt.setString(4, driver.getDriverName());
            stmt.setString(5, driver.getRegisteredVehicleNumber());
            stmt.setString(6, new SimpleDateFormat(pattern).format(getLicenseDate(driver)));
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
    public boolean updateUser(User user) {
        Driver driverUser = (Driver) user;
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL update_driver_profile(?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, driverUser.getDriverId());
            statement.setString(2, driverUser.getDriverEmail());
            statement.setInt(3, driverUser.getDriverApprovalStatus());
            statement.setString(4, driverUser.getDriverLicense());
            statement.setString(5, driverUser.getDriverName());
            statement.setString(6, driverUser.getVehicleName());
            statement.setString(7, driverUser.getVehicleModel());
            statement.setString(8, driverUser.getVehicleColor());
            statement.setString(9, driverUser.getRegisteredVehicleNumber());
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
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

}
