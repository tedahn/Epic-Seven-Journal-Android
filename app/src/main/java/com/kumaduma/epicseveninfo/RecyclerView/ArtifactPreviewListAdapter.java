package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.Hero.Rarity;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArtifactPreviewListAdapter extends RecyclerView.Adapter<ArtifactPreviewListViewHolder> {

    private Context c;
    private List<Artifact> artifactList;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private final String ASSET_URL;
    private Rarity star = new Rarity();
    private int adapterId;

    public ArtifactPreviewListAdapter(Context c, List<Artifact> artifactList, int adapterId, OnAdapterItemClickListener onAdapterItemClickListener, String url){
        this.c = c;
        this.artifactList = artifactList;
        this.onAdapterItemClickListener = onAdapterItemClickListener;
        this.ASSET_URL = url + "artifact/";
        this.adapterId = adapterId;
    }

    @NonNull
    @Override
    public ArtifactPreviewListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.rv_item_artifact_preview
                , viewGroup, false);
        return new ArtifactPreviewListViewHolder(view, onAdapterItemClickListener, adapterId);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtifactPreviewListViewHolder artifactPreviewListViewHolder, int i) {
        Artifact artifact = artifactList.get(i);
        //BIND DATA
        artifactPreviewListViewHolder.name.setText(artifact.getName());
        artifactPreviewListViewHolder.rarity.setNumStars(artifact.getRarity());

        if (artifact.getExclusive().isEmpty()){
            //Non-Exclusive Type
            artifactPreviewListViewHolder.typeExclusive.setImageDrawable(null);
        } else {
            artifactPreviewListViewHolder.typeExclusive.setImageResource(c.getResources()
                    .getIdentifier("cm_icon_role_"+artifact.getExclusive().get(0)
                    .replace("-","_"),
                            "drawable",
                            c.getPackageName()
                    )
            );
        }

        Picasso.get().load(ASSET_URL + artifact.getFileId() + "/icon.png")
                .into(artifactPreviewListViewHolder.icon);
        Picasso.get().load(ASSET_URL + artifact.getFileId() + "/small.jpg")
                .into(artifactPreviewListViewHolder.smallImg);
        Picasso.get().load(R.drawable.arti_frame_small)
                .into(artifactPreviewListViewHolder.smallFrame);

    }

    @Override
    public int getItemCount() { return artifactList.size(); }

    public void updateList(List<Artifact> newList){
        artifactList = new ArrayList<>();
        artifactList.addAll(newList);
        notifyDataSetChanged();
    }

    public List<Artifact> getList(){
        return artifactList;
    }
}
