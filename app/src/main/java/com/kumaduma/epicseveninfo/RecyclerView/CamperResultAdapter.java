package com.kumaduma.epicseveninfo.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Model.CamperReactions;
import com.kumaduma.epicseveninfo.R;

import java.util.List;

public class CamperResultAdapter extends RecyclerView.Adapter<CamperResultViewHolder> {
    Context c;
    List<CamperReactions> resultsList;
    String ASSET_URL;

    public CamperResultAdapter(Context c,List<CamperReactions> resultsList, String url){
        this.c = c;
        this.resultsList = resultsList;
        this.ASSET_URL = url + "hero/";
    }
    @NonNull
    @Override
    public CamperResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.camper_result_item
                , viewGroup, false);
        return new CamperResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CamperResultViewHolder viewHolder, int i) {
        CamperReactions result = resultsList.get(i);
        Picasso.get().load(ASSET_URL + result.getHeroFileId() + "/icon.png").into(viewHolder.icon);
        viewHolder.text.setText(result.optionToString());
        String str = Integer.toString(result.getPoints());
        viewHolder.points.setText(str);
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
}
