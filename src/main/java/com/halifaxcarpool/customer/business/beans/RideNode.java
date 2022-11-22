package com.halifaxcarpool.customer.business.beans;

import com.halifaxcarpool.customer.business.recommendation.DistanceFinder;

import java.util.Objects;

public class RideNode implements Comparable<RideNode> {

    public double latitude;
    public double longitude;
    public int rideId;
    public int sequence;

    public RideNode(double latitude, double longitude, int rideId, int sequence) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rideId = rideId;
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideNode rideNode = (RideNode) o;
        return rideId == rideNode.rideId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rideId);
    }

    @Override
    public int compareTo(RideNode rideNode) {
        return (int)DistanceFinder.findDistance(latitude, longitude, rideNode.latitude, rideNode.longitude);
    }

    @Override
    public String toString() {
        return "RideNode{" +
                "rideId=" + rideId +
                '}';
    }

}
