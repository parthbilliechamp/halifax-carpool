package com.halifaxcarpool.admin.database.dao;

import java.util.ArrayList;
import java.util.List;

public class LocationPopularityDaoMock implements ILocationPopularityDao {

    private List<String> locations;

    public LocationPopularityDaoMock(){
        locations = new ArrayList<>();
        locations.add("8979, University Avenue, Halifax");
        locations.add("8772, Quinnpool, Halifax");
        locations.add("4566, Spring Garden, Halifax");
        locations.add("2244, Spring Garden, Halifax");
        locations.add("4888, Spring Garden, Halifax");
    }

    @Override
    public List<String> getPickUpLocations() {
        return locations;
    }
}
