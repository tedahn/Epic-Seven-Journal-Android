package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class HeroSmallListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon, type, element;
    RatingBar rarity;
    private OnItemClickListener onItemClickListener;

    public HeroSmallListViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        icon = itemView.findViewById(R.id.small_hero_item_image);
        rarity = itemView.findViewById(R.id.small_hero_item_rarity);
        type = itemView.findViewById(R.id.small_hero_item_type);
        element = itemView.findViewById(R.id.small_hero_item_element);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "hero");
    }
}
