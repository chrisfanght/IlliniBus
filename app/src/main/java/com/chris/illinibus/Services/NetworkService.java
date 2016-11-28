package com.chris.illinibus.Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provide network layer for data transmit with backend server and CUMTD api
 * Created by Chris on 11/5/16.
 */

public class NetworkService {
    public static final String BUS_BASE_URL="https://developer.cumtd.com/api/v2.2/JSON/";
    public static final String BACKEND_BASE_URL = "http://162.243.15.223:5000/";

    private BusAPI mBusAPI;
    private NetworkAPI mNetworkAPI;

    public NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BUS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBusAPI =  retrofit.create(BusAPI.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNetworkAPI = retrofit.create(NetworkAPI.class);
    }

    public BusAPI getBusAPI() {
        return mBusAPI;
    }

    public NetworkAPI getNetworkAPI() {
        return mNetworkAPI;
    }
}
