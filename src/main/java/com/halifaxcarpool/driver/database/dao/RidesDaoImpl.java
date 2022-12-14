package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Driver;
import com.halifaxcarpool.driver.business.beans.Ride;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RidesDaoImpl implements IRidesDao {

    private final IDatabase database;
    private Connection connection;

    public RidesDaoImpl() {
        database = new DatabaseImpl();
    }

    @Override
    public boolean createNewRide(Ride ride) {
        try {
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL create_new_ride(?, ?, ?, ?, ?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, ride.getDriverId());
            stmt.setString(2, ride.getStartLocation());
            stmt.setString(3, ride.getEndLocation());
            stmt.setInt(4, ride.getSeatsOffered());
            stmt.setInt(5, ride.getRideStatus());

            stmt.executeQuery();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            database.closeDatabaseConnection();
        }
    }

    @Override
    public List<Ride> getRides(int driverId) {
        try {
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL view_rides(?)}";
            CallableStatement stmt = connection.prepareCall(SQL_STRING);
            stmt.setInt(1, driverId);

            ResultSet resultSet = stmt.executeQuery();

            return buildRidesFrom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Ride> getActiveRides(int customerId) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL view_ongoing_rides(?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            return buildOngoingRidesFrom(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return new ArrayList<>();
    }

    @Override
    public Ride getRide(int rideId) {
        Ride ride = null;
        try {
            connection = database.openDatabaseConnection();

            String SQL_STRING = "{CALL view_ride(?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideId);
            ResultSet resultSet = statement.executeQuery();
            ride = buildRidesFrom(resultSet).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return ride;
    }

    @Override
    public boolean startRide(int rideId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL start_ride(?)");
            statement.setInt(1, rideId);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean stopRide(int rideId) {
        try{
            connection = database.openDatabaseConnection();
            CallableStatement statement = connection.prepareCall("CALL stop_ride(?)");
            statement.setInt(1, rideId);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean cancelRide(int rideId) {
        try {
            connection = database.openDatabaseConnection();
            String SQL_STRING = "{CALL cancel_ride(?)}";
            CallableStatement statement = connection.prepareCall(SQL_STRING);
            statement.setInt(1, rideId);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        return false;
    }

    private static List<Ride> buildRidesFrom(ResultSet resultSet) throws SQLException {

        List<Ride> rideList = new ArrayList<>();
        while (resultSet.next()) {
            String rideIdLiteral = "ride_id";
            String driverIdLiteral = "driver_id";
            String startLocationLiteral = "start_location";
            String endLocationLocation = "end_location";
            String seatsOfferedLiteral = "seats_offered";
            String rideStatusLiteral = "ride_status";
            int rideId = Integer.parseInt(resultSet.getString(rideIdLiteral));
            int driverId = Integer.parseInt(resultSet.getString(driverIdLiteral));
            String startLocation = resultSet.getString(startLocationLiteral);
            String endLocation = resultSet.getString(endLocationLocation);
            int seatsOffered = Integer.parseInt(resultSet.getString(seatsOfferedLiteral));
            byte rideStatus = Byte.parseByte(resultSet.getString(rideStatusLiteral));
            Ride ride = new Ride(rideId, driverId, startLocation, endLocation, seatsOffered, rideStatus);
            rideList.add(ride);
        }
        return rideList;
    }

    private static List<Ride> buildOngoingRidesFrom(ResultSet resultSet) throws SQLException {

        List<Ride> rideList = new ArrayList<>();
        while (resultSet.next()) {
            String driverNameLiteral = "driver_name";
            String registeredVehicleNumberLiteral = "registered_vehicle_number";
            String startLocationLiteral = "start_location";
            String endLocationLiteral = "end_location";
            String fairPriceLiteral = "fair_price";
            String paymentIdLiteral = "payment_id";
            String driverName = resultSet.getString(driverNameLiteral);
            String vehicleNumber = resultSet.getString(registeredVehicleNumberLiteral);
            String startLocation = resultSet.getString(startLocationLiteral);
            String endLocation = resultSet.getString(endLocationLiteral);
            long paymentId = resultSet.getLong(paymentIdLiteral);
            double fare = Double.parseDouble(resultSet.getString(fairPriceLiteral));
            Ride ride = new Ride();
            ride.setStartLocation(startLocation);
            ride.setEndLocation(endLocation);
            ride.withFare(fare);
            ride.withPaymentId(paymentId);
            Driver driver = new Driver();
            driver.setDriverName(driverName);
            driver.setRegisteredVehicleNumber(vehicleNumber);

            ride.withDriver(driver);
            rideList.add(ride);
        }
        return rideList;
    }

}
