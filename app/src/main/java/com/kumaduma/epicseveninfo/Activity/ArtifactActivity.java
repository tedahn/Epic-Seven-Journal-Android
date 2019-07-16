package com.kumaduma.epicseveninfo.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.DataManager.TierManager;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;
import com.kumaduma.epicseveninfo.RecyclerView.HeroSmallListAdapter;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.Hero.Stats;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArtifactActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG = "ArtifactActivity";
    Artifact artifact;
    String artifactId;
    ArtifactManager dataManager = ArtifactManager.getInstance();
    private LinearLayout baseLayout, maxLayout;
    private static final String ASSET_URL = "https://assets.epicsevendb.com/";
    private TierManager tierManager = TierManager.getInstance();
    LinearLayout artifactNoteLayout, artifactTierLayout;
    boolean emptyNote = true;
    boolean isNullTier = false;
    HeroSmallListAdapter usedByHeroAdapter;
    RecyclerView rvUsedByHero;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artifact_activity);

        Toolbar t = findViewById(R.id.artifact_act_toolbar);

        setSupportActionBar(t);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        //IMAGE
        ImageView artifactImage = findViewById(R.id.artifact_act_image_full);
        ImageView artifactIcon = findViewById(R.id.artifact_act_image_icon);

        //PROFILE
        RatingBar artifactRarity = findViewById(R.id.artifact_act_rarity);
        TextView artifactName = findViewById(R.id.artifact_act_name);
        TextView loreDesc = findViewById(R.id.artifact_act_lore_description);

        //STATS SWITCH
        baseLayout = findViewById(R.id.artifact_base_stat_layout);
        maxLayout = findViewById(R.id.artifact_max_stat_layout);
        SwitchCompat statsSwitch = findViewById(R.id.artifact_stats_switch);
        TextView statsSwitchText = findViewById(R.id.artifact_stats_switch_text);

        //DISCORD ARTIFACT TIER
        TextView pvpTier = findViewById(R.id.artifact_pvp_tier);
        TextView pveTier = findViewById(R.id.artifact_pve_tier);

        //STATS
        TextView artifactBaseStatOne = findViewById(R.id.artifact_base_stat_one_name);
        TextView artifactBaseStatOnePt = findViewById(R.id.artifact_base_stat_one_pt);
        TextView artifactBaseStatTwo = findViewById(R.id.artifact_base_stat_two_name);
        TextView artifactBaseStatTwoPt = findViewById(R.id.artifact_base_stat_two_pt);
        TextView artifactMaxStatOne = findViewById(R.id.artifact_max_stat_one_name);
        TextView artifactMaxStatOnePt = findViewById(R.id.artifact_max_stat_one_pt);
        TextView artifactMaxStatTwo = findViewById(R.id.artifact_max_stat_two_name);
        TextView artifactMaxStatTwoPt = findViewById(R.id.artifact_max_stat_two_pt);

        //SKILL
        TextView artifactSkillBase = findViewById(R.id.artifact_skill_base);
        TextView artifactSkillMax = findViewById(R.id.artifact_skill_max);

        //REC HEROES
        rvUsedByHero = findViewById(R.id.artifact_used_by_hero_recycle_view);

        Bundle extras = getIntent().getExtras();
        artifactId = extras.getString("artifact_id");
        artifact = dataManager.getArtifactById(artifactId);
        if (artifact == null) {
            Toast toast = Toast.makeText(this, "Can't load artifact!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            finish();
        } else {
            //IMAGE
            Picasso.get().load(ASSET_URL + "artifact/" + artifact.getFileId() + "/full.png")
                    .into(artifactImage);
            Picasso.get().load(ASSET_URL + "artifact/" + artifact.getFileId() + "/icon.png")
                    .into(artifactIcon);

            //On Image Click
            /*
            artifactImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isImageFitToScreen) {
                        isImageFitToScreen=false;
                        artifactImage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                        artifactImage.setAdjustViewBounds(true);
                    }else{
                        isImageFitToScreen=true;
                        artifactImage.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                        artifactImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            });
            */

            //PROFILE
            artifactRarity.setNumStars(artifact.getRarity());
            artifactName.setText(artifact.getName());
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : artifact.getLoreDescription()){
                stringBuilder.append(s + "\n");
            }
            loreDesc.setText(stringBuilder.toString());
            TextView artifactNote = findViewById(R.id.artifact_notes);
            //DISCORD TIER LIST TIER
            ArtifactTier artTier = tierManager.getArtifactTierById(artifact.getFileId());
            if (artTier != null) {
                pveTier.setText(artTier.getTierEnvironment());
                pvpTier.setText(artTier.getTierPlayer());

                String note = artTier.getDescription();
                emptyNote = note.trim().equals("");
                artifactNote.setText(note);
            } else {isNullTier=true;}

            //STATS SWITCH
            statsSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)->{
                if (isChecked){
                    maxLayout.setVisibility(View.VISIBLE);
                    baseLayout.setVisibility(View.GONE);
                    statsSwitchText.setText("Max");
                    statsSwitchText.setTextColor(getResources().getColor(R.color.colorMax));
                } else {
                    maxLayout.setVisibility(View.GONE);
                    baseLayout.setVisibility(View.VISIBLE);
                    statsSwitchText.setText("Base");
                    statsSwitchText.setTextColor(getResources().getColor(R.color.colorBase));
                }
            });
            statsSwitch.setChecked(false);

            //STATS
            Stats stat = new Stats();
            Iterator<Map.Entry<String,Integer>> itrBase = artifact.getBase().entrySet().iterator();
            if (itrBase.hasNext()) {
                Map.Entry<String, Integer> baseStatOne = itrBase.next();
                String str = stat.getStatName(baseStatOne.getKey());
                artifactBaseStatOne.setText(str);
                str = "+ " + Integer.toString(baseStatOne.getValue());
                artifactBaseStatOnePt.setText(str);
            }
            if (itrBase.hasNext()) {
                Map.Entry<String, Integer> baseStatTwo = itrBase.next();
                String str = stat.getStatName(baseStatTwo.getKey());
                artifactBaseStatTwo.setText(str);
                str = "+ " + Integer.toString(baseStatTwo.getValue());
                artifactBaseStatTwoPt.setText(str);
            }
            Iterator<Map.Entry<String,Integer>> itrMax = artifact.getMax().entrySet().iterator();
            if (itrMax.hasNext()) {
                Map.Entry<String, Integer> maxStatOne = itrMax.next();
                String str = stat.getStatName(maxStatOne.getKey());
                artifactMaxStatOne.setText(str);
                str = "+ " + Integer.toString(maxStatOne.getValue());
                artifactMaxStatOnePt.setText(str);
            }
            if (itrMax.hasNext()) {
                Map.Entry<String, Integer> maxStatTwo = itrMax.next();
                String str = stat.getStatName(maxStatTwo.getKey());
                artifactMaxStatTwo.setText(str);
                str = "+ " + Integer.toString(maxStatTwo.getValue());
                artifactMaxStatTwoPt.setText(str);
            }

            //SKILL
            artifactSkillBase.setText(artifact.getSkillDescription().get("base"));
            artifactSkillMax.setText(artifact.getSkillDescription().get("max"));


            //REC HEROES

            List<SimpleHero> tempHeroList = HeroManager.getInstance().getHeroList();
            List<SimpleHero> heroList = new ArrayList<>();
            Set<String> heroSet = TierManager.getInstance().getHeroesByArtifactId(artifactId);
            if (heroSet != null) {
                List<String> suggestedHeroString = new ArrayList<>(heroSet);
                for (SimpleHero h : tempHeroList) {
                    if (!h.getFileId().contains("phantasma") && suggestedHeroString.contains(h.getFileId())) {
                        heroList.add(h);
                    }
                }
                int spanCount;
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    spanCount = 7;
                } else {
                    spanCount = 5;
                }
                GridLayoutManager grm = new GridLayoutManager(this, spanCount);
                rvUsedByHero.setLayoutManager(grm);

                usedByHeroAdapter = new HeroSmallListAdapter(this, heroList, this, ASSET_URL,spanCount);
                rvUsedByHero.setAdapter(usedByHeroAdapter);
                Button loadAllBtn = findViewById(R.id.artifact_used_by_hero_show_more);
                loadAllBtn.setOnClickListener((View v)->{
                    usedByHeroAdapter.expandAll();
                    loadAllBtn.setVisibility(View.GONE);
                });
                if (usedByHeroAdapter.isExpanded()) loadAllBtn.setVisibility(View.GONE);

            }
            if (heroList.isEmpty())
                findViewById(R.id.artifact_used_by_hero_layout).setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        boolean isHiddenTier = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_tier_activity",false);
        boolean isHiddenNote = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_note_all",false);

        artifactTierLayout = findViewById(R.id.artifact_tier_layout);
        if (isHiddenTier){
            artifactTierLayout.setVisibility(View.GONE);
        } else {
            artifactTierLayout.setVisibility(View.VISIBLE);
        }
        artifactNoteLayout = findViewById(R.id.artifact_notes_layout);
        if (isHiddenNote || emptyNote){
            artifactNoteLayout.setVisibility(View.GONE);
        } else {
            artifactNoteLayout.setVisibility(View.VISIBLE);
        }

        LinearLayout dtlHeader = findViewById(R.id.artifact_dtl_header);
        if (isNullTier || (isHiddenTier && (isHiddenNote || emptyNote))){
            dtlHeader.setVisibility(View.GONE);
        } else {
            dtlHeader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(int position, String type) {
        if (type.equals("hero")){
            String id = usedByHeroAdapter.getList().get(position).getFileId();
            Intent intent = new Intent(this, HeroActivity.class);
            intent.putExtra("hero_id", id);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
