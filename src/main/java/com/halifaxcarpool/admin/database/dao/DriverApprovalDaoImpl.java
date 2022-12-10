package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.business.beans.User;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DriverApprovalDaoImpl implements DriverApprovalDao{
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
                driver.setDriver_name(resultSet.getString("driver_name"));
                driver.setRegistered_vehicle_number(resultSet.getString("registered_vehicle_number"));
                driver.setDriver_license(resultSet.getString("driver_license"));
                driver.setLicense_expiry_date(resultSet.getString("license_expiry_date"));

                unapprovedDrivers.add(driver);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return unapprovedDrivers;
    }
}
