package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.DataManager.CampManager;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CamperSelectAdapter extends RecyclerView.Adapter<CamperSelectViewHolder>  {
    private List<SimpleHero> heroList;
    private Context c;
    private OnItemClickListener mOnItemClickListener;
    private final String ASSET_URL;
    private Set<Integer> selectedPos;

    public CamperSelectAdapter(Context c, List<SimpleHero> heroList, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.heroList = heroList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "hero/";
        setSelected();
    }

    @NonNull
    @Override
    public CamperSelectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.camper_select_item
                , viewGroup, false);
        return new CamperSelectViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CamperSelectViewHolder viewHolder, int i) {
        viewHolder.itemView.setSelected(selectedPos.contains(i));
        SimpleHero hero = heroList.get(i);

        Picasso.get()
                .load(ASSET_URL + hero.getFileId() + "/icon.png")
                .error(c.getResources().getDrawable(R.drawable.icon_missing))
                .into(viewHolder.icon);

        viewHolder.name.setText(hero.getName());
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public SimpleHero getHeroAt(int position){
        return heroList.get(position);
    }

    public void setSelected(int i){
        //selectedHeroes = CampManager.getInstance().getSelectedHeroIds();
        selectedPos.add(i);
        notifyDataSetChanged();
    }

    public void removeSelected(int i){
        selectedPos.remove(i);
        notifyDataSetChanged();
    }

    public void refreshSelected(){
        setSelected();
        notifyDataSetChanged();
    }

    private void setSelected(){
        selectedPos = new HashSet<>();
        List<String> selected = CampManager.getInstance().getSelectedHeroIds();
        for (int i = 0; i < heroList.size(); i++){
            for (String s: selected){
                if (heroList.get(i).getFileId().equals(s)) selectedPos.add(i);
            }
        }
    }

    public void updateList(List<SimpleHero> list){
        heroList = list;
        refreshSelected();
    }
}
