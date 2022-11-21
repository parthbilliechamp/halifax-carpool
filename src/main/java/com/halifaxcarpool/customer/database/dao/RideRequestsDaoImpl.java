package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseConnection;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
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
        connection = database.openDatabaseConnection();
    }

    @Override
    public void insertRideRequest(RideRequest rideRequest) {
        try {
            Statement statement = connection.createStatement();
            System.out.println("CALL insert_ride_request(" + rideRequest.rideRequestId + "," + rideRequest.startLocation + "," +
                                       rideRequest.endLocation + "," + rideRequest.customerId + "," + "2022-11-17" + ")");
            // TODO Get method which return date time.
            // TODO: Research on calling this method better
            statement.executeQuery("CALL insert_ride_request(" + rideRequest.rideRequestId + ", '" + rideRequest.startLocation + "', '" +
                    rideRequest.endLocation + "', " + rideRequest.customerId + ", '" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<RideRequest> viewRideRequests(int customerId)  {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("CALL view_ride_requests(" + customerId + ")");
            List<RideRequest> rideRequests = new ArrayList<>();
            RideRequest rideRequest = new RideRequest(1, 1, "Dalhousie", "sds");
            rideRequests.add(rideRequest);
            return rideRequests;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

}
