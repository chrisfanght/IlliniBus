package com.chris.illinibus.Services;

import com.chris.illinibus.Models.StopRequest;
import com.chris.illinibus.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * API calls for the backend server
 * Created by chrisfang on 11/17/16.
 */

public interface NetworkAPI {
    /**
     * Sign up with google account to record information in the backend
     * @param user user detailed information
     * @return
     */
    @POST("signin")
    Call<User> signup(@Body User user);

    /**
     * Request a notification for a certain bus
     * @param stopRequest detailed information about a bus
     * @return
     */
    @POST("requestBus")
    Call<StopRequest> requestStop(@Body StopRequest stopRequest);
}
