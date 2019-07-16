package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class BuffIconListViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;

    public BuffIconListViewHolder(@NonNull View itemView){
        super (itemView);

        imageView = itemView.findViewById(R.id.buff_icon);
    }
}
