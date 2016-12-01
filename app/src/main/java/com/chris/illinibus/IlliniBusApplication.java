package com.chris.illinibus;

import android.app.Application;

import com.chris.illinibus.Models.RouteRequest;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.Services.BusAPI;
import com.chris.illinibus.Services.NetworkAPI;
import com.chris.illinibus.Services.NetworkService;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Shared data for the application
 * Created by Chris on 11/9/16.
 */

public class IlliniBusApplication extends Application {
    private boolean mSignedIn = false;
    private GoogleSignInAccount mGoogleAccount;
    private List<Stop> mStopList;
    private List<Stop> mNearbyList;
    private NetworkService mNetworkService;
    private RouteRequest mRouteRequest;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkService = new NetworkService();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Sign in using google account
     *
     * @param googleSignInAccount
     */
    public void signIn(GoogleSignInAccount googleSignInAccount) {
        mSignedIn = true;
        mGoogleAccount = googleSignInAccount;
    }

    /**
     * Sign out the account
     */
    public void signOut() {
        mSignedIn = false;
    }

    /**
     * Get google account information once a user signed in
     *
     * @return
     */
    public GoogleSignInAccount getGoogleAccount() {
        return mGoogleAccount;
    }

    /**
     * Check if a user has signed in or not
     *
     * @return
     */
    public boolean isSignedIn() {
        return mSignedIn;
    }

    /**
     * Get all information about stops in cumtd system
     *
     * @return a list of stops
     */
    public List<Stop> getStopList() {
        return mStopList;
    }

    /**
     * Set the stop list data using query result from database
     *
     * @param stopList stopList from database
     */
    public void setStopList(List<Stop> stopList) {
        mStopList = new ArrayList<>();
        mStopList.addAll(stopList);
    }

    /**
     * Sort the stops list based on distance to get the nearby stops
     * @return
     */
    public List<Stop> getNearbyStopList() {
        if (mNearbyList == null) {
            mNearbyList = new ArrayList<>();
            mNearbyList.addAll(mStopList);
            Collections.sort(mNearbyList, new Comparator<Stop>() {
                @Override
                public int compare(Stop stop, Stop t1) {
                    return Double.compare(stop.getDistance(), t1.getDistance());
                }
            });
        }
        return mNearbyList.subList(0, 10);
    }

    public BusAPI getBusNetworkService() {
        return mNetworkService.getBusAPI();
    }

    public NetworkAPI getBackendNetworkService() {
        return mNetworkService.getNetworkAPI();
    }

    public boolean existsRouteRequest() {
        return mRouteRequest != null;
    }

    public void cancelRouteRequest() {
        mRouteRequest = null;
    }

    public RouteRequest getRouteRequest() {
        return mRouteRequest;
    }

    /**
     * Set up a routeRequest
     * @param routeRequest
     */
    public void setRouteRequest(RouteRequest routeRequest) {
        mRouteRequest = routeRequest;
    }
}
