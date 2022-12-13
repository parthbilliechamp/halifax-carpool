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
    }

    @Override
    public Driver authenticate(String username, String password) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL login_driver(?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();

            return buildDriverFrom(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    private static Driver buildDriverFrom(ResultSet resultSet) throws SQLException {
        String datePattern = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        Driver driver = null;

        while (resultSet.next()) {
            String driverIdLiteral = "driver_id";
            String driverEmailLiteral = "driver_email";
            String driverPasswordLiteral = "driver_password";
            String driverLicenseLiteral = "driver_license";
            String driverNameLiteral = "driver_name";
            String registeredVehicleNumberLiteral = "registered_vehicle_number";
            String licenseExpiryDateLiteral = "license_expiry_date";
            String vehicleNameLiteral = "vehicle_name";
            String vehicleModelLiteral = "vehicle_model";
            String vehicleColorLiteral = "vehicle_color";
            String driverApprovalStatusLiteral = "driver_approval_status";

            int driver_id = Integer.parseInt(resultSet.getString(driverIdLiteral));
            String driver_email = resultSet.getString(driverEmailLiteral);
            String driver_password = resultSet.getString(driverPasswordLiteral);
            String driver_license = resultSet.getString(driverLicenseLiteral);
            String driver_name = resultSet.getString(driverNameLiteral);
            String registered_vehicle_number = resultSet.getString(registeredVehicleNumberLiteral);
            Date license_expiry_date_extracted = resultSet.getDate(licenseExpiryDateLiteral);
            String license_expiry_date = dateFormat.format(license_expiry_date_extracted);
            String vehicle_name = resultSet.getString(vehicleNameLiteral);
            String vehicle_model = resultSet.getString(vehicleModelLiteral);
            String vehicle_color = resultSet.getString(vehicleColorLiteral);
            int driver_approval_status = Integer.parseInt(resultSet.getString(driverApprovalStatusLiteral));

            driver = new Driver(driver_id, driver_email, driver_password, driver_license,
                    driver_name, registered_vehicle_number, license_expiry_date, vehicle_name,
                    vehicle_model, vehicle_color, driver_approval_status);
        }
        return driver;
    }

}
