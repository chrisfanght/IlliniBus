package com.chris.illinibus.Models.Network;

/**
 * JSON form of the returned value from Stop API
 * Created by chrisfang on 11/17/16.
 */

public class StopTime {
    String arrival_time;
    String departure_time;
    Trip trip;

    public String getArrivalTime() {
        return arrival_time;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrival_time = arrivalTime;
    }

    public String getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(String departureTime) {
        this.departure_time = departureTime;
    }

    public Trip getBusTrip() {
        return trip;
    }

    public void setBusTrip(Trip busTrip) {
        this.trip = busTrip;
    }
}
