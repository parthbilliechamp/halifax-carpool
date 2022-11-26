package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DriverRegistrationDaoImpl implements IDriverRegistrationDao {

    IDatabase database;
    Connection connection;

    public DriverRegistrationDaoImpl() {

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

    public void registerDriver(Driver driver) {

        try {
            database = new DatabaseImpl();
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL insert_driver_details('" + driver.driver_email + "', '" + driver.driver_password + "', '" + driver.driver_license + "', '" + driver.driver_name + "', '" + driver.registered_vehicle_number + "', '" + new SimpleDateFormat("yyyy-MM-dd").format(getLicenseDate(driver)) + "', '" + driver.vehicle_name + "', '" + driver.vehicle_model + "', '" + driver.vehicle_color + "', " + driver.driver_approval_status + ")");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
}
