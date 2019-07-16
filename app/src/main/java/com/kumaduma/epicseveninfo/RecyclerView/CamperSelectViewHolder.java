package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class CamperSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon;
    TextView name;
    LinearLayout layout;
    OnItemClickListener onItemClickListener;

    public CamperSelectViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        icon = itemView.findViewById(R.id.camper_select_item_image);
        name = itemView.findViewById(R.id.camper_select_item_name);
        layout = itemView.findViewById(R.id.camper_select_layout);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), "camper");
    }
}
