package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class MainCatalystListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView, bgImageView;
    TextView name;
    OnItemClickListener onItemClickListener;

    public MainCatalystListViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        imageView = itemView.findViewById(R.id.catalyst_item_image);
        bgImageView = itemView.findViewById(R.id.catalyst_item_bg);
        name = itemView.findViewById(R.id.catalyst_item_name);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "catalyst");
    }
}
