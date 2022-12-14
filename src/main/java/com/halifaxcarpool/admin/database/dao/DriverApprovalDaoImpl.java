package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverApprovalDaoImpl implements IDriverApprovalDao {
    protected final IDatabase database;
    protected Connection connection;

    public DriverApprovalDaoImpl(){
        database = new DatabaseImpl();
    }

    @Override
    public List<User> getPendingApprovalDrivers() {
        connection = database.openDatabaseConnection();
        Statement statement;
        List<User> unapprovedDrivers = new ArrayList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL get_unapproved_drivers()");
            while(resultSet.next()){
                Driver driver = new Driver();
                String driverNameLiteral = "driver_name";
                driver.setDriverName(resultSet.getString(driverNameLiteral));
                String registeredVehicleNumberLiteral = "registered_vehicle_number";
                driver.setRegisteredVehicleNumber(resultSet.getString(registeredVehicleNumberLiteral));
                String driverLicenseLiteral = "driver_license";
                driver.setDriverLicense(resultSet.getString(driverLicenseLiteral));
                String licenseExpiryDateLiteral = "license_expiry_date";
                driver.setLicenseExpiryDate(resultSet.getString(licenseExpiryDateLiteral));
                unapprovedDrivers.add(driver);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
        return unapprovedDrivers;
    }

    @Override
    public boolean acceptDriverRequest(String id) {
        connection = database.openDatabaseConnection();
        try {
            String SQL_STRING = "{CALL accept_driver_profile(?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            database.closeDatabaseConnection();
        }
        return true;
    }

    @Override
    public boolean rejectDriverRequest(String id) {
        connection = database.openDatabaseConnection();
        try {
            String SQL_STRING = "{CALL reject_driver_profile(?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        } finally {
            database.closeDatabaseConnection();
        }
        return true;
    }
}
