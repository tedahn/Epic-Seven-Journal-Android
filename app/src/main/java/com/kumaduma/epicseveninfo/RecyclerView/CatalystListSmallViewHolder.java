package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class CatalystListSmallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView, bgImageView;
    TextView text;
    private OnItemClickListener onItemClickListener;
    private String stringId;

    public CatalystListSmallViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener, String stringId){
        super (itemView);

        this.stringId = stringId;
        imageView = itemView.findViewById(R.id.catalyst_item_small_image);
        bgImageView = itemView.findViewById(R.id.catalyst_item_small_bg);
        text = itemView.findViewById(R.id.catalyst_item_small_text);
        this.onItemClickListener = onItemClickListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onItemClickListener.onItemClick(getAdapterPosition(), stringId);
    }
}
