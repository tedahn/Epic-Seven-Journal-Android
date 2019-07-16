package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BuffIconListAdapter extends RecyclerView.Adapter<BuffIconListViewHolder> {

    private Context c;
    private List<String> buffLists;

    public BuffIconListAdapter(Context c, List<String> buffLists){
        this.c = c;
        this.buffLists = buffLists;

    }
    @NonNull
    @Override
    public BuffIconListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_skill_buff_icon
                , viewGroup, false);
        return new BuffIconListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuffIconListViewHolder buffIconListViewHolder, int i) {
        String buff = buffLists.get(i);

        ImageView img = buffIconListViewHolder.imageView;
        int resId = c.getResources().getIdentifier(buff, "drawable", c.getPackageName());
        resId = (resId == 0) ? R.drawable.ic_broken_image_black_24dp : resId;
        Picasso.get().load(resId)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .into(img);
        img.setOnClickListener((View buffStickView)->{
            int tempId = c.getResources().getIdentifier(buff,"string",c.getPackageName());
            String buffDesc;
            if  (tempId == 0) buffDesc = buff;
            else buffDesc = c.getResources().getString(tempId);
            Toast toast = Toast.makeText(c,buffDesc,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        });
    }

    @Override
    public int getItemCount() { return buffLists.size(); }
}
