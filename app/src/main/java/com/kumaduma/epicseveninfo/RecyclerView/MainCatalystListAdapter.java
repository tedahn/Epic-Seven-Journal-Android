package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;

import java.util.ArrayList;
import java.util.List;

public class MainCatalystListAdapter extends RecyclerView.Adapter<MainCatalystListViewHolder> {

    private Context c;
    private List<Catalyst> cataList;
    private OnItemClickListener mOnItemClickListener;
    private final String ASSET_URL;

    public MainCatalystListAdapter(Context c, List<Catalyst> cataList, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.cataList = cataList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "item/";

    }
    @NonNull
    @Override
    public MainCatalystListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_catalyst
                , viewGroup, false);
        return new MainCatalystListViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCatalystListViewHolder mainCatalystListViewHolder, int i) {
        Catalyst catalyst = cataList.get(i);
        //BIND DATA
        mainCatalystListViewHolder.name.setText(catalyst.getName());
        int itemBg = R.drawable._itembg_stuff_1;
        if (catalyst.getRarity().equals("2")){
            itemBg = R.drawable._itembg_stuff_2;
        } else if (catalyst.getRarity().equals("3")){
            itemBg = R.drawable._itembg_stuff_3;
        } else if (catalyst.getRarity().equals("4")){
            itemBg = R.drawable._itembg_stuff_4;
        } else if (catalyst.getRarity().equals("5")){
            itemBg = R.drawable._itembg_stuff_5;
        }
        Picasso.get().load(itemBg)
                .into(mainCatalystListViewHolder.bgImageView);
        Picasso.get().load(ASSET_URL + catalyst.getFileId() + ".png")
                .error(R.drawable.ic_catalyst)
                .into(mainCatalystListViewHolder.imageView);

    }

    @Override
    public int getItemCount() { return cataList.size(); }

    public void updateList(List<Catalyst> newList){
        cataList = new ArrayList<>();
        cataList.addAll(newList);
        notifyDataSetChanged();
    }

    public List<Catalyst> getList(){
        return cataList;
    }
}
