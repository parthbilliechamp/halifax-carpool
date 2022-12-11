package com.halifaxcarpool.admin.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LocationPopularityDaoImpl implements LocationPopularityDao{

    protected final IDatabase database;
    protected Connection connection;

    public LocationPopularityDaoImpl(){
        database = new DatabaseImpl();
    }
    @Override
    public List<String> getPickUpLocations() {
        List<String> streetNames = new ArrayList<>();
        connection = database.openDatabaseConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL get_pickup_locations()");
            while(resultSet.next()){
                streetNames.add(resultSet.getString("start_location"));
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }finally {
            database.closeDatabaseConnection();
        }
        return streetNames;
    }
}
