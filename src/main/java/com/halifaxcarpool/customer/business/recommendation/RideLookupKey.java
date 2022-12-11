package com.halifaxcarpool.customer.business.recommendation;

import java.util.Objects;

public class RideLookupKey {
    int firstRideId;
    int secondRideId;

    RideLookupKey(int firstRideId, int secondRideId) {
        this.firstRideId = firstRideId;
        this.secondRideId = secondRideId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideLookupKey that = (RideLookupKey) o;
        return firstRideId == that.firstRideId && secondRideId == that.secondRideId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstRideId, secondRideId);
    }
}
