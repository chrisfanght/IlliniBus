package com.chris.illinibus.Services;

import com.chris.illinibus.Models.Network.RouteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API calls for the CUMTD API
 * Created by chrisfang on 11/17/16.
 */

public interface BusAPI {
    String API_KEY = "540c9808c82b43928e0c63aeed553f33";

    /**
     * Get bus departure time information based on StopId
     *
     * @param stopId
     * @return
     */
    @GET("GetDeparturesByStop?key=" + API_KEY)
    Call<RouteResponse> getStopTimes(@Query("stop_id") String stopId);
}
