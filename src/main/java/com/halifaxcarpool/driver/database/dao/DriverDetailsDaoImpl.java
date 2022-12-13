package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.admin.database.dao.IUserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverDetailsDaoImpl extends IUserDetails {

    public DriverDetailsDaoImpl(){
        super();
    }

    @Override
    public int getNumberOfUsers() {
        try {
            String countLabel = "count";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_driver_count()");
            resultSet.next();
            return resultSet.getInt(countLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }
}
