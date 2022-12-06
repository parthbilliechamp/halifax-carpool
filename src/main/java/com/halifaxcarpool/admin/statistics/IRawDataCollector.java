package com.halifaxcarpool.admin.statistics;

import java.util.List;

public interface IRawDataCollector {
    int getTotalUsers();
    int getTotalRides();
    int getTotalSeats();
    List<Integer> getSeats();
    List<Float> getRideDistance();
}
