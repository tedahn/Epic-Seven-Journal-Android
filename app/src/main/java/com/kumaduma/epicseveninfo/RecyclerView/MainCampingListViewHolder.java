package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class MainCampingListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView name;
    OnItemClickListener onItemClickListener;

    public MainCampingListViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener){
        super (itemView);

        imageView = itemView.findViewById(R.id.camping_item_image);
        name = itemView.findViewById(R.id.camping_item_name);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), "camp");
    }
}
