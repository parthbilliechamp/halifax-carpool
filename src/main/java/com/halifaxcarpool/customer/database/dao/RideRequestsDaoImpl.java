package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RideRequestsDaoImpl implements IRideRequestsDao {

    IDatabase database;
    Connection connection;

    public RideRequestsDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void insertRideRequest(RideRequest rideRequest) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            System.out.println("CALL insert_ride_request(" + rideRequest.rideRequestId + "," + rideRequest.startLocation + "," +
                                       rideRequest.endLocation + "," + rideRequest.customerId + "," + "2022-11-17" + ")");
            // TODO Get method which return date time.
            // TODO: Research on calling this method better
            statement.executeQuery("CALL insert_ride_request(" + rideRequest.rideRequestId + ", '" + rideRequest.startLocation + "', '" +
                    rideRequest.endLocation + "', " + rideRequest.customerId + ", '" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //database.closeDatabaseConnection();
        }
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
            //database.closeDatabaseConnection();
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
