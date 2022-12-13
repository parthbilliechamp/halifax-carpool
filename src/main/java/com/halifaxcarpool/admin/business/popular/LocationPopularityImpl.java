package com.halifaxcarpool.admin.business.popular;

import com.halifaxcarpool.admin.database.dao.LocationPopularityDao;

import java.util.*;

public class LocationPopularityImpl implements LocationPopularity {

    private final LocationPopularityDao locationPopularityDao;
    private final Map<Integer, List<String>> popularLocations;

    private static final String COMMA_SEPARATOR = ",";

    public LocationPopularityImpl(LocationPopularityDao halifaxPopularityDao) {
        this.locationPopularityDao = halifaxPopularityDao;
        popularLocations = new HashMap<>();
    }

    @Override
    public Map<Integer, List<String>> getPopularPickUpLocations() {
        List<String> locations = locationPopularityDao.getPickUpLocations();
        List<String> streetNames = new ArrayList<>();

        Iterator<String> locationItr = locations.iterator();
        while (locationItr.hasNext()) {
            String completeLocation = locationItr.next();
            String[] locationBreakdown = completeLocation.split(COMMA_SEPARATOR);

            String streetName = getStreetName(locationBreakdown);
            streetNames.add(streetName);
        }

        int popularLocationOccurrence = findMaximumOccurrence(streetNames);
        List<String> popularStreetNames = getPopularStreets(streetNames, popularLocationOccurrence);

        popularLocations.put(popularLocationOccurrence, popularStreetNames);
        return popularLocations;
    }

    private int findMaximumOccurrence(List<String> locations) {
        int maximum = 0, currentFrequency;
        Iterator<String> iterator = locations.iterator();
        while (iterator.hasNext()) {
            currentFrequency = Collections.frequency(locations, iterator.next());
            if(maximum < currentFrequency){
                maximum = currentFrequency;
            }
        }
        return maximum;
    }

    private String getStreetName(String[] locationBreakdown) {
        Iterator<String> iterator = Arrays.stream(locationBreakdown).iterator();
        int streetIndex = 0;
        while ((iterator.hasNext())) {
            if (iterator.next().replaceAll("\\s", "").equalsIgnoreCase("halifax")) {
                return locationBreakdown[--streetIndex];
            }
            streetIndex++;
        }
        return null;
    }

    private List<String> getPopularStreets(List<String> streetNames, int maximumOccurrence){
        Iterator<String> iterator = streetNames.iterator();
        List<String> popularStreets = new ArrayList<>();
        while (iterator.hasNext()){
            String streetName = iterator.next();
            int currentFrequency = Collections.frequency(streetNames, streetName);
            if(maximumOccurrence == currentFrequency && Boolean.FALSE.equals(popularStreets.contains(streetName))) {
                popularStreets.add(streetName);
            }
        }
        return popularStreets;
    }

}
