package com.kumaduma.epicseveninfo.Activity.Catalyst;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

public class ApShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView chapter, cost, quantity;
    OnItemClickListener onItemClickListener;

    public ApShopViewHolder(@NonNull View itemView, OnItemClickListener mOnItemClickListener) {
        super(itemView);
        chapter = itemView.findViewById(R.id.catalyst_apshop_chapter);
        cost = itemView.findViewById(R.id.catalyst_apshop_cost);
        quantity = itemView.findViewById(R.id.catalyst_apshop_quantity);
        this.onItemClickListener = mOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(getAdapterPosition(), "apshop");
    }
}
