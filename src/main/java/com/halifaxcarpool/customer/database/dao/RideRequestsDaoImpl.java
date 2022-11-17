package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseConnection;
import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RideRequestsDaoImpl implements IRideRequestsDao {

    IDatabase database;
    Connection connection;

    public RideRequestsDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public void createRideRequest(RideRequest rideRequest) {

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
