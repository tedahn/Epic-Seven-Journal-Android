package com.kumaduma.epicseveninfo.Activity.Hero;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

public class HeroRelationsSmallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView relationTypeImage, heroImage;
    private OnItemClickListener onItemClickListener;

    public HeroRelationsSmallViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);
        relationTypeImage = itemView.findViewById(R.id.hero_relations_small_type_image);
        heroImage = itemView.findViewById(R.id.hero_relations_small_hero_image);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "hero");
    }
}
