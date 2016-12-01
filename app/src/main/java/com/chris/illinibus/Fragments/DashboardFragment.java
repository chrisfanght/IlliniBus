package com.chris.illinibus.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.illinibus.Fragments.Adapter.StopAdapter;
import com.chris.illinibus.IlliniBusApplication;
import com.chris.illinibus.Models.RouteRequest;
import com.chris.illinibus.R;

import java.util.Calendar;

/**
 * Main fragment containing nearby stops and requested stop count down
 * Created by Chris on 11/5/16.
 */

public class DashboardFragment extends Fragment {
    private RecyclerView mNearestStopsList;
    private CardView mRequestedStop;
    private CardView mNoRouteCard;
    private CountDownTimer mCountDownTimer;
    private Animation mFadeInAnimation;
    private Animation mFadeOutAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mNearestStopsList = (RecyclerView) view.findViewById(R.id.nearby_list);
        mRequestedStop = (CardView) view.findViewById(R.id.requested_route_card_view);
        mNoRouteCard = (CardView) view.findViewById(R.id.no_bus_card_view);
        mFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        mFadeOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);

        initNearestStopsList();
        initRequestRoute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRequestRoute();
    }

    /**
     * List 10 nearest stops based on user's current location
     */
    private void initNearestStopsList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNearestStopsList.setLayoutManager(layoutManager);
        mNearestStopsList.setNestedScrollingEnabled(false);
        IlliniBusApplication myApplication = (IlliniBusApplication) getActivity().getApplication();
        StopAdapter stopAdapter = new StopAdapter(myApplication.getNearbyStopList());
        mNearestStopsList.setAdapter(stopAdapter);
    }

    /**
     * When there's a requested route in the application, initialize the layout for it
     */
    private void initRequestRoute() {
        IlliniBusApplication myApplication = (IlliniBusApplication) getActivity().getApplication();
        if (myApplication.existsRouteRequest()) {
            RouteRequest routeRequest = myApplication.getRouteRequest();
            ((TextView) mRequestedStop.findViewById(R.id.route_name))
                    .setText(routeRequest.getHeadSign());
            ((TextView) mRequestedStop.findViewById(R.id.route_destination))
                    .setText(routeRequest.getDestination());
            ((TextView) mRequestedStop.findViewById(R.id.requested_stop_name))
                    .setText(routeRequest.getStopName());

            initOnClickListener(routeRequest);
            initCounter(routeRequest);
            mRequestedStop.setVisibility(View.VISIBLE);
            mNoRouteCard.setVisibility(View.GONE);
        } else {
            mRequestedStop.setVisibility(View.GONE);
            mNoRouteCard.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Initialize a counter for the scheduled departure time of a requested stop
     * @param routeRequest
     */
    private void initCounter(RouteRequest routeRequest) {
        final long timeRemaining = routeRequest.getExpectedDeparture().getTime()
                - Calendar.getInstance().getTime().getTime();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        // start counting down to the scheduled departure time
        mCountDownTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = (millisUntilFinished / 1000) % 60;
                long minsLeft = (millisUntilFinished / 1000) / 60;
                String timeFormat = minsLeft + " : " + secondsLeft;
                ((TextView) mRequestedStop.findViewById(R.id.route_time))
                        .setText(timeFormat);
            }

            @Override
            public void onFinish() {
                ((TextView) mRequestedStop.findViewById(R.id.route_time))
                        .setText("due");

                // Hide request card 5 second after the bus is due
                Handler handler = new Handler(Looper.getMainLooper());
                final Runnable r = new Runnable() {
                    public void run() {
                        mRequestedStop.setAnimation(mFadeOutAnimation);
                        mRequestedStop.setVisibility(View.GONE);
                        mNoRouteCard.setVisibility(View.VISIBLE);
                        mNoRouteCard.setAnimation(mFadeInAnimation);
                    }
                };
                handler.postDelayed(r, 5000);
                ((IlliniBusApplication) getActivity().getApplication()).cancelRouteRequest();
            }
        }.start();
    }

    /**
     * Start google map service to get direction to the requested stop
     * @param routeRequest
     */
    private void initOnClickListener(final RouteRequest routeRequest) {
        mRequestedStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Long Click to Get Direction", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mRequestedStop.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Uri mapDirectionUri = Uri.parse("google.navigation:q=" +
                        routeRequest.getStopLatitude() +
                        "," +
                        routeRequest.getStopLongitude() +
                        "&mode=b");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapDirectionUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                return true;
            }
        });
    }
}
