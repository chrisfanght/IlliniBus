package com.chris.illinibus.Models.Network;

import java.util.List;

/**
 * Created by chrisfang on 11/27/16.
 */

public class RouteResponse {
    List<RouteTime> departures;

    public List<RouteTime> getDepartures() {
        return departures;
    }

    public void setDepartures(List<RouteTime> stop_times) {
        this.departures = stop_times;
    }
}
