package com.chris.illinibus.Fragments.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.illinibus.IlliniBusApplication;
import com.chris.illinibus.Models.Network.RouteTime;
import com.chris.illinibus.Models.RouteRequest;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.Notification.BusReceiver;
import com.chris.illinibus.R;

import java.util.Calendar;
import java.util.List;

/**
 * Recycler View adapter for bus routes in stop details activity
 * Created by chrisfang on 11/27/16.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private List<RouteTime> mRouteList;
    private Stop mStop;
    private Context mContext;

    public RouteAdapter(List<RouteTime> routeList, Stop stop) {
        mRouteList = routeList;
        mStop = stop;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.route_card, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Bind the view for a single list item view
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        RouteTime currRoute = mRouteList.get(position);
        viewHolder.mRouteName.setText(currRoute.getHeadsign());
        viewHolder.mRouteDestination.setText(currRoute.getBusTrip().getTripHeadsign());
        String timeLeft = currRoute.getExpectedMins() + " min";
        // Change the remaining time to due when the bus is coming
        if (timeLeft.equals("0 min")) {
            timeLeft = "due";
        }
        viewHolder.mRouteTime.setText(timeLeft);
    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView mRouteName, mRouteDestination, mRouteTime;


        public ViewHolder(View v) {
            super(v);
            mRouteName = (TextView) v.findViewById(R.id.route_name);
            mRouteDestination = (TextView) v.findViewById(R.id.route_destination);
            mRouteTime = (TextView) v.findViewById(R.id.route_time);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "Long Click to Receive Notification", Toast.LENGTH_SHORT)
                    .show();
        }

        /**
         * Long click the list item will set a counter in the main activity
         * and enable push notification for this bus route
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v) {
            RouteRequest routeRequest = new RouteRequest();
            RouteTime currRoute = mRouteList.get(getPosition());
            routeRequest.setHeadSign(currRoute.getHeadsign());
            routeRequest.setExpectedDeparture(currRoute.getExpected());
            routeRequest.setDestination(currRoute.getBusTrip().getTripHeadsign());
            routeRequest.setStopLatitude(mStop.getLatitude());
            routeRequest.setStopLongitude(mStop.getLongitude());
            routeRequest.setStopName(mStop.getName());
            IlliniBusApplication myApplication = (IlliniBusApplication) ((Activity) mContext).getApplication();
            myApplication.setRouteRequest(routeRequest);

            initNotification(routeRequest);
            Toast.makeText(mContext, "Route Notification Enabled", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        /**
         * Set alarm to send push notification when bus is coming within 40 seconds
         * @param routeRequest
         */
        private void initNotification(RouteRequest routeRequest) {
            Intent notifyIntent = new Intent(mContext, BusReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,
                    0,
                    notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) mContext.
                    getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    routeRequest.getExpectedDeparture().getTime() - 40000,
                    pendingIntent);
        }
    }
}
