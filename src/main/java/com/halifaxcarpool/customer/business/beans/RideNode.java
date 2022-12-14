package com.halifaxcarpool.customer.business.beans;

import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.recommendation.DistanceFinder;

import java.util.Objects;

public class RideNode implements Comparable<RideNode> {

    private final double latitude;
    private final double longitude;
    private int rideId;
    private final int sequence;

    public RideNode(double latitude, double longitude, int rideId, int sequence) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rideId = rideId;
        this.sequence = sequence;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getSequence() {
        return sequence;
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
        LatLng latLngStart = new LatLng(latitude, longitude);
        LatLng latLngEnd = new LatLng(rideNode.latitude, rideNode.longitude);
        return (int)DistanceFinder.findDistance(latLngStart, latLngEnd);
    }

    @Override
    public String toString() {
        return "RideNode{" +
                "rideId=" + rideId +
                '}';
    }

}
