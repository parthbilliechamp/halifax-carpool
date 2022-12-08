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

    public void registerDriver(Driver driver) {

        try {
            database = new DatabaseImpl();
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL insert_driver_details('" + driver.getDriverEmail() + "', '" +
                    driver.getDriverPassword() + "', '" + driver.getDriverLicense() + "', '" +
                    driver.getDriverName() + "', '" + driver.getRegisteredVehicleNumber() + "', '" +
                    new SimpleDateFormat("yyyy-MM-dd").format(getLicenseDate(driver)) + "', '" +
                    driver.getVehicleName() + "', '" + driver.getVehicleModel() + "', '" + driver.getVehicleColor() +
                    "', " + driver.getDriverApprovalStatus() + ")");

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
