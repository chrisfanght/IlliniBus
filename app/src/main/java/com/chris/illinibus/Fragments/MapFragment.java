package com.chris.illinibus.Fragments;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.illinibus.IlliniBusApplication;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Map Fragment using Google Maps API to show all of the stop locations
 * Created by Chris on 11/9/16.
 */

public class MapFragment extends Fragment {
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private LocationManager mLocationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setupGoogleMap(view, savedInstanceState);
        return view;
    }

    /**
     * Set up stop information on the map page
     */
    private void setupGoogleMap(View view, Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            public static final int ZOOM_LEVEL = 17;

            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setMyLocationEnabled(true);

                Location currLocation = getCurrentLocation();
                if (currLocation != null) {
                    // Zoom in Google map to user's current location
                    CameraPosition cameraPosition = CameraPosition
                            .builder()
                            .target(new LatLng(currLocation.getLatitude(), currLocation.getLongitude()))
                            .zoom(ZOOM_LEVEL)
                            .build();
                    List<Stop> stopList = ((IlliniBusApplication) getActivity()
                            .getApplication())
                            .getStopList();

                    // Add markers to the map to show locations of stops
                    for (Stop stop : stopList) {
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(stop.getLatitude(), stop.getLongitude()))
                                .draggable(false)
                                .title(stop.getName()));
                    }
                    mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }

            /**
             * Get current location of the user based on the last best location point
             * @return current latitude and longitude of the user
             */
            private Location getCurrentLocation() {
                mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(LOCATION_SERVICE);
                List<String> providers = mLocationManager.getProviders(true);
                Location bestLocation = null;
                for (String provider : providers) {
                    Location l = mLocationManager.getLastKnownLocation(provider);
                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                        // Found best last known location: %s", l);
                        bestLocation = l;
                    }
                }
                return bestLocation;
            }
        });
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
