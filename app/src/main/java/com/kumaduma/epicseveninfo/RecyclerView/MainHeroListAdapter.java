package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.DataManager.TierManager;
import com.kumaduma.epicseveninfo.Model.SimpleHeroTier;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Model.Hero.Rarity;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import java.util.ArrayList;
import java.util.List;

public class MainHeroListAdapter extends RecyclerView.Adapter<MainHeroListViewHolder>{

    private Context c;
    private List<SimpleHeroTier> heroList;
    private OnItemClickListener mOnItemClickListener;
    private String ASSET_URL;
    private boolean isHiddenTierList;

    public MainHeroListAdapter(Context c, List<SimpleHeroTier> heroList, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.heroList = heroList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "hero/";
        isHiddenTierList = PreferenceManager.getDefaultSharedPreferences(c).getBoolean("dtl_tier_list",false);

    }
    @NonNull
    @Override
    public MainHeroListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_hero
                , viewGroup, false);

        return new MainHeroListViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHeroListViewHolder heroViewHolder, int i) {
        SimpleHeroTier hero = heroList.get(i);
        //BIND DATA
        heroViewHolder.name.setText(hero.getName());
        heroViewHolder.rarity.setNumStars(hero.getRarity());

        heroViewHolder.element.setImageResource(c.getResources().getIdentifier("cm_icon_pro"+hero.getElement(), "drawable", c.getPackageName()));

        heroViewHolder.type.setImageResource(c.getResources().getIdentifier("cm_icon_role_"+hero.getClassType().replace("-","_"), "drawable", c.getPackageName()));

        //Too dizzy for zodiac texts
        //heroViewHolder.zodiacLabel.setText(hero.getZodiac());

        heroViewHolder.zodiacIcon.setImageResource(c.getResources().getIdentifier("zod_icon_"+hero.getZodiac(), "drawable", c.getPackageName()));

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

        if (!isHiddenTierList) {
            heroViewHolder.pveAverage.setText(hero.getPveAvg());
            heroViewHolder.pvpAverage.setText(hero.getPvpAvg());
        }
    }

    @Override
    public int getItemCount() { return heroList.size(); }

    public void updateList(List<SimpleHeroTier> newList){
        heroList = new ArrayList<>();
        heroList.addAll(newList);
        notifyDataSetChanged();
    }

    public List<SimpleHeroTier> getList(){
        return heroList;
    }

}
