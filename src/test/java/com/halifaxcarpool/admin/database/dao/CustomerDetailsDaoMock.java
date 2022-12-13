package com.halifaxcarpool.admin.database.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDetailsDaoMock extends IUserDetails {

    private static final int numberOfUsers = 19;
    private static final int numberOfRides = 45;
    private static final int numberOfSeats = 65;
    private static final int averageSeats = 4;

    public CustomerDetailsDaoMock(){
        super();
    }

    @Override
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    @Override
    public int getNumberOfRides(){
        return numberOfRides;
    }

    @Override
    public int getNumberOfSeats(){
        return numberOfSeats;
    }

    @Override
    public int getAverageSeats(){
        return averageSeats;
    }

    @Override
    public Map<Integer, List<String>> getRideLocations(){
        Map<Integer, List<String>> distances = new HashMap<>();
        List<String> locations = new ArrayList<>();
        locations.add("6328-6276 Quinpool Rd, Halifax, NS B3L 1A5");
        locations.add("6056 University Ave, Halifax, NS B3H 1W5");
        distances.put(1,locations);
        return distances;
    }

}
