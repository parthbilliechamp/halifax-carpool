package com.halifaxcarpool.driver.database.dao;

import com.halifaxcarpool.commons.database.DatabaseImpl;
import com.halifaxcarpool.commons.database.IDatabase;
import com.halifaxcarpool.driver.business.beans.Ride;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewRidesDaoImpl implements IViewRidesDao {

    private final IDatabase database;
    private final Connection connection;

    public ViewRidesDaoImpl() {
        database = new DatabaseImpl();
        connection = database.openDatabaseConnection();
    }

    @Override
    public List<Ride> getAllRides() {
        try {
            //TODO create procedures and call them.
            //PreparedStatement statement = connection.prepareStatement("select * from rides");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        Ride ride = new Ride();
        ride.rideId = 1;
        ride.startLocation = "Halifax public garden";
        ride.endLocation = "Dalhousie university";
        return new ArrayList<>(List.of(ride));

    }

    @Override
    public List<Ride> getAllRidesForDriver(int driverId) {
        try {
            //TODO create procedures and call them.
            PreparedStatement statement = connection.prepareStatement("select * from rides");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeDatabaseConnection();
        }
        Ride ride = new Ride();
        List<Ride> list = new ArrayList<>();
        list.add(ride);
        return list;
    }

}
