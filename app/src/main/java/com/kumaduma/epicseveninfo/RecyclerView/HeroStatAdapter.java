package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Hero.Stats;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeroStatAdapter extends RecyclerView.Adapter<HeroStatViewHolder>{

    private Context c;
    private List<String> statList;
    private List<String> statNameList;
    private String ASSET_URL;
    private Stats stats;

    public HeroStatAdapter(Context c, List<String> statList, List<String> statNameList, String url){
        this.c = c;
        this.statList = statList;
        this.statNameList = statNameList;
        this.ASSET_URL = url;
        this.stats = new Stats();

    }

    @NonNull
    @Override
    public HeroStatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_hero_stats
                , viewGroup, false);

        return new HeroStatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroStatViewHolder heroViewHolder, int i) {
        String stat = statList.get(i);
        String statName = statNameList.get(i);

        Picasso.get().load(ASSET_URL + "stat/cm_icon_stat_" + statName + ".png").into(heroViewHolder.icon);
        heroViewHolder.desc.setText(stats.getStatName(statName));
        heroViewHolder.point.setText(stat);

    }

    @Override
    public int getItemCount() { return statList.size(); }

    public void updateList(List<String> newList){
        statList = new ArrayList<>();
        statList.addAll(newList);
        notifyDataSetChanged();
    }
}
