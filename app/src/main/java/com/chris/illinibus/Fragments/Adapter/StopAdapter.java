package com.chris.illinibus.Fragments.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.R;

import java.util.List;

/**
 * Created by Chris on 11/14/16.
 */

public class StopAdapter extends RecyclerView.Adapter<StopAdapter.ViewHolder> {
    private List<Stop> mStopList;
    private Context mContext;

    public StopAdapter(List<Stop> stopList) {
        mStopList = stopList;
    }

    @Override
    public int getItemCount() {
        return mStopList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.stop_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setStopName(mStopList.get(position).getName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mStopName;

        public ViewHolder(View v) {
            super(v);
            mStopName = (TextView) v.findViewById(R.id.stop_name);
        }

        public void setStopName(String stopNmae) {
            mStopName.setText(stopNmae);
        }
    }
}
