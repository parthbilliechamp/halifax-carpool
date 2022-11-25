package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RidesDaoImpl implements IRidesDao {

    private final IDatabase database;
    private final Connection connection;

    public RidesDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public void createNewRide(Ride ride) {
        try {
            Statement statement = connection.createStatement();
            // TODO Get method which return date time.
            // TODO: Research on calling this method better

            ride.setDateTime(ride.getDateTime().replace("T", " "));

            statement.executeQuery("CALL create_new_ride(" + ride.getRideId() + "," + ride.getDriverId() + ", '" +
                    ride.getStartLocation() + "', '" + ride.getEndLocation() + "', " + ride.getSeatsOffered() + ", "
                    + ride.getRideStatus() + ", '"
                    + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new SimpleDateFormat("YYYY-MM-dd HH:mm").parse(ride.getDateTime().toString())) + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Ride> getRides(int driverId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("CALL view_rides(" + driverId + ")");

            return buildRideRequestsFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //connection.close();
        }
        return new ArrayList<>();
    }

    private static List<Ride> buildRideRequestsFrom(ResultSet resultSet) throws SQLException {

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
