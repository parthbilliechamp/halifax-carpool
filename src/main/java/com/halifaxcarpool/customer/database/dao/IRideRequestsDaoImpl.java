package com.halifaxcarpool.customer.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class IRideRequestsDaoImpl implements IRideRequestsDao {

    IDatabase database;
    Connection connection;

    public IRideRequestsDaoImpl(){
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }
    @Override
    public void insertRideRequest(RideRequest rideRequest) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO `CSCI5308_12_DEVINT`.`ride_requests` (`ride_req_id`, `start_location_coordinates`, `end_location_coordinates`, `customer_id`, `req_date_time`) VALUES ('1', '123', '222', '2', '2022-11-16');\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error in closing connection");
                throw new RuntimeException(e);
            }
        }
    }
}
