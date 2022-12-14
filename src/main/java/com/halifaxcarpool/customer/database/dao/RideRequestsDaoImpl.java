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
            String SQL_STRING = "{CALL insert_ride_request(?,?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, rideRequest.getRideRequestId());
            stmt.setString(2, rideRequest.getStartLocation());
            stmt.setString(3, rideRequest.getEndLocation());
            stmt.setInt(4, rideRequest.getCustomerId());
            stmt.setString(5, new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
                    .format(Calendar.getInstance().getTime()));
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

            String SQL_STRING = "{CALL view_ride_requests(?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, customerId);

            ResultSet resultSet = stmt.executeQuery();

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

            String SQL_STRING = "{CALL get_ride_requests_count(?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideId);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return 0;
    }

    @Override
    public int getCustomerId(int rideRequestId) {
        try{
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL get_customer_id_from_ride_request(?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideRequestId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return 0;
    }

    public void cancelRideRequest(RideRequest rideRequest) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL cancel_ride_request(?, ?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideRequest.getRideRequestId());
            statement.setInt(2, rideRequest.getCustomerId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RideRequest getRideRequest(int rideRequestId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL get_ride_request(?)");
            statement.setInt(1, rideRequestId);
            ResultSet resultSet = statement.executeQuery();
            return buildRideRequestsFrom(resultSet).get(0);

        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }
        finally {
            database.closeDatabaseConnection();
        }
    }

    private static List<RideRequest> buildRideRequestsFrom(ResultSet resultSet) throws SQLException {

        List<RideRequest> rideRequests = new ArrayList<>();
        while (resultSet.next()) {
            String rideReqIdLabel = "ride_req_id";
            String customerIdLabel = "customer_id";
            String startLocationLabel = "start_location";
            String endLocationLabel = "end_location";
            int rideRequestId = Integer.parseInt(resultSet.getString(rideReqIdLabel));
            int customerId = Integer.parseInt(resultSet.getString(customerIdLabel));
            String startLocation = resultSet.getString(startLocationLabel);
            String endLocation = resultSet.getString(endLocationLabel);
            RideRequest rideRequest = new RideRequest(rideRequestId, customerId, startLocation, endLocation);
            rideRequests.add(rideRequest);
        }
        return rideRequests;
    }

}
