package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.authentication.IUserAuthentication;
import com.halifaxcarpool.customer.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.customer.database.dao.IUserDao;
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
    public boolean updateUser(User user) {
        Driver driverUser = (Driver) user;
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL update_driver_profile(?,?,?,?,?,?,?,?,?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, driverUser.getDriver_id());
            statement.setString(2, driverUser.getDriver_email());
            statement.setInt(3, driverUser.getDriver_approval_status());
            statement.setString(4, driverUser.getDriver_license());
            statement.setString(5, driverUser.getDriver_name());
            statement.setString(6, driverUser.getVehicle_name());
            statement.setString(7, driverUser.getVehicle_model());
            statement.setString(8, driverUser.getVehicle_color());
            statement.setString(9, driverUser.getRegistered_vehicle_number());
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public void registerUser(User user) {
        Driver driver = (Driver) user;
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL insert_driver_details('" + driver.getDriver_email() + "', '" +
                    driver.getDriver_password() + "', '" + driver.getDriver_license() + "', '" +
                    driver.getDriver_name() + "', '" + driver.getRegistered_vehicle_number() + "', '" +
                    new SimpleDateFormat("yyyy-MM-dd").format(getLicenseDate(driver)) + "', '" +
                    driver.getVehicle_name() + "', '" + driver.getVehicle_model() + "', '" + driver.getVehicle_color() +
                    "', " + driver.getDriver_approval_status() + ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    private Date getLicenseDate(Driver driver) {
        String licenseExpiryDate;
        licenseExpiryDate = driver.getLicense_expiry_date();
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
