package com.kumaduma.epicseveninfo.Activity.Hero;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kumaduma.epicseveninfo.R;

public class SkillEnhancementViewHolder extends RecyclerView.ViewHolder {

    TextView title, effect, resources;

    public SkillEnhancementViewHolder(@NonNull View itemView){
        super (itemView);
        title = itemView.findViewById(R.id.skill_enhance_title);
        effect = itemView.findViewById(R.id.skill_enhance_effect);
        resources = itemView.findViewById(R.id.skill_enhance_resources);
    }
}
