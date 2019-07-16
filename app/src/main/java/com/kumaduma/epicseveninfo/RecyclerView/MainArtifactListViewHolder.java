package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class MainArtifactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon, typeExclusive;
    TextView name, pveTier, pvpTier;
    RatingBar rarity;
    private OnItemClickListener onItemClickListener;

    public MainArtifactListViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        icon = itemView.findViewById(R.id.artifact_item_image);
        name = itemView.findViewById(R.id.artifact_item_name);
        rarity = itemView.findViewById(R.id.artifact_item_rarity);
        typeExclusive = itemView.findViewById(R.id.artifact_item_type);
        //imageLarge = itemView.findViewById(R.id.artifact_item_image_large);
        pveTier = itemView.findViewById(R.id.artifact_item_pve_tier_average);
        pvpTier = itemView.findViewById(R.id.artifact_item_pvp_tier_average);

        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "artifact");
    }
}
