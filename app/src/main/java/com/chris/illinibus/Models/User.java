package com.chris.illinibus.Models;

/**
 * User information in the backend server
 * Created by chrisfang on 11/17/16.
 */

public class User {
    String googleToken;
    String ipAddress;
    String email;

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
