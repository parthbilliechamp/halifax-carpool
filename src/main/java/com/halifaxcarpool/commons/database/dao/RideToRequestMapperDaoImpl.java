package com.halifaxcarpool.commons.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RideToRequestMapperDaoImpl implements IRideToRequestMapperDao {

    IDatabase database;
    Connection connection;

    public RideToRequestMapperDaoImpl() {
        database = new DatabaseImpl();
    }
    @Override
    public void insertRideToRequestMapper(int rideId, int rideRequestId, String status) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            String SQL_STRING = "CALL insert_ride_to_req_map(" + rideId + "," +
                    rideRequestId + ", '" + status + "' )";
            statement.executeUpdate(SQL_STRING);
        } catch (SQLException e) {
            throw new RuntimeException("Ride Request already sent!!");
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public List<RideRequest> viewReceivedRequests(int rideId) {
        List<RideRequest> receivedRideRequestList = new ArrayList<>();
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            String SQL_STRING = "CALL view_received_requests(" + rideId + ")";
            ResultSet resultSet = statement.executeQuery(SQL_STRING);
            receivedRideRequestList = buildReceivedRideRequestsFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return receivedRideRequestList;
    }

    private List<RideRequest> buildReceivedRideRequestsFrom(ResultSet resultSet) {
        List<RideRequest> resultList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int rideRequestId = Integer.parseInt(resultSet.getString(1));
                int customerId = Integer.parseInt(resultSet.getString(2));
                String date = resultSet.getString(3);
                String startLocation = resultSet.getString(4);
                String endLocation = resultSet.getString(5);
                RideRequest rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
                resultList.add(rideRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

}
