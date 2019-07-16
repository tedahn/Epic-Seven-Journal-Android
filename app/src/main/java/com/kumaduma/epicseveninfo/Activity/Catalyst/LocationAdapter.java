package com.kumaduma.epicseveninfo.Activity.Catalyst;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Catalyst.Locations;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> implements OnItemClickListener {

    private Context c;
    private List<Locations> locationsList;
    private OnItemClickListener mOnItemClickListener;

    public LocationAdapter(Context c, List<Locations> locations, OnItemClickListener mOnItemClickListener){
        this.c = c;
        this.locationsList = locations;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.catalyst_location_rv_item
                , viewGroup, false);
        return new LocationViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i) {
        Locations loc = locationsList.get(i);
        locationViewHolder.locationName.setText(loc.getName());
        locationViewHolder.mapLocation.setText(loc.getNode());
        locationViewHolder.mobCount.setText(loc.getMobcount());
    }

    @Override
    public int getItemCount() {
        return locationsList.size();
    }

    @Override
    public void onItemClick(int position, String type) {

    }
}
