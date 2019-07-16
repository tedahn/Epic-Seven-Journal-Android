package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.DataManager.CampManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import java.util.ArrayList;
import java.util.List;

public class MainCampingListAdapter extends RecyclerView.Adapter<MainCampingListViewHolder> {

    private Context c;
    List<SimpleHero> camperList;
    private OnItemClickListener mOnItemClickListener;
    private String ASSET_URL;

    public MainCampingListAdapter(Context c, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.camperList = new ArrayList<>();
        this.mOnItemClickListener = mOnItemClickListener;
        this. ASSET_URL = url + "hero/";
    }
    @NonNull
    @Override
    public MainCampingListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_camping
                , viewGroup, false);
        return new MainCampingListViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCampingListViewHolder mainCampingListViewHolder, int i) {
        if (camperList.size() <= i){
            mainCampingListViewHolder.name.setText("Select a Hero");
            mainCampingListViewHolder.imageView.setImageDrawable(c.getDrawable(R.drawable.ic_add_black_24dp));
        } else {
            //BIND DATA
            SimpleHero hero = camperList.get(i);
            mainCampingListViewHolder.name.setText(hero.getName());

            Picasso.get()
                    .load(ASSET_URL + hero.getFileId() + "/icon.png")
                    .error(c.getResources().getDrawable(R.drawable.icon_missing))
                    .into(mainCampingListViewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() { return 4; }

    public void updateList(){
        List<String> selectedHeroes = CampManager.getInstance().getSelectedHeroIds();
        List<SimpleHero> heroList = HeroManager.getInstance().getHeroList();
        camperList = new ArrayList<>();
        for (SimpleHero hero: heroList){
            for(String s: selectedHeroes){
                if (s.equals(hero.getFileId())) camperList.add(hero);
            }
        }
        notifyDataSetChanged();
    }


}
