package com.chris.illinibus.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A route request for push notification and main page countdown
 * Created by chrisfang on 11/28/16.
 */

public class RouteRequest {
    String stopName;
    double stopLatitude;
    double stopLongitude;
    Date expectedDeparture;
    String expectedArrival;
    String headSign;
    String destination;

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public double getStopLatitude() {
        return stopLatitude;
    }

    public void setStopLatitude(double stopLatitude) {
        this.stopLatitude = stopLatitude;
    }

    public double getStopLongitude() {
        return stopLongitude;
    }

    public void setStopLongitude(double stopLongitude) {
        this.stopLongitude = stopLongitude;
    }

    public Date getExpectedDeparture() {
        return expectedDeparture;
    }

    public void setExpectedDeparture(String expectedArrival) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss z");
        expectedArrival = expectedArrival.replace('T', ' ').replace("-06:00", " CST").replaceAll("-", " ");
        try {
            expectedDeparture = sdf.parse(expectedArrival);
        } catch (ParseException e) {
        }
    }

    public String getHeadSign() {
        return headSign;
    }

    public void setHeadSign(String headSign) {
        this.headSign = headSign;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
