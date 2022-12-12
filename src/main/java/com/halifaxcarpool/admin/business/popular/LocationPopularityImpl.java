package com.halifaxcarpool.admin.business.popular;

import com.halifaxcarpool.admin.database.dao.ILocationPopularityDao;

import java.util.*;

public class LocationPopularityImpl implements ILocationPopularity {

    private ILocationPopularityDao locationPopularityDao;
    private Map<Integer, List<String>> popularLocations;

    public LocationPopularityImpl(ILocationPopularityDao halifaxPopularityDao){
        this.locationPopularityDao = halifaxPopularityDao;
        popularLocations = new HashMap<>();
    }

    @Override
    public Map<Integer, List<String>> getPopularPickUpLocations() {
        List<String> locations = locationPopularityDao.getPickUpLocations();
        List<String> streetNames = new ArrayList<>();

        Iterator<String> locationItr = locations.iterator();
        while (locationItr.hasNext()){
            String completeLocation = locationItr.next();
            String[] locationBreakdown = completeLocation.split(",");

            String streetName = getStreetName(locationBreakdown);
            streetNames.add(streetName);
        }

        int popularLocationOccurrence = findMaximumOccurence(streetNames);
        List<String> popularStreetNames = getPopularStreets(streetNames, popularLocationOccurrence);

        popularLocations.put(popularLocationOccurrence, popularStreetNames);

        return popularLocations;
    }

    private int findMaximumOccurence(List<String> locations){
        int maximum = 0, currentFrequency = 0;
        Iterator<String> iterator = locations.iterator();
        while (iterator.hasNext()){
            currentFrequency = Collections.frequency(locations, iterator.next());
            if(maximum < currentFrequency){
                maximum = currentFrequency;
            }
        }
        return maximum;
    }

    private String getStreetName(String[] locationBreakdown){
        Iterator<String> iterator = Arrays.stream(locationBreakdown).iterator();
        int streetIndex = 0;
        while ((iterator.hasNext())){
            if(iterator.next().replaceAll("\\s", "").equalsIgnoreCase("halifax")){
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
            if(maximumOccurrence == currentFrequency && Boolean.FALSE.equals(popularStreets.contains(streetName))){
                popularStreets.add(streetName);
            }
        }
        return popularStreets;
    }
}
