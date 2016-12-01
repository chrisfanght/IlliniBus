package com.chris.illinibus.Models.Network;

/**
 * JSON form of the returned value from Stop API
 * Created by chrisfang on 11/17/16.
 */

public class RouteTime {
    String expected;
    int expected_mins;
    String headsign;
    Trip trip;

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public Trip getBusTrip() {
        return trip;
    }

    public void setBusTrip(Trip busTrip) {
        this.trip = busTrip;
    }

    public int getExpectedMins() {
        return expected_mins;
    }

    public void setExpectedMins(int expected_mins) {
        this.expected_mins = expected_mins;
    }

    public String getHeadsign() {
        return headsign;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

}
