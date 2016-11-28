package com.chris.illinibus;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.chris.illinibus.Fragments.DashboardFragment;
import com.chris.illinibus.Fragments.MapFragment;
import com.chris.illinibus.Fragments.ProfileFragment;
import com.chris.illinibus.Fragments.StopsFragment;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.Services.StopDBService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int NUM_PAGERS = 4;
    static final int METER_TO_MILE = 1609;

    private PagerAdapter mAdapter;
    private ViewPager mPager;
    private Context mContext;
    private StopDBService mDBService;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });

        setupActionBar();
        setupStopData();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDBService.close();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDBService.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDBService.open();
    }

    /**
     * Set up the layout of action bar and action of it based on the scroll in view pager
     */
    private void setupActionBar() {
        List<Integer> tabIcons = Arrays.asList(R.drawable.ic_action_home,
                R.drawable.ic_action_map,
                R.drawable.ic_action_bus,
                R.drawable.ic_action_name);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        for (Integer tabIcon : tabIcons) {
            actionBar.addTab(actionBar.newTab()
                    .setIcon(tabIcon)
                    .setTabListener(tabListener));
        }
    }

    /**
     * Set up stop information for the applications and read from database
     */
    private void setupStopData() {
        mDBService = new StopDBService(mContext);
        mDBService.open();
        List<Stop> stopList = mDBService.getStops();
        Location currLocation = getCurrentLocation();
        for (Stop stop : stopList) {
            Location location = new Location("");
            location.setLatitude(stop.getLatitude());
            location.setLongitude(stop.getLongitude());
            double distance = currLocation.distanceTo(location);
            stop.setDistance(distance/METER_TO_MILE);
        }
        ((IlliniBusApplication) getApplication()).setStopList(stopList);
    }

    private Location getCurrentLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
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

    @Override
    public void onBackPressed() {
        // Do nothing when click on back button on the main page
    }

    /**
     * View Pager adapter for the viewPager in the main page
     */
    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_PAGERS;
        }

        /**
         * Render different fragment based on the position of current view pager
         *
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                default:
                    fragment = Fragment.instantiate(mContext, DashboardFragment.class.getName());
                    break;
                case 0:
                    fragment = Fragment.instantiate(mContext, DashboardFragment.class.getName());
                    break;
                case 1:
                    fragment = Fragment.instantiate(mContext, MapFragment.class.getName());
                    break;
                case 2:
                    fragment = Fragment.instantiate(mContext, StopsFragment.class.getName());
                    break;
                case 3:
                    fragment = Fragment.instantiate(mContext, ProfileFragment.class.getName());
                    break;
            }
            return fragment;
        }
    }
}
