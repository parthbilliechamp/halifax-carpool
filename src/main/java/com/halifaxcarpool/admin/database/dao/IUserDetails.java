package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public abstract class IUserDetails {

    protected final IDatabase database;
    protected Connection connection;

    public IUserDetails(){
        database = new DatabaseImpl();
    }

    public abstract int getNumberOfUsers();

    public int getNumberOfRides(){
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_ride_count()");
            return resultSet.getInt("ride_count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
    }

    public int getNumberOfSeats(){
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_seats_offered()");
            return resultSet.getInt("seats_offered");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
    }

    public int getAverageSeats(){
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_average_seats_offered()");
            return resultSet.getBigDecimal("avg_seats_offered").intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
    }

    public Map<Integer, List<String>> getRideLocations(){
        return null;
    }

}
