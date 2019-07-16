package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.Hero.Rarity;
import com.kumaduma.epicseveninfo.Model.Settings.Contributor;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContributorCardAdapter extends RecyclerView.Adapter<ContributorCardViewHolder> {

    private Context c;
    private List<Contributor> contributorList;
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private int adapterId;

    public ContributorCardAdapter(Context c, List<Contributor> contributorList, int adapterId, OnAdapterItemClickListener onAdapterItemClickListener){
        this.c = c;
        this.contributorList = contributorList;
        this.onAdapterItemClickListener = onAdapterItemClickListener;
        this.adapterId = adapterId;
    }

    @NonNull
    @Override
    public ContributorCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.menu_contributor_about
                , viewGroup, false);
        return new ContributorCardViewHolder(view, onAdapterItemClickListener, adapterId);
    }

    @Override
    public void onBindViewHolder(@NonNull ContributorCardViewHolder artifactPreviewListViewHolder, int i) {
        Contributor contributor = contributorList.get(i);
        //BIND DATA
        artifactPreviewListViewHolder.name.setText(contributor.getName());
        artifactPreviewListViewHolder.desc.setText(contributor.getDescription());

        Picasso.get().load(contributor.getImageUrl())
                .into(artifactPreviewListViewHolder.icon);

    }

    @Override
    public int getItemCount() { return contributorList.size(); }

    public List<Contributor> getList(){
        return contributorList;
    }
}
