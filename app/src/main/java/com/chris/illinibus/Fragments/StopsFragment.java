package com.chris.illinibus.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chris.illinibus.Fragments.Adapter.StopAdapter;
import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.R;
import com.chris.illinibus.Services.StopDBService;

import java.util.List;

/**
 * Created by Chris on 11/5/16.
 */

public class StopsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private StopDBService mDBService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_stops, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.stops_recycler_view);
        initDBService();
        initRecyclerView();
        return view;
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

    public void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        List<Stop> stopList = mDBService.getStops();
        StopAdapter stopAdapter = new StopAdapter(stopList);
        mRecyclerView.setAdapter(stopAdapter);
    }

    public void initDBService() {
        mDBService = new StopDBService(getContext());
        mDBService.open();
    }
}