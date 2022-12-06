package com.halifaxcarpool.admin.statistics;

import java.util.List;

public class DriverDataCollector implements IRawDataCollector{

    @Override
    public int getTotalUsers() {
        return 0;
    }

    @Override
    public int getTotalRides() {
        return 0;
    }

    @Override
    public int getTotalSeats() {
        return 0;
    }

    @Override
    public List<Integer> getSeats() {
        return null;
    }

    @Override
    public List<Float> getRideDistance() {
        return null;
    }
}
