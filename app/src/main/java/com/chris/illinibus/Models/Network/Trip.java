package com.chris.illinibus.Models.Network;

/**
 * A certain bus trip information
 * Created by chrisfang on 11/17/16.
 */

public class Trip {
    String trip_id;
    String trip_headsign;
    String route_id;

    String direction;

    public String getTripId() {
        return trip_id;
    }

    public void setTripId(String tripId) {
        this.trip_id = tripId;
    }

    public String getTripHeadsign() {
        return trip_headsign;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.trip_headsign = tripHeadsign;
    }

    public String getRouteId() {
        return route_id;
    }

    public void setRouteId(String routeId) {
        this.route_id = routeId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
