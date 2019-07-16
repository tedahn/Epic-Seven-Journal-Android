package com.kumaduma.epicseveninfo.Activity.Catalyst;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mapLocation, locationName, mobCount;
    OnItemClickListener onItemClickListener;

    public LocationViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
        super(itemView);
        mapLocation = itemView.findViewById(R.id.catalyst_location);
        locationName = itemView.findViewById(R.id.catalyst_location_name);
        mobCount = itemView.findViewById(R.id.catalyst_location_mob_count);
        this.onItemClickListener = mOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), "location");
    }
}
