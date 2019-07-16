package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class ArtifactPreviewListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon, smallImg, smallFrame, typeExclusive;
    RatingBar rarity;
    TextView name;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private int adapterId;

    public ArtifactPreviewListViewHolder(@NonNull View itemView, OnAdapterItemClickListener onAdapterItemClickListener, int adapterId){
        super (itemView);

        smallImg = itemView.findViewById(R.id.rv_item_artifact_preview_small);
        icon = itemView.findViewById(R.id.rv_item_artifact_preview_icon);
        name = itemView.findViewById(R.id.rv_item_artifact_preview_name);
        rarity = itemView.findViewById(R.id.rv_item_artifact_preview_rarity);
        typeExclusive = itemView.findViewById(R.id.rv_item_artifact_preview_class);
        smallFrame = itemView.findViewById(R.id.rv_item_artifact_preview_small_frame);
        this.adapterId = adapterId;

        this.onAdapterItemClickListener = onAdapterItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onAdapterItemClickListener.onItemClick(getAdapterPosition(), "artifact", adapterId);
    }
}
