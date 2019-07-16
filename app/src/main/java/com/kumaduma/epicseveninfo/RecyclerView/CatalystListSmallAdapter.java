package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalystListSmallAdapter extends RecyclerView.Adapter<CatalystListSmallViewHolder> {

    private Context c;
    private List<Catalyst> cataList;
    private Map<String,Integer> cataCount;
    private OnItemClickListener mOnItemClickListener;
    private final String ASSET_URL;
    private String id;

    public CatalystListSmallAdapter(Context c, List<Catalyst> cataList, Map<String,Integer> cataCount, String id, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.cataList = cataList;
        this.cataCount = cataCount;
        this.id = id;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "item/";

    }
    @NonNull
    @Override
    public CatalystListSmallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_catalyst_small_with_counter
                , viewGroup, false);
        return new CatalystListSmallViewHolder(view, mOnItemClickListener, id);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalystListSmallViewHolder catalystListSmallViewHolder, int i) {
        Catalyst catalyst = cataList.get(i);
        //BIND DATA
        String str = "" + cataCount.get(catalyst.getFileId());
        catalystListSmallViewHolder.text.setText(str);
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
                .into(catalystListSmallViewHolder.bgImageView);
        Picasso.get().load(ASSET_URL + catalyst.getFileId() + ".png")
                .error(R.drawable.ic_catalyst)
                .into(catalystListSmallViewHolder.imageView);

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
