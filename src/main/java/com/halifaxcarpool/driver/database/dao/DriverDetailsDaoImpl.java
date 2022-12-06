package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DriverDetailsDaoImpl implements IDriverDetailsDao{

    private final IDatabase database;
    private Connection connection;

    public DriverDetailsDaoImpl(){
        database = new DatabaseImpl();
    }

    @Override
    public int getNumberOfDrivers() {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
