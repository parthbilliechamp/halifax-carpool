package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.*;
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
            // TODO Get method which return date time.
            // TODO: Research on calling this method better
            String SQL_STRING = "{CALL insert_ride_request(?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, rideRequest.rideRequestId);
            stmt.setString(2, rideRequest.startLocation);
            stmt.setString(3, rideRequest.endLocation);
            stmt.setInt(4, rideRequest.customerId);
            stmt.setString(5, new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
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
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }


    @Override
    public int getRideRequestCount(int rideId) {
        try{
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL get_ride_requests_count("+rideId+")");
            resultSet.next();
            return Integer.parseInt(resultSet.getString(1));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return 0;
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
