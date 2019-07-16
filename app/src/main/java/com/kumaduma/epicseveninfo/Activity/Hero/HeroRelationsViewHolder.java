package com.kumaduma.epicseveninfo.Activity.Hero;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

public class HeroRelationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView relationType, heroName;
    ImageView relationTypeImage, heroImage;
    OnItemClickListener onItemClickListener;

    public HeroRelationsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);
        relationType = itemView.findViewById(R.id.hero_relations_type_label);
        heroName = itemView.findViewById(R.id.hero_relations_name);
        relationTypeImage = itemView.findViewById(R.id.hero_relations_type_image);
        heroImage = itemView.findViewById(R.id.hero_relations_hero_image);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "hero");
    }
}
