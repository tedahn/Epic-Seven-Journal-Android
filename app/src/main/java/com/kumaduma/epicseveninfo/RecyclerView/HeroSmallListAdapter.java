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

import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeroSmallListAdapter extends RecyclerView.Adapter<HeroSmallListViewHolder>{

    private Context c;
    private List<SimpleHero> heroList;
    private OnItemClickListener mOnItemClickListener;
    private String ASSET_URL;
    private int showCount;

    public HeroSmallListAdapter(Context c, List<SimpleHero> heroList, OnItemClickListener mOnItemClickListener, String url, int count){
        this.c = c;
        this.heroList = heroList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "hero/";
        if (heroList.size() <= (count*2)){
            showCount = heroList.size();
        } else {
            this.showCount = (count*2);
        }

    }
    @NonNull
    @Override
    public HeroSmallListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_hero_small
                , viewGroup, false);

        return new HeroSmallListViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroSmallListViewHolder heroViewHolder, int i) {
        SimpleHero hero = heroList.get(i);
        //BIND DATA
        heroViewHolder.rarity.setNumStars(hero.getRarity());

        heroViewHolder.element.setImageResource(c.getResources().getIdentifier("cm_icon_pro"+hero.getElement(), "drawable", c.getPackageName()));

        heroViewHolder.type.setImageResource(c.getResources().getIdentifier("cm_icon_role_"+hero.getClassType().replace("-","_"), "drawable", c.getPackageName()));


        Picasso.get().load(ASSET_URL + hero.getFileId() + "/icon.png")
                .error(R.drawable.icon_missing)
                .into(heroViewHolder.icon);

        String str = hero.getElement();
        int colorId = 0;
        if (str.equals("fire")) colorId=c.getResources().getColor(R.color.colorFire);
        else if (str.equals("earth")) colorId=c.getResources().getColor(R.color.colorEarth);
        else if (str.equals("ice")) colorId=c.getResources().getColor(R.color.colorIce);
        else if (str.equals("dark")) colorId=c.getResources().getColor(R.color.colorDark);
        else if (str.equals("light")) colorId=c.getResources().getColor(R.color.colorLight);
        else colorId=c.getResources().getColor(R.color.colorWhite);

        Drawable unwrappedDrawable = c.getResources().getDrawable(R.drawable.layout_circle_border_element);
        unwrappedDrawable.mutate().setColorFilter(colorId, PorterDuff.Mode.SRC_IN);

        if (Build.VERSION.SDK_INT >= 23)
            heroViewHolder.icon.setForeground(unwrappedDrawable);

    }

    @Override
    public int getItemCount() { return showCount; }

    public void updateList(List<SimpleHero> newList){
        heroList = new ArrayList<>();
        heroList.addAll(newList);
        notifyDataSetChanged();
    }

    public List<SimpleHero> getList(){
        return heroList;
    }

    public void expandAll(){showCount = heroList.size(); notifyDataSetChanged();}

    public boolean isExpanded(){
        return showCount >= heroList.size();
    }
}
