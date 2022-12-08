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
            statement.executeQuery("CALL insert_driver_details('" + driver.getDriver_email() + "', '" +
                    driver.getDriver_password() + "', '" + driver.getDriver_license() + "', '" +
                    driver.getDriver_name() + "', '" + driver.getRegistered_vehicle_number() + "', '" +
                    new SimpleDateFormat("yyyy-MM-dd").format(getLicenseDate(driver)) + "', '" +
                    driver.getVehicle_name() + "', '" + driver.getVehicle_model() + "', '" + driver.getVehicle_color() +
                    "', " + driver.getDriver_approval_status() + ")");

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
