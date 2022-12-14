package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IUserDetails {

    protected final IDatabase database;
    protected Connection connection;

    public IUserDetails(){
        database = new DatabaseImpl();
    }

    public abstract int getNumberOfUsers();

    public int getNumberOfRides() {
        try {
            String rideCountLiteral = "ride_count";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_ride_count()");
            resultSet.next();
            return resultSet.getInt(rideCountLiteral);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
    }

    public int getNumberOfSeats(){
        try {
            String seatsOfferedLiteral = "seats_offered";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_seats_offered()");
            resultSet.next();
            return resultSet.getInt(seatsOfferedLiteral);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

    public int getAverageSeats(){
        try {
            String avgSeatsOfferedLiteral = "avg_seats_offered";
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL get_average_seats_offered()");
            resultSet.next();
            return resultSet.getBigDecimal(avgSeatsOfferedLiteral).intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            database.closeDatabaseConnection();
        }
    }

    public Map<Integer, List<String>> getRideLocations(){
        Map<Integer, List<String>> rideLocations = new HashMap<>();
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("CALL get_ride_locations()");
            while(resultSet.next()){
                List<String> locations = new ArrayList<>();
                String startLocationLiteral = "start_location";
                String endLocationLiteral = "end_location";
                String rideIdLiteral = "ride_id";
                locations.add(resultSet.getString(startLocationLiteral));
                locations.add(resultSet.getString(endLocationLiteral));
                rideLocations.put(resultSet.getInt(rideIdLiteral), locations);
            }
            return rideLocations;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            database.closeDatabaseConnection();
        }
    }

}
