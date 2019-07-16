package com.kumaduma.epicseveninfo.Activity.Hero;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Hero.Relations;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HeroRelationsSmallAdapter extends RecyclerView.Adapter<HeroRelationsSmallViewHolder> {
    private Context c;
    private List<Relations> relations;
    private OnItemClickListener mOnItemClickListener;
    private final String ASSET_URL;

    public HeroRelationsSmallAdapter(Context c, List<Relations> relations, String url, OnItemClickListener mOnItemClickListener){
        this.c = c;
        this.relations = relations;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url;
    }

    @NonNull
    @Override
    public HeroRelationsSmallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.hero_relations_item_small
                , viewGroup, false);
        return new HeroRelationsSmallViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroRelationsSmallViewHolder heroRelationsViewHolder, int i) {
        Relations relation = relations.get(i);

        Picasso.get().load(ASSET_URL + "hero/" + relation.getFileId() + "/icon.png")
                .error(R.drawable.icon_missing)
                .into(heroRelationsViewHolder.heroImage);

        Picasso.get().load(ASSET_URL + "relationship/cm_icon_storymap_" + relation.getRelationType() + ".png")
                .into(heroRelationsViewHolder.relationTypeImage);

        String str = relation.getRelationType();
        int colorId = 0;
        if (str.equals("grudge")) colorId=c.getResources().getColor(R.color.fontGrudge);
        else if (str.equals("love")) colorId=c.getResources().getColor(R.color.fontLove);
        else if (str.equals("trust")) colorId=c.getResources().getColor(R.color.fontTrust);
        else if (str.equals("longing")) colorId=c.getResources().getColor(R.color.fontLonging);
        else if (str.equals("rival")) colorId=c.getResources().getColor(R.color.fontRival);
        else colorId=c.getResources().getColor(R.color.colorText);

        Drawable unwrappedDrawable = c.getResources().getDrawable(R.drawable.layout_circle_border);
        unwrappedDrawable.mutate().setColorFilter(colorId, PorterDuff.Mode.SRC_IN);

        if (Build.VERSION.SDK_INT >= 23)
            heroRelationsViewHolder.heroImage.setForeground(unwrappedDrawable);


    }

    @Override
    public int getItemCount() {
        return relations.size();
    }

    public List<Relations> getList(){
        return relations;
    }
}
