package com.chris.illinibus;

import android.content.Context;
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

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int NUM_PAGERS = 4;
    private PagerAdapter mAdapter;
    private ViewPager mPager;
    private Context mContext;

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
    }


    /**
     * Set up the layout of action bar and action of it based on the scroll in view pager
     */
    public void setupActionBar() {
        List<String> tabNames = Arrays.asList("Dashboard",
                "Map",
                "Stops",
                "Profile");

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

        for (String tabName : tabNames) {
            actionBar.addTab(actionBar.newTab()
                    .setText(tabName)
                    .setTabListener(tabListener));
        }
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
