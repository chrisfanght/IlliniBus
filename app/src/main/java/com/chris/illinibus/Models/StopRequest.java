package com.chris.illinibus.Models;

/**
 * JSON request for stopRequest API
 * Created by chrisfang on 11/17/16.
 */

public class StopRequest {
    String stopId;
    String busId;
    String googleToken;

    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }
}
