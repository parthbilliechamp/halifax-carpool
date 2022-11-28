package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RidesDaoImpl implements IRidesDao {

    private final IDatabase database;
    private Connection connection;

    public RidesDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public void createNewRide(Ride ride) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            // TODO Get method which return date time.
            // TODO: Research on calling this method better

            //ride.setDateTime(ride.getDateTime().replace("T", " "));

            statement.executeQuery("CALL create_new_ride(" + ride.getDriverId() + ", '" +
                    ride.getStartLocation() + "', '" + ride.getEndLocation() + "', " + ride.getSeatsOffered() + ", "
                    + ride.getRideStatus() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //database.closeDatabaseConnection();
        }
    }

    @Override
    public List<Ride> getRides(int driverId) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_rides(" + driverId + ")");
            return buildRidesFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //connection.close();
        }
        return new ArrayList<>();
    }

    @Override
    public Ride getRide(int rideId) {
        Ride ride = null;
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_ride(" + rideId + ")");
            ride = buildRidesFrom(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //connection.close();
        }
        return ride;
    }

    @Override
    public void insertRideNodes(List<RideNode> rideNodes) {
        try {
            connection = database.openDatabaseConnection();
            Statement statement = connection.createStatement();
            Iterator<RideNode> iterator = rideNodes.iterator();
            while (iterator.hasNext()) {
                RideNode rideNode = iterator.next();
                statement.addBatch("CALL insert_ride_nodes(" + rideNode.rideId +
                        "," + rideNode.latitude + "," + rideNode.longitude +
                        "," + rideNode.sequence + ")");
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //database.closeDatabaseConnection();
        }
    }

    private static List<Ride> buildRidesFrom(ResultSet resultSet) throws SQLException {

        List<Ride> rideList = new ArrayList<>();
        while (resultSet.next()) {
            int rideId = Integer.parseInt(resultSet.getString("ride_id"));
            int driverId = Integer.parseInt(resultSet.getString("driver_id"));
            String startLocation = resultSet.getString("start_location");
            String endLocation = resultSet.getString("end_location");
            int seatsOffered = Integer.parseInt(resultSet.getString("ride_id"));
            byte rideStatus = Byte.parseByte(resultSet.getString("ride_status"));
            //TODO convert to local date time
            String dateTime = resultSet.getString("ride_date_time");
            Ride ride = new Ride(rideId, driverId, startLocation, endLocation, seatsOffered, rideStatus, dateTime);
            rideList.add(ride);
        }
        return rideList;
    }

}
