package com.chris.illinibus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chris.illinibus.Fragments.Adapter.RouteAdapter;
import com.chris.illinibus.Models.Network.RouteResponse;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.Services.BusAPI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Full screen activity for stop details
 */
public class StopDetailActivity extends AppCompatActivity implements Callback<RouteResponse> {
    private static final String FORMAT = "%02d:%02d";

    private Stop mStop;
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private RecyclerView mRecyclerView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_detail);

        mMapView = (MapView) findViewById(R.id.stop_map_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.routes_recycler_view);
        mImageView = (ImageView) findViewById(R.id.no_bus_image);
        mImageView.setVisibility(View.GONE);

        initStopData();
        initLayout();
        initGoogleMap(savedInstanceState);
        initNetwork();
    }

    private void initStopData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mStop = new Stop();
            mStop.setId(extras.getString("ID"));
            mStop.setName(extras.getString("NAME"));
            mStop.setLongitude(extras.getDouble("LONGITUDE"));
            mStop.setLatitude(extras.getDouble("LATITUDE"));
        }
    }

    private void initLayout() {
        getSupportActionBar().setTitle(mStop.getName());
    }

    private void initGoogleMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            public static final int ZOOM_LEVEL = 17;

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setMyLocationEnabled(true);
                CameraPosition cameraPosition = CameraPosition
                        .builder()
                        .target(new LatLng(mStop.getLatitude(), mStop.getLongitude()))
                        .zoom(ZOOM_LEVEL)
                        .build();
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mStop.getLatitude(), mStop.getLongitude()))
                        .draggable(false)
                        .title(mStop.getName()));
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    private void initNetwork() {
        IlliniBusApplication myApplication = (IlliniBusApplication) getApplication();
        BusAPI busAPI = myApplication.getBusNetworkService();
        Call<RouteResponse> call = busAPI.getStopTimes(mStop.getId());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        if (response.body().getDepartures().isEmpty()) {
            mImageView.setVisibility(View.VISIBLE);
        } else {
            mImageView.setVisibility(View.GONE);
        }
        RouteAdapter routeAdapter = new RouteAdapter(response.body().getDepartures(), mStop);
        mRecyclerView.setAdapter(routeAdapter);
    }

    @Override
    public void onFailure(Call<RouteResponse> call, Throwable t) {
        Toast.makeText(StopDetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }
}
