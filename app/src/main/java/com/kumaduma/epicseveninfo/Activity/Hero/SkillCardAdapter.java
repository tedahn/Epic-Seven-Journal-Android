package com.kumaduma.epicseveninfo.Activity.Hero;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.RecyclerView.BuffIconListAdapter;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Skills;
import com.kumaduma.epicseveninfo.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SkillCardAdapter extends PagerAdapter {

    private List<Skills> skills;
    LayoutInflater layoutInflater;
    private Context c;
    private String heroName;
    RecyclerView enhancementList;
    RecyclerView buffRecyclerView;
    BuffIconListAdapter buffIconListAdapter;
    List<String> buffList;

    public SkillCardAdapter(Context c, List<Skills> skills, String heroName, RecyclerView enhancementList) {
        this.c = c;
        this.skills = skills;
        this.heroName = heroName;
        this.enhancementList = enhancementList;

    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(c);
        View view = layoutInflater.inflate(R.layout.hero_skill_card, container, false);

        Skills skill = skills.get(position);

        RecyclerView buffRecyclerView;
        ImageView skillImage, cooldownImage, skillImageBorder;
        TextView skillName, skillDesc, cooldown, soulAcquire, sbConsume, sbDesc;
        LinearLayout sbLayout;


        skillImage = view.findViewById(R.id.skill_card_image);
        skillImageBorder = view.findViewById(R.id.skill_card_image_border);
        skillName = view.findViewById(R.id.skill_name);
        cooldown = view.findViewById(R.id.skill_cooldown);
        cooldownImage = view.findViewById(R.id.skill_cooldown_image);
        skillDesc = view.findViewById(R.id.skill_description);
        soulAcquire = view.findViewById(R.id.skill_soul_acquire);
        sbLayout = view.findViewById(R.id.skill_soulburn_layout);
        sbConsume= view.findViewById(R.id.skill_soulburn_consume);
        sbDesc= view.findViewById(R.id.skill_soulburn_description);

        //set skill image
        String skillInt = Integer.toString(position + 1);
        if(skill.isPassive()){
            skillInt += "p";
        }
        Picasso.get()
                .load("https://assets.epicsevendb.com/hero/"+heroName+"/sk_"+skillInt+".png")
                .error(R.drawable.sk_missing)
                .into(skillImage);
        if(skill.isAwakenUpgrade()){
            skillImageBorder.setImageResource(R.drawable.upgrade_active);
        }

        skillName.setText(skill.getName());
        skillDesc.setText(skill.getDescription());

        if(skill.isPassive()){
            String str = "Passive";
            soulAcquire.setText(str);
        } else {
            String str = "Acquire " + skill.getSoulAcquire() + " Soul";
            soulAcquire.setText(str);
        }

        int sb = skill.getSoulBurn();
        if (sb <= 0){
            sbLayout.setVisibility(View.GONE);
        } else {
            String str = "Consumes "+skill.getSoulBurn() + " Soul";
            sbConsume.setText(str);
            sbDesc.setText(skill.getSoulBurnEffect());
        }

        int cd = skill.getCooldown();
        if (cd <= 0){
            cooldown.setText("");
            cooldownImage.setImageDrawable(null);
        } else {
            String str = cd + " turns";
            cooldown.setText(str);
        }

        buffList = new ArrayList<>();
        buffList.addAll(skill.getBuffs());
        buffList.addAll(skill.getDebuffs());
        buffIconListAdapter = new BuffIconListAdapter(c, buffList);
        buffRecyclerView = view.findViewById(R.id.skill_buff_icon_rv);
        buffRecyclerView.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL,false));

        buffRecyclerView.setAdapter(buffIconListAdapter);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
