package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import java.text.DecimalFormat;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class RideToRequestMapperDaoImpl implements IRideToRequestMapperDao {

    IDatabase database;
    Connection connection;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public RideToRequestMapperDaoImpl() {
        database = new DatabaseImpl();
    }
    @Override
    public void insertRideToRequestMapper(int rideId, int rideRequestId, String status, double amount) {
        try {
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL insert_ride_to_req_map(?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, rideId);
            stmt.setInt(2, rideRequestId);
            stmt.setString(3, status);
            stmt.setDouble(4,amount);

            stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public List<RideRequest> viewReceivedRequests(int rideId) {
        List<RideRequest> receivedRideRequestList = new ArrayList<>();
        try {
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL view_received_requests(?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, rideId);

            ResultSet resultSet = stmt.executeQuery();

            receivedRideRequestList = buildReceivedRideRequestsFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return receivedRideRequestList;
    }

    @Override
    public List<RideRequest> viewRidePassengers(int rideId) {
        List<RideRequest> receivedRideRequestList = new ArrayList<>();
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "CALL view_approved_request_customers(?)";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideId);
            ResultSet resultSet = statement.executeQuery();
            receivedRideRequestList = buildReceivedRideRequestsFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return receivedRideRequestList;
    }

    @Override
    public void updateRideRequestStatus(int rideId, int rideRequestId, String status) {
        try{
            connection = database.openDatabaseConnection();
            String query = "CALL update_ride_request_status(?,?,?);";
            CallableStatement statement = connection.prepareCall(query);

            statement.setInt(1,rideId);
            statement.setInt(2,rideRequestId);
            statement.setString(3,status.toUpperCase());

            System.out.println(query);
            int result = statement.executeUpdate();
            System.out.println("updated cols:"+result);

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //database.closeDatabaseConnection();
        }

    }

    @Override
    public void updatePaymentAmount(int rideId, int rideRequestId, double amount) {
        try{
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery
                    ("CALL update_ride_request_fair_price("+
                            rideId+","+rideRequestId+","+amount+")");

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public double getPaymentAmount(int rideId,int rideRequestId) {
        try{
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL get_fair_price(" +
                    ""+rideId+","+rideRequestId+")");
            resultSet.next();
            return Double.parseDouble(resultSet.getString(1));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return 0;
    }

    private List<RideRequest> buildReceivedRideRequestsFrom(ResultSet resultSet) {
        List<RideRequest> resultList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int rideRequestId = Integer.parseInt(resultSet.getString(1));
                int customerId = Integer.parseInt(resultSet.getString(2));
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
