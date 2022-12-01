package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RideNodeDaoImpl implements IRideNodeDao {

    IDatabase database;
    Connection connection;

    public RideNodeDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void insertRideNodes(List<RideNode> rideNodes) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            Iterator<RideNode> iterator = rideNodes.iterator();
            while (iterator.hasNext()) {
                RideNode rideNode = iterator.next();
                statement.addBatch("CALL insert_ride_nodes(" + rideNode.rideId +
                        "," + rideNode.latitude + "," + rideNode.longitude +
                        "," + rideNode.sequence + ")");
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public List<RideNode> getRideNodes(LatLng latLng) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("CALL view_ride_nodes(")
                    .append(latLng.latitude)
                    .append(",")
                    .append(latLng.longitude)
                    .append(")");
            ResultSet resultSet =
                    statement.executeQuery(queryBuilder.toString());

            return buildRideNodesFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }

    @Override
    public int getLatestRideId() {
        int rideId = 0;
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            String SQL_STRING = "CALL get_max_ride_id()";
            ResultSet resultSet = statement.executeQuery(SQL_STRING);
            while (resultSet.next()) {
                rideId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return rideId;
    }

    private static List<RideNode> buildRideNodesFrom(ResultSet resultSet) throws SQLException {

        List<RideNode> rideNodes = new ArrayList<>();
        while (resultSet.next()) {
            int rideId = Integer.parseInt(resultSet.getString("ride_id"));
            double latitude = Double.parseDouble(resultSet.getString("latitude"));
            double longitude = Double.parseDouble(resultSet.getString("longitude"));
            int sequence = Integer.parseInt(resultSet.getString("sequence"));
            RideNode rideNode = new RideNode(latitude, longitude, rideId, sequence);
            rideNodes.add(rideNode);
        }
        return rideNodes;
    }

}
