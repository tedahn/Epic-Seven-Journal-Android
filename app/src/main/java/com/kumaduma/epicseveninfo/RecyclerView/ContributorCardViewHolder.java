package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class ContributorCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView icon;
    TextView name, desc;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private int adapterId;

    public ContributorCardViewHolder(@NonNull View itemView, OnAdapterItemClickListener onAdapterItemClickListener, int adapterId){
        super (itemView);

        icon = itemView.findViewById(R.id.contributor_card_image);
        name = itemView.findViewById(R.id.contributor_card_name);
        desc = itemView.findViewById(R.id.contributor_card_description);
        this.adapterId = adapterId;

        this.onAdapterItemClickListener = onAdapterItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onAdapterItemClickListener.onItemClick(getAdapterPosition(), "contributor", adapterId);
    }
}
