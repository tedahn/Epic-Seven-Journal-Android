package com.kumaduma.epicseveninfo.Activity.Hero;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Hero.Skills.Enhancement;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Resources;
import com.kumaduma.epicseveninfo.R;

import java.util.List;

public class SkillEnhancementAdapter extends RecyclerView.Adapter<SkillEnhancementViewHolder> {
    private Context c;
    private List<Enhancement> enhancements;

    public SkillEnhancementAdapter(Context c, List<Enhancement> enhancements){
        this.c = c;
        this.enhancements = enhancements;
    }

    @NonNull
    @Override
    public SkillEnhancementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.hero_skill_enhance_item
                , viewGroup, false);
        return new SkillEnhancementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillEnhancementViewHolder skillEnhancementViewHolder, int i) {
        Enhancement enhance = enhancements.get(i);
        String str = "+"+Integer.toString(i+1);
        skillEnhancementViewHolder.title.setText(str);
        skillEnhancementViewHolder.effect.setText(enhance.getDescription());
        List<Resources> resList = enhance.getResources();
        str = new Resources().toString(resList);
        skillEnhancementViewHolder.resources.setText(str);

    }

    @Override
    public int getItemCount() {
        return enhancements.size();
    }

    public List<Enhancement> getList(){
        return enhancements;
    }
}
