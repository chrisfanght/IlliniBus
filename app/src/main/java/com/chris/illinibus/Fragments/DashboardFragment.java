package com.chris.illinibus.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.illinibus.Fragments.Adapter.StopAdapter;
import com.chris.illinibus.IlliniBusApplication;
import com.chris.illinibus.R;

/**
 * Created by Chris on 11/5/16.
 */

public class DashboardFragment extends Fragment {
    private RecyclerView mNearestStopsList;
    private RecyclerView mRequestedStopsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        mNearestStopsList = (RecyclerView) view.findViewById(R.id.nearby_list);
        initNearestStopsList();
        return view;
    }

    private void initNearestStopsList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNearestStopsList.setLayoutManager(layoutManager);
        IlliniBusApplication myApplication = (IlliniBusApplication) getActivity().getApplication();
        StopAdapter stopAdapter = new StopAdapter(myApplication.getNearbyStopList());
        mNearestStopsList.setAdapter(stopAdapter);
    }
}
