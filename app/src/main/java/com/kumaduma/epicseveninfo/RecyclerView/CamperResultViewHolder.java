package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class CamperResultViewHolder extends RecyclerView.ViewHolder {

    ImageView icon;
    TextView text, points;

    public CamperResultViewHolder(@NonNull View itemView) {
        super(itemView);

        icon = itemView.findViewById(R.id.camper_result_icon);
        text = itemView.findViewById(R.id.camper_result_text);
        points = itemView.findViewById(R.id.camper_result_points);
    }

}
