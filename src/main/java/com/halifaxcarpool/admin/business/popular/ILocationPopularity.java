package com.halifaxcarpool.admin.business.popular;

import java.util.List;
import java.util.Map;

public interface ILocationPopularity {

    Map<Integer, List<String>> getPopularPickUpLocations();

}
