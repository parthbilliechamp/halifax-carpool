package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RideRequestsDaoImpl implements IRideRequestsDao {

    IDatabase database;
    Connection connection;

    public RideRequestsDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void createRideRequest(RideRequest rideRequest) {

    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId)  {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_ride_requests(" + customerId + ")");

            return buildRideRequestsFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }

    private static List<RideRequest> buildRideRequestsFrom(ResultSet resultSet) throws SQLException {

        List<RideRequest> rideRequests = new ArrayList<>();
        while (resultSet.next()) {
            int rideRequestId = Integer.parseInt(resultSet.getString("ride_req_id"));
            int customerId = Integer.parseInt(resultSet.getString("customer_id"));
            String startLocation = resultSet.getString("start_location");
            String endLocation = resultSet.getString("end_location");
            RideRequest rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
            rideRequests.add(rideRequest);
        }
        return rideRequests;
    }

}
