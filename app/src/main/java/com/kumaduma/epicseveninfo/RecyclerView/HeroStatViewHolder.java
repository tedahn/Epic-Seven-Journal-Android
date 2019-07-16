package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

import org.w3c.dom.Text;

public class HeroStatViewHolder extends RecyclerView.ViewHolder {

    ImageView icon;
    TextView desc, point;

    public HeroStatViewHolder(@NonNull View itemView ){
        super (itemView);

        icon = itemView.findViewById(R.id.rv_item_stats_icon);
        desc = itemView.findViewById(R.id.rv_item_stats_desc);
        point = itemView.findViewById(R.id.rv_item_stats_pt);
    }
}
