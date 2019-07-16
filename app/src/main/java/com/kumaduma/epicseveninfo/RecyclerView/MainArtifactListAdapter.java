package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.DataManager.TierManager;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Model.Hero.Rarity;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;

import java.util.ArrayList;
import java.util.List;

public class MainArtifactListAdapter extends RecyclerView.Adapter<MainArtifactListViewHolder> {

    private Context c;
    private List<SimpleArtifactTier> artifactList;
    private OnItemClickListener mOnItemClickListener;
    private final String ASSET_URL;
    private boolean isHiddenTierList;

    public MainArtifactListAdapter(Context c, List<SimpleArtifactTier> artifactList, OnItemClickListener mOnItemClickListener, String url){
        this.c = c;
        this.artifactList = artifactList;
        this.mOnItemClickListener = mOnItemClickListener;
        this.ASSET_URL = url + "artifact/";
        isHiddenTierList = PreferenceManager.getDefaultSharedPreferences(c).getBoolean("dtl_tier_list",false);

    }

    @NonNull
    @Override
    public MainArtifactListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_artifact
                , viewGroup, false);
        return new MainArtifactListViewHolder(view,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainArtifactListViewHolder mainArtifactListViewHolder, int i) {
        SimpleArtifactTier artifact = artifactList.get(i);
        //BIND DATA
        mainArtifactListViewHolder.name.setText(artifact.getName());
        mainArtifactListViewHolder.rarity.setNumStars(artifact.getRarity());

        if (artifact.getExclusive().isEmpty()){
            //Non-Exclusive Type
            mainArtifactListViewHolder.typeExclusive.setImageDrawable(null);
        } else {
            mainArtifactListViewHolder.typeExclusive.setImageResource(c.getResources()
                    .getIdentifier("cm_icon_role_"+artifact.getExclusive().get(0)
                    .replace("-","_"),
                            "drawable",
                            c.getPackageName()
                    )
            );
        }

        Picasso.get().load(ASSET_URL + artifact.getFileId() + "/icon.png")
                .into(mainArtifactListViewHolder.icon);
//        Picasso.get().load(ASSET_URL + artifact.getFileId() + "/small.jpg")
//                .into(mainArtifactListViewHolder.imageLarge);

        if(!isHiddenTierList) {
            mainArtifactListViewHolder.pvpTier.setText(artifact.getPvpAvg());
            mainArtifactListViewHolder.pveTier.setText(artifact.getPveAvg());
        }

    }

    @Override
    public int getItemCount() { return artifactList.size(); }

    public void updateList(List<SimpleArtifactTier> newList){
        artifactList = new ArrayList<>();
        artifactList.addAll(newList);
        notifyDataSetChanged();
    }

    public List<SimpleArtifactTier> getList(){
        return artifactList;
    }
}
