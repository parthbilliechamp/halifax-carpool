package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.admin.database.dao.IUserDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDetailsDaoImpl extends IUserDetails {

    public CustomerDetailsDaoImpl(){
        super();
    }

    @Override
    public int getNumberOfUsers() {
        try {
            String countLabel = "count";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_customer_count()");
            resultSet.next();
            return resultSet.getInt(countLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

}
