package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.database.dao.IUserAuthenticationDao;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DriverAuthenticationDaoImpl implements IUserAuthenticationDao {
    IDatabase database;
    Connection connection;

    public DriverAuthenticationDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public Driver authenticate(String username, String password) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL login_driver('" + username + "', '" + password + "')");
            return buildDriverFrom(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    private static Driver buildDriverFrom(ResultSet resultSet) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Driver driver = null;

        while (resultSet.next()) {
            int driver_id = Integer.parseInt(resultSet.getString("driver_id"));
            String driver_email = resultSet.getString("driver_email");
            String driver_password = resultSet.getString("driver_password");
            String driver_license = resultSet.getString("driver_license");
            String driver_name = resultSet.getString("driver_name");
            String registered_vehicle_number = resultSet.getString("registered_vehicle_number");
            Date license_expiry_date_extracted = resultSet.getDate("license_expiry_date");
            String license_expiry_date = dateFormat.format(license_expiry_date_extracted);
            String vehicle_name = resultSet.getString("vehicle_name");
            String vehicle_model = resultSet.getString("vehicle_model");
            String vehicle_color = resultSet.getString("vehicle_color");
            int driver_approval_status = Integer.parseInt(resultSet.getString("driver_approval_status"));

            driver = new Driver(driver_id, driver_email, driver_password, driver_license, driver_name, registered_vehicle_number, license_expiry_date, vehicle_name, vehicle_model, vehicle_color, driver_approval_status);
        }
        return driver;
    }




}
