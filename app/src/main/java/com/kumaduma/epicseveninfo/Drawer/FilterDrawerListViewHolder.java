package com.kumaduma.epicseveninfo.Drawer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class FilterDrawerListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView name;
        CheckBox checkBox;
        View.OnClickListener onClickListener;

    public FilterDrawerListViewHolder(@NonNull View itemView) {
        super (itemView);

        icon = itemView.findViewById(R.id.filter_item_icon);
        name = itemView.findViewById(R.id.filter_item_title);
        checkBox = itemView.findViewById(R.id.filter_item_checkBox);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        onClickListener.onClick(view);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
