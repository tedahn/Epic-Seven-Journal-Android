package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class MainHeroListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon, type, element, zodiacIcon;
    RatingBar rarity;
    TextView name, pveAverage, pvpAverage;
    private OnItemClickListener onItemClickListener;

    public MainHeroListViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        icon = itemView.findViewById(R.id.hero_item_image);
        name = itemView.findViewById(R.id.hero_item_name);
        rarity = itemView.findViewById(R.id.hero_item_rarity);
        type = itemView.findViewById(R.id.hero_item_type);
        element = itemView.findViewById(R.id.hero_item_element);
        zodiacIcon = itemView.findViewById(R.id.hero_item_zodiac_icon);
        pveAverage = itemView.findViewById(R.id.hero_item_pve_tier_average);
        pvpAverage = itemView.findViewById(R.id.hero_item_pvp_tier_average);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "hero");
    }
}
