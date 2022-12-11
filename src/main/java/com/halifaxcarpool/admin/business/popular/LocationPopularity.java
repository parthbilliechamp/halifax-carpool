package com.halifaxcarpool.admin.business.popular;

import java.util.List;
import java.util.Map;

public interface LocationPopularity {
    Map<Integer, List<String>> getPopularPickUpLocations();
}
