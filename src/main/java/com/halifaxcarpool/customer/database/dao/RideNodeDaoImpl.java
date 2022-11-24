package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RideNodeDaoImpl implements IRideNodeDao {

    IDatabase database;
    Connection connection;

    public RideNodeDaoImpl() {
        database = new DatabaseImpl();
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
