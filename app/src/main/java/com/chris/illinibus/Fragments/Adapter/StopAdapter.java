package com.chris.illinibus.Fragments.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.illinibus.Models.Stop;
import com.chris.illinibus.R;
import com.chris.illinibus.StopDetailActivity;

import java.text.DecimalFormat;
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
                .inflate(R.layout.stop_card, viewGroup, false);
        setOnClickListener(view, position);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mStopName.setText(mStopList.get(position).getName());
        DecimalFormat df = new DecimalFormat("#0.00");
        viewHolder.mStopDistance.setText(df.format(mStopList.get(position).getDistance()) + " mile");
        viewHolder.mStopImage.setImageResource(R.drawable.bus);
    }

    private void setOnClickListener(View childView, final int position) {
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StopDetailActivity.class);
                Stop currStop = mStopList.get(position);
                intent.putExtra("ID", currStop.getId());
                intent.putExtra("NAME", currStop.getName());
                intent.putExtra("LONGITUDE", currStop.getLongitude());
                intent.putExtra("LATITUDE", currStop.getLatitude());
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mStopName, mStopDistance;
        public final ImageView mStopImage;

        public ViewHolder(View v) {
            super(v);
            mStopName = (TextView) v.findViewById(R.id.stop_name);
            mStopDistance = (TextView) v.findViewById(R.id.stop_distance);
            mStopImage = (ImageView) v.findViewById(R.id.stop_image);
        }
    }
}
