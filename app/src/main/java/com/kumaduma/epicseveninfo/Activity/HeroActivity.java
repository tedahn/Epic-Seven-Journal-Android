package com.kumaduma.epicseveninfo.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.RadarChart;
import com.kumaduma.epicseveninfo.Activity.Hero.HeroRelationsSmallAdapter;
import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.DataManager.TierManager;
import com.kumaduma.epicseveninfo.Model.Artifact;
import com.kumaduma.epicseveninfo.Model.Hero.SpecialtyChangeName;
import com.kumaduma.epicseveninfo.Model.Tier.ArtifactTier;
import com.kumaduma.epicseveninfo.Model.Tier.PVETier;
import com.kumaduma.epicseveninfo.Model.Tier.PVPTier;
import com.kumaduma.epicseveninfo.RecyclerView.ArtifactPreviewListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.CatalystListSmallAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.HeroStatAdapter;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Activity.Hero.SkillCardAdapter;
import com.kumaduma.epicseveninfo.Activity.Hero.SkillEnhancementAdapter;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.Model.Hero.Awakening;
import com.kumaduma.epicseveninfo.Model.Hero.Hero;
import com.kumaduma.epicseveninfo.Model.Hero.MemoryImprint;
import com.kumaduma.epicseveninfo.Model.Hero.Rarity;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Enhancement;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Resources;
import com.kumaduma.epicseveninfo.Model.Hero.Skills.Skills;
import com.kumaduma.epicseveninfo.Model.Hero.Stats;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.Activity.Hero.HeroRelationsAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.CatalystCounterListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;
import com.kumaduma.epicseveninfo.RecyclerView.OnAdapterItemClickListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class HeroActivity extends AppCompatActivity implements OnItemClickListener, OnAdapterItemClickListener{
    private static final String TAG = "HeroActivity";
    private static final String ASSET_URL = "https://assets.epicsevendb.com/";
    private static String heroId = "";
    private HeroManager dataManager = HeroManager.getInstance();
    private Hero hero;

    boolean isTierListNull = false;
    boolean emptyPvpNote = true;
    boolean emptyPveNote = true;
    boolean emptyPvpArt = true;
    boolean emptyPveArt = true;

    private final int PVE_ARTIFACT_ID = 0;
    private final int PVP_ARTIFACT_ID = 1;


    RecyclerView relationsView, enhancementList, awakeningResources, pvpRecommendedArtifactList, pveRecommendedArtifactList;
    HeroRelationsSmallAdapter relationsAdapter;
    SkillCardAdapter skillCardAdapter;
    ArtifactPreviewListAdapter pvpArtifactAdapter, pveArtifactAdapter;
    ViewPager viewPager;
    LinearLayout enhancementListLayout, specialtyChangeLayout, heroSepcialtyChangeBar, pvpLayout, pveLayout;
    LinearLayout pvpTierLayout, pveTierLayout, pvpNoteLayout, pveNoteLayout, pvpSuggestLayout, pveSuggestLayout;

    Stats stat;
    CatalystCounterListAdapter resourceAdapter;
    CatalystListSmallAdapter awakeningResourcesAdapter;

    CatalystManager catalystManager = CatalystManager.getInstance();
    TierManager tierManager = TierManager.getInstance();
    ArtifactManager artifactManager = ArtifactManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_activity);


        Bundle extras = getIntent().getExtras();
        heroId = extras.getString("hero_id");
        hero = dataManager.getHeroById(heroId);
        String title = null;
        if (hero == null){
            Toast toast = Toast.makeText(this, "Can't load hero!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            finish();
        } else {
            title = hero.getName();
            this.populateData();
        }

        Toolbar t = findViewById(R.id.hero_info_toolbar);

        setSupportActionBar(t);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isHiddenSuggest = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_suggestion_all",false);
        boolean isHiddenNote = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_note_all",false);
        boolean isHiddenTier = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_tier_activity",false);


        if (emptyPvpArt)findViewById(R.id.hero_pvp_recommended_artifact_hint).setVisibility(View.VISIBLE);
        else findViewById(R.id.hero_pvp_recommended_artifact_hint).setVisibility(View.GONE);
        if (emptyPveArt)findViewById(R.id.hero_pve_recommended_artifact_hint).setVisibility(View.VISIBLE);
        else findViewById(R.id.hero_pve_recommended_artifact_hint).setVisibility(View.GONE);
        if (isHiddenTier){
            pveTierLayout.setVisibility(View.GONE);
            pvpTierLayout.setVisibility(View.GONE);
        } else {
            pveTierLayout.setVisibility(View.VISIBLE);
            pvpTierLayout.setVisibility(View.VISIBLE);
        }
        if (isHiddenNote){
            pveNoteLayout.setVisibility(View.GONE);
            pvpNoteLayout.setVisibility(View.GONE);
        } else {
            if  (emptyPveNote)
                pveNoteLayout.setVisibility(View.GONE);
            if (emptyPvpNote)
                pvpNoteLayout.setVisibility(View.GONE);
        }
        if (isHiddenSuggest){
            pveSuggestLayout.setVisibility(View.GONE);
            pvpSuggestLayout.setVisibility(View.GONE);
        } else {
            pveSuggestLayout.setVisibility(View.VISIBLE);
            pvpSuggestLayout.setVisibility(View.VISIBLE);
        }

        LinearLayout dtlHeader = findViewById(R.id.hero_dtl_header);
        if (isTierListNull || (isHiddenTier && (isHiddenNote || (emptyPveNote && emptyPvpNote)) && isHiddenSuggest)){
            dtlHeader.setVisibility(View.GONE);
        } else {
            dtlHeader.setVisibility(View.VISIBLE);
        }

    }

    //Recreate when Activity is called again by a different Activity when HeroActivity is inside Activity stack.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        Bundle extras = intent.getExtras();
        heroId = extras.getString("hero_id");
        hero = dataManager.getHeroById(heroId);
        if (hero == null){
            Toast toast = Toast.makeText(this, "Can't load hero!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            finish();
        } else {
            this.populateData();
            NestedScrollView heroActivityLayout = findViewById(R.id.hero_activity_scroll_view);
            heroActivityLayout.fullScroll(ScrollView.FOCUS_UP);
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

    @Override
    public void onItemClick(int position, String type) {
        if (type.equals("hero")){
            String id = relationsAdapter.getList().get(position).getFileId();
            Intent intent = new Intent(this, HeroActivity.class);
            intent.putExtra("hero_id", id);
            startActivity(intent);
        } else if (type.equals("catalyst_count")){
            Catalyst cata = resourceAdapter.getList().get(position);
            if (cata.getType().equals("catalyst")) {
                String id = cata.getFileId();
                Intent intent = new Intent(this, CatalystActivity.class);
                intent.putExtra("catalyst_id", id);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, cata.getName(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else if (type.equals("catalyst_awakening")) {
            Catalyst cata = awakeningResourcesAdapter.getList().get(position);
            if (cata.getType().equals("catalyst")) {
                String id = cata.getFileId();
                Intent intent = new Intent(this, CatalystActivity.class);
                intent.putExtra("catalyst_id", id);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, cata.getName(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    @Override
    public void onItemClick(int position, String type, int adapterId) {
        if (type.equals("artifact")){
            String id = null;
            if (adapterId == PVE_ARTIFACT_ID){
                id = pveArtifactAdapter.getList().get(position).getFileId();
            } else if (adapterId == PVP_ARTIFACT_ID){
                id = pvpArtifactAdapter.getList().get(position).getFileId();
            }
            if (id != null) {
                Intent intent = new Intent(this, ArtifactActivity.class);
                intent.putExtra("artifact_id", id);
                startActivity(intent);
            }

        }
    }


    private void populateData(){

        //PROFILE
        ImageView heroIcon = this.findViewById(R.id.hero_profile_icon);
        RatingBar heroRarity = this.findViewById(R.id.hero_profile_rarity);
        TextView heroName = this.findViewById(R.id.hero_profile_name);
        ImageView heroTypeIcon = this.findViewById(R.id.hero_profile_type);
        TextView heroTypeLabel = this.findViewById(R.id.hero_profile_type_label);
        ImageView heroElementIcon = this.findViewById(R.id.hero_profile_element_icon);
        TextView heroElementLabel = this.findViewById(R.id.hero_profile_element_label);
        ImageView heroZodiacIcon = this.findViewById(R.id.hero_profile_zodiac_icon);
        TextView heroZodiacLabel = this.findViewById(R.id.hero_profile_zodiac_label);
        SwitchCompat skillEnhanceSwitch = this.findViewById(R.id.hero_skill_enhancement_switch);
        TextView skillEnhanceSwitchText = this.findViewById(R.id.hero_skill_enhancement_switch_text);

        //SPECIALTY CHANGE
        specialtyChangeLayout = findViewById(R.id.hero_specialty_change_layout);
        ImageView heroSpecialtyChangeImage = findViewById(R.id.hero_specialty_change_image);
        TextView heroSpecialtyChangeName = findViewById(R.id.hero_specialty_change_name);
        TextView heroSepcialtyChangeDescription = findViewById(R.id.hero_specialty_change_description);
        heroSepcialtyChangeBar = findViewById(R.id.hero_specialty_change_bar);

        //BACKGROUND
        ImageView heroBattleImage = findViewById(R.id.hero_info_full_image);
        TextView heroBackground = this.findViewById(R.id.hero_info_background);
        relationsView = findViewById(R.id.hero_info_relations);

        //SKILL
        viewPager = findViewById(R.id.skill_view_pager);
        enhancementList = findViewById(R.id.skill_enhancement_list);
        enhancementList.setLayoutManager(new LinearLayoutManager(this));
        enhancementListLayout = findViewById(R.id.skill_enhancement_layout);

        //AWAKENING
        RatingBar awakeningStarBar = findViewById(R.id.awakening_stars);
        ImageView statIncreaseIconOne = findViewById(R.id.awakening_stat_icon_1);
        TextView statIncreaseNameOne = findViewById(R.id.awakening_stat_inc_desc_1);
        TextView statIncreasePtOne = findViewById(R.id.awakening_stat_inc_pt_1);
        ImageView statIncreaseIconTwo = findViewById(R.id.awakening_stat_icon_2);
        TextView statIncreaseNameTwo = findViewById(R.id.awakening_stat_inc_desc_2);
        TextView statIncreasePtTwo = findViewById(R.id.awakening_stat_inc_pt_2);
        ImageView statIncreaseIconThree = findViewById(R.id.awakening_stat_icon_3);
        TextView statIncreaseNameThree = findViewById(R.id.awakening_stat_inc_desc_3);
        TextView statIncreasePtThree = findViewById(R.id.awakening_stat_inc_pt_3);
        TextView awakeningResource = findViewById(R.id.awakening_consumed_resources);
        awakeningResources = findViewById(R.id.hero_awakening_consumed_resources_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        awakeningResources.setLayoutManager(linearLayoutManager);

        stat = new Stats();

        //DISCORD TIER INFO
        pvpTierLayout = findViewById(R.id.hero_pvp_tier_rating_layout);
        pveTierLayout = findViewById(R.id.hero_pve_tier_rating_layout);
        pvpSuggestLayout = findViewById(R.id.hero_pvp_suggest_layout);
        pveSuggestLayout = findViewById(R.id.hero_pve_suggest_layout);
        pvpNoteLayout = findViewById(R.id.hero_pvp_notes_layout);
        pveNoteLayout = findViewById(R.id.hero_pve_notes_layout);
        //Switch
        SwitchCompat suggestionSwitch = findViewById(R.id.hero_suggestion_switch);
        TextView suggestionSwitchText = findViewById(R.id.hero_suggestion_switch_text);
        //PVP
        pvpLayout = findViewById(R.id.hero_pvp_layout);
        TextView pvpArenaOffense = findViewById(R.id.pvp_tier_arena_offense);
        TextView pvpArenaDefense = findViewById(R.id.pvp_tier_arena_defense);
        TextView pvpGwOffense = findViewById(R.id.pvp_tier_gw_offense);
        TextView pvpGwDefense = findViewById(R.id.pvp_tier_gw_defense);
        TextView pvpAverage = findViewById(R.id.pvp_tier_average);
        pvpRecommendedArtifactList = findViewById(R.id.hero_pvp_recommended_artifact_list);
        pvpRecommendedArtifactList.setLayoutManager(new LinearLayoutManager(this));
        TextView pvpRecSets = findViewById(R.id.pvp_rec_sets);
        TextView pvpRecNeck = findViewById(R.id.pvp_rec_neck);
        TextView pvpSuggestedRoles = findViewById(R.id.pvp_suggested_role);
        //PVE
        pveLayout = findViewById(R.id.hero_pve_layout);
        TextView pveHunt = findViewById(R.id.pve_tier_hunt);
        TextView pveAbyss = findViewById(R.id.pve_tier_abyss);
        TextView pveRaid = findViewById(R.id.pve_tier_raid);
        TextView pveAverage = findViewById(R.id.pve_tier_average);
        pveRecommendedArtifactList = findViewById(R.id.hero_pve_recommended_artifact_list);
        pveRecommendedArtifactList.setLayoutManager(new LinearLayoutManager(this));
        TextView pveRecSets = findViewById(R.id.pve_rec_sets);
        TextView pveRecNeck = findViewById(R.id.pve_rec_neck);
        TextView pveSuggestedRoles = findViewById(R.id.pve_suggested_role);

        //MEMORY IMPRINT
        ImageView miNorth = findViewById(R.id.memory_imprint_north);
        ImageView miSouth = findViewById(R.id.memory_imprint_south);
        ImageView miEast = findViewById(R.id.memory_imprint_east);
        ImageView miWest = findViewById(R.id.memory_imprint_west);
        TextView miRankD = findViewById(R.id.memory_imprint_d);
        TextView miRankC = findViewById(R.id.memory_imprint_c);
        TextView miRankB = findViewById(R.id.memory_imprint_b);
        TextView miRankA = findViewById(R.id.memory_imprint_a);
        TextView miRankS = findViewById(R.id.memory_imprint_s);
        TextView miRankSS = findViewById(R.id.memory_imprint_ss);
        TextView miRankSSS = findViewById(R.id.memory_imprint_sss);
        TextView miStatus = findViewById(R.id.memory_imprint_status);
        ImageView miStatusIcon = findViewById(R.id.memory_imprint_status_icon);

        //RESOURCES
        RecyclerView heroResources = findViewById(R.id.hero_info_resources);
        heroResources.setLayoutManager(new LinearLayoutManager(this));

        //PROFILE
        Picasso.get()
                .load(ASSET_URL + "hero/" + hero.getFileId() + "/icon.png")
                .error(R.drawable.icon_missing)
                .into(heroIcon);
        heroRarity.setNumStars(hero.getRarity());
        heroName.setText(hero.getName());
        heroTypeIcon.setImageResource(this.getResources().getIdentifier("cm_icon_role_" + hero.getClassType().replace("-", "_"), "drawable", this.getPackageName()));

        String tempStr = hero.getClassType();
        if (!tempStr.isEmpty() && tempStr.length() > 1) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            heroTypeLabel.setText(tempStr);
            Picasso.get()
                    .load(
                            this.getResources().getIdentifier(
                                    "cm_icon_pro" + hero.getElement(),
                                    "drawable",
                                    this.getPackageName()))
                    .into(heroElementIcon);
        }
        tempStr = hero.getElement();
        if (!tempStr.isEmpty() && tempStr.length() > 1) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            heroElementLabel.setText(tempStr);
            Picasso.get()
                    .load(
                            this.getResources().getIdentifier(
                                    "zod_icon_" + hero.getZodiac(),
                                    "drawable",
                                    this.getPackageName()))
                    .into(heroZodiacIcon);
        }
        tempStr = hero.getZodiac();
        if (!tempStr.isEmpty() && tempStr.length() > 1) {
            tempStr = tempStr.substring(0, 1).toUpperCase() + tempStr.substring(1);
            heroZodiacLabel.setText(tempStr);
        }

        // SPECIALTY CHANGE
        if (hero.getSpecialtyChangeName() != null){
            SpecialtyChangeName specialty = hero.getSpecialtyChangeName();
            Picasso.get()
                    .load(ASSET_URL + "hero/" + specialty.getFileId() + "/icon.png")
                    .error(R.drawable.icon_missing)
                    .into(heroSpecialtyChangeImage);
            heroSpecialtyChangeName.setText(specialty.getName());
            String desc = hero.getName() + " has a Specialty Change to "+specialty.getName()+" . You need the unit to be level 50 before you can start the Specialty Change quests.";
            heroSepcialtyChangeDescription.setText(desc);
            heroSepcialtyChangeBar.setOnClickListener((View v) -> {
                Intent intent = new Intent(v.getContext(), HeroActivity.class);
                String id = specialty.getFileId();
                intent.putExtra("hero_id", id);
                v.getContext().startActivity(intent);
            });
        } else {
            specialtyChangeLayout.setVisibility(View.GONE);
        }

        //BACKGROUND
        Picasso.get()
                .load(ASSET_URL+"hero/"+hero.getFileId()+"/full.png")
                .error(R.drawable.full_missing)
                .into(heroBattleImage);
        String bg = hero.getBackground();
        heroBackground.setText(bg);
        relationsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        relationsAdapter = new HeroRelationsSmallAdapter(this, hero.getRelations(), ASSET_URL, this);
        relationsView.setAdapter(relationsAdapter);
        relationsView.setNestedScrollingEnabled(false);

        //SKILL
        skillCardAdapter = new SkillCardAdapter(this, hero.getSkills(), hero.getFileId(), enhancementList);
        viewPager.setAdapter(skillCardAdapter);
        viewPager.setPadding(70,0,70,0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                SkillEnhancementAdapter enhancementAdapter = new SkillEnhancementAdapter(getApplicationContext(), hero.getSkills().get(position).getEnhancement());
                enhancementList.setAdapter(enhancementAdapter);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        enhancementList.setAdapter(new SkillEnhancementAdapter(getApplicationContext(), hero.getSkills().get(viewPager.getCurrentItem()).getEnhancement()));
        enhancementListLayout.setVisibility(View.GONE);
        skillEnhanceSwitchText.setText("Skill View");

        skillEnhanceSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)->{
            if (isChecked){
                skillEnhanceSwitchText.setText("Enhancement View");
                enhancementListLayout.animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                enhancementListLayout.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                skillEnhanceSwitchText.setText("Skill View");
                enhancementListLayout.animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                enhancementListLayout.setVisibility(View.GONE);
                            }
                        });
            }
        });


        //AWAKENING
        awakeningStarBar.setOnRatingBarChangeListener((RatingBar ratingBar, float v, boolean b) -> {
            int stars = (int) ratingBar.getRating();
            Awakening aStats = hero.getAwakeningByRank(stars);
            if (aStats == null){
                return;
            }
            List<Map<String, Double>> s = aStats.getStatsIncrease();

            String statOneName,statOnePt,statTwoName,statTwoPt,statThreeName,statThreePt;
            statOneName = statOnePt = statTwoName = statTwoPt = statThreeName = statThreePt = "-";
            if (aStats.isSkillUpgrade()){
                statOneName = "Skill Upgrade";
                statOnePt = "YES";
                if (s.size() >= 2) {
                    Iterator<Map.Entry<String, Double>> itStatOne = s.get(0).entrySet().iterator();
                    if (itStatOne.hasNext()) {
                        Map.Entry<String, Double> statPtOne = itStatOne.next();
                        statTwoName = statPtOne.getKey();
                        statTwoPt = "+ " + stat.convertStatDouble(statPtOne.getValue());
                    }
                    Iterator<Map.Entry<String, Double>> itStatTwo = s.get(1).entrySet().iterator();
                    if (itStatTwo.hasNext()) {
                        Map.Entry<String, Double> statPtTwo = itStatTwo.next();
                        statThreeName = statPtTwo.getKey();
                        statThreePt = "+ " + stat.convertStatDouble(statPtTwo.getValue());
                    }
                }
            } else {
                if (s.size() >= 3) {
                    Iterator<Map.Entry<String, Double>> itStatOne = s.get(0).entrySet().iterator();
                    if (itStatOne.hasNext()) {
                        Map.Entry<String, Double> statPtOne = itStatOne.next();
                        statOneName = statPtOne.getKey();
                        statOnePt = "+ " + stat.convertStatDouble(statPtOne.getValue());
                    }
                    Iterator<Map.Entry<String, Double>> itStatTwo = s.get(1).entrySet().iterator();
                    if (itStatTwo.hasNext()) {
                        Map.Entry<String, Double> statPtTwo = itStatTwo.next();
                        statTwoName = statPtTwo.getKey();
                        statTwoPt = "+ " + stat.convertStatDouble(statPtTwo.getValue());
                    }
                    Iterator<Map.Entry<String, Double>> itStatThree = s.get(2).entrySet().iterator();
                    if (itStatThree.hasNext()) {
                        Map.Entry<String, Double> statPtThree = itStatThree.next();
                        statThreeName = statPtThree.getKey();
                        statThreePt = "+ " + stat.convertStatDouble(statPtThree.getValue());
                    }
                }
            }
            Picasso.get().load(ASSET_URL + "stat/cm_icon_stat_" + statOneName + ".png").into(statIncreaseIconOne);
            statIncreaseNameOne.setText(stat.getStatName(statOneName));
            statIncreasePtOne.setText(statOnePt);
            Picasso.get().load(ASSET_URL + "stat/cm_icon_stat_" + statTwoName + ".png").into(statIncreaseIconTwo);
            statIncreaseNameTwo.setText(stat.getStatName(statTwoName));
            statIncreasePtTwo.setText(statTwoPt);
            Picasso.get().load(ASSET_URL + "stat/cm_icon_stat_" + statThreeName + ".png").into(statIncreaseIconThree);
            statIncreaseNameThree.setText(stat.getStatName(statThreeName));
            statIncreasePtThree.setText(statThreePt);

            List<Resources> resourcesList = aStats.getResources();
            awakeningResource.setVisibility(View.GONE);
            //awakeningResource.setText(new Resources().toString(resourcesList));

            List<Catalyst> cataList = new ArrayList<>();
            Map<String,Integer> cataCount = new HashMap<>();
            for(Resources res : resourcesList){
                cataList.add(catalystManager.getCatalystById(res.getItem()));
                cataCount.put(res.getItem(), res.getQty());
            }
            cataList.removeAll(Collections.singleton(null));

            if (cataList.size() > 0) {
                awakeningResourcesAdapter = new CatalystListSmallAdapter(HeroActivity.this,
                        cataList, cataCount, "catalyst_awakening",
                        HeroActivity.this, ASSET_URL);
                awakeningResources.setAdapter(awakeningResourcesAdapter);
            } else {
                awakeningResource.setVisibility(View.GONE);
            }

        });
        if (hero.getAwakening() == null){
            //Log.d(TAG, "onCreate: no awakening for this hero");
        }else
            awakeningStarBar.setRating(1);


        //recommendedArtifactList = findViewById(R.id.hero_recommended_artifact_list);
        suggestionSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)->{
            if (isChecked){
                pveLayout.setVisibility(View.VISIBLE);
                pvpLayout.setVisibility(View.GONE);
                suggestionSwitchText.setText("PvE");
                suggestionSwitchText.setTextColor(getResources().getColor(R.color.colorPveFont));
            } else {
                pveLayout.setVisibility(View.GONE);
                pvpLayout.setVisibility(View.VISIBLE);
                suggestionSwitchText.setText("PvP");
                suggestionSwitchText.setTextColor(getResources().getColor(R.color.colorPvpFont));
            }
        });
        suggestionSwitch.setChecked(true);
        //DISCORD TIER LIST
        PVPTier heroPvpTier = tierManager.getPvpTierById(hero.getFileId());
        if (heroPvpTier == null){
            //Show error message for no Pvp data
            Log.d(TAG, "onCreate: " + hero.getFileId() + " is null");
        } else {
            String str = Double.toString(heroPvpTier.getArenaOffense());
            pvpArenaOffense.setText(str);
            str = Double.toString(heroPvpTier.getArenaDefense());
            pvpArenaDefense.setText(str);
            str = Double.toString(heroPvpTier.getGwOffense());
            pvpGwOffense.setText(str);
            str = Double.toString(heroPvpTier.getGwDefense());
            pvpGwDefense.setText(str);
            str = Double.toString(heroPvpTier.getAverage());
            pvpAverage.setText(str);

            StringBuilder setRec = new StringBuilder();
            for (String s : heroPvpTier.getRecommendedSetList()){
                setRec.append(s).append("\n");
            }
            pvpRecSets.setText(setRec.toString().trim());
            StringBuilder neckRec = new StringBuilder();
            for (String s : heroPvpTier.getRecommendedNeckList()){
                neckRec.append(s).append("\n");
            }
            pvpRecNeck.setText(neckRec.toString().trim());
            StringBuilder suggestedRoles = new StringBuilder();
            for (String s : heroPvpTier.getSuggestedRoleList()){
                suggestedRoles.append(s).append("\n");
            }
            pvpSuggestedRoles.setText(suggestedRoles.toString().trim());

            List<Artifact> pvpArtifactRecList = new ArrayList<>();

            ArtifactTier artifactTier = tierManager.getArtifactTierByImgId(heroPvpTier.getRecommendedArtifactImageId());
            if (artifactTier != null) {
                Artifact art = artifactManager.getArtifactById(artifactTier.getNameId());
                pvpArtifactRecList.add(art);
            }

            List<String> pvpArtAltList = heroPvpTier.getAlternateArtifactImageIdList();
            for (String alt: pvpArtAltList) {
                artifactTier = tierManager.getArtifactTierByImgId(alt);
                if (artifactTier != null) {
                    Artifact art = artifactManager.getArtifactById(artifactTier.getNameId());
                    pvpArtifactRecList.add(art);
                }
            }
            pvpArtifactRecList.removeAll(Collections.singleton(null));

            pvpArtifactAdapter = new ArtifactPreviewListAdapter(this, pvpArtifactRecList, PVP_ARTIFACT_ID, this, ASSET_URL);
            pvpRecommendedArtifactList.setAdapter(pvpArtifactAdapter);

            emptyPvpArt = pvpArtifactRecList.isEmpty();

            TextView pvpNotes = findViewById(R.id.pvp_notes);
            String note = heroPvpTier.getNote();
            emptyPvpNote = note.trim().equals("");
            pvpNotes.setText(note);

        }
        PVETier heroPveTier = tierManager.getPveTierById(hero.getFileId());
        if (heroPveTier == null){
            //Show error message for no Pve data
            Log.d(TAG, "onCreate: " + hero.getFileId() + " is null");
        } else {
            RadarChart pveRadarChart = findViewById(R.id.hero_pve_radar_graph);
            pveRadarChart.getDescription().setEnabled(false);


            /*
            String str = Double.toString(heroPveTier.getHunt());
            pveHunt.setText(str);
            str = Double.toString(heroPveTier.getAbyss());
            pveAbyss.setText(str);
            str = Double.toString(heroPveTier.getRaid());
            pveRaid.setText(str);
            str = Double.toString(heroPveTier.getAverage());
            pveAverage.setText(str);
            */

            StringBuilder setRec = new StringBuilder();
            for (String s : heroPveTier.getRecommendedSetList()){
                setRec.append(s).append("\n");
            }
            pveRecSets.setText(setRec.toString().trim());
            StringBuilder neckRec = new StringBuilder();
            for (String s : heroPveTier.getRecommendedNeckList()){
                neckRec.append(s).append("\n");
            }
            pveRecNeck.setText(neckRec.toString().trim());
            StringBuilder suggestedRoles = new StringBuilder();
            for (String s : heroPveTier.getSuggestedRoleList()){
                suggestedRoles.append(s).append("\n");
            }
            pveSuggestedRoles.setText(suggestedRoles.toString().trim());

            List<Artifact> pveArtifactRecList = new ArrayList<>();

            ArtifactTier artifactTier = tierManager.getArtifactTierByImgId(heroPveTier.getRecommendedArtifactImageId());
            if (artifactTier != null) {
                Artifact art = artifactManager.getArtifactById(artifactTier.getNameId());
                pveArtifactRecList.add(art);
            }

            List<String> pveArtAltList = heroPveTier.getAlternateArtifactImageIdList();
            for (String alt: pveArtAltList) {
                artifactTier = tierManager.getArtifactTierByImgId(alt);
                if (artifactTier != null) {
                    Artifact art = artifactManager.getArtifactById(artifactTier.getNameId());
                    pveArtifactRecList.add(art);
                }
            }

            Log.d(TAG, "onCreate: list is now " + pveArtifactRecList.size());
            pveArtifactRecList.removeAll(Collections.singleton(null));

            pveArtifactAdapter = new ArtifactPreviewListAdapter(this, pveArtifactRecList, PVE_ARTIFACT_ID, this, ASSET_URL);
            pveRecommendedArtifactList.setAdapter(pveArtifactAdapter);

            emptyPveArt = pveArtifactRecList.isEmpty();

            TextView pveNotes = findViewById(R.id.pve_notes);
            String note = heroPveTier.getNote();
            emptyPveNote = note.trim().equals("");
            pveNotes.setText(note);
        }

        if (heroPveTier == null && heroPvpTier == null){
            isTierListNull = true;
        }

        //MEMORY IMPRINT
        Stats stat = new Stats();
        if (hero.getMemoryImprintFormation().isNorth()) miNorth.setImageDrawable(getResources().getDrawable(R.drawable.memory_imprint_on));
        if (hero.getMemoryImprintFormation().isSouth()) miSouth.setImageDrawable(getResources().getDrawable(R.drawable.memory_imprint_on));
        if (hero.getMemoryImprintFormation().isEast()) miEast.setImageDrawable(getResources().getDrawable(R.drawable.memory_imprint_on));
        if (hero.getMemoryImprintFormation().isWest()) miWest.setImageDrawable(getResources().getDrawable(R.drawable.memory_imprint_on));
        List<MemoryImprint> miList = hero.getMemoryImprint();
        DecimalFormat df = new DecimalFormat("#%");
        for (MemoryImprint mi : miList){
            double num = mi.getStatus().getIncrease();
            String str = "+";
            if (num < 1) str += df.format(num);
            else str += Integer.toString((int)num);
            switch (mi.getRank().toUpperCase()){
                case "D":
                    if (num != 0) miRankD.setText(str);
                    break;
                case "C":
                    if (num != 0) miRankC.setText(str);
                    break;
                case "B":
                    if (num != 0) miRankB.setText(str);
                    break;
                case "A":
                    if (num != 0) miRankA.setText(str);
                    break;
                case "S":
                    if (num != 0) miRankS.setText(str);
                    break;
                case "SS":
                    if (num != 0) miRankSS.setText(str);
                    break;
                case "SSS":
                    if (num != 0) miRankSSS.setText(str);
                    break;
            }
            miStatus.setText(stat.getStatName(mi.getStatus().getType()));
        }

        Picasso.get().load(ASSET_URL + "stat/cm_icon_stat_"+hero.getMemoryImprintAttribute()+".png").into(miStatusIcon);

        //RESOURCES
        List<Catalyst> catalystList = new ArrayList<>();
        Map<String, Integer> catalystCount = new HashMap<String, Integer>();
        Set<String> resourceUnique = new HashSet<>();
        List<Skills> skills = hero.getSkills();

        for(Skills s : skills){
            Iterator<Enhancement> iSkill = s.getEnhancement().iterator();
            while(iSkill.hasNext()){
                Iterator<Resources> iResource = iSkill.next().getResources().iterator();
                while(iResource.hasNext()){
                    Resources catRes = iResource.next();
                    int q = 0;
                    if (catalystCount.containsKey(catRes.getItem())){
                        q += catalystCount.get(catRes.getItem());
                    }
                    catalystCount.put(catRes.getItem(), q + catRes.getQty());
                    resourceUnique.add(catRes.getItem());
                }

            }
        }
        List<Awakening> awakenings = hero.getAwakening();
        for(Awakening a : awakenings){
            Iterator<Resources> iResource = a.getResources().iterator();
            while(iResource.hasNext()){
                Resources catRes = iResource.next();
                int q = 0;
                if (catalystCount.containsKey(catRes.getItem())){
                    q += catalystCount.get(catRes.getItem());
                }
                catalystCount.put(catRes.getItem(), q + catRes.getQty());
                resourceUnique.add(catRes.getItem());
            }
        }

        Iterator<String> heroResourceItr = resourceUnique.iterator();
        CatalystManager itemManager = CatalystManager.getInstance();

        //MAIN CATALYST COUNTER - ONLY ADD CATALYSTS
        while(heroResourceItr.hasNext()){
            Catalyst ca = itemManager.getCatalystById(heroResourceItr.next());
            if (ca != null && (ca.getType().equals("catalyst") || ca.getType().equals("rune") || ca.getType().equals("skill-enhance"))) catalystList.add(ca);
        }
        Comparator<Catalyst> compareType = (Catalyst o1, Catalyst o2)->o1.getType().compareTo(o2.getType());
        Comparator<Catalyst> compareRarity = (Catalyst o1, Catalyst o2)->o2.getRarity().compareTo(o1.getRarity());
        Collections.sort(catalystList,compareRarity);
        Collections.sort(catalystList,compareType);
        if (catalystList.isEmpty()){
            findViewById(R.id.hero_info_resources_layout).setVisibility(View.GONE);
        } else {
            resourceAdapter = new CatalystCounterListAdapter(this, catalystList, catalystCount, this, getResources().getString(R.string.esdb_asset_url));
            heroResources.setAdapter(resourceAdapter);
        }


        //STATS
        Spinner statsSpinner = findViewById(R.id.hero_stats_spinner);
        RecyclerView rvHeroStats = findViewById(R.id.hero_stats_rv);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<>(hero.getStats().keySet()));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statsSpinner.setAdapter(dataAdapter);

        rvHeroStats.setLayoutManager(new LinearLayoutManager(this));

        rvHeroStats.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        statsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                HeroStatAdapter statAdapter = new HeroStatAdapter(HeroActivity.this,
                        hero.getStats().get(statsSpinner.getSelectedItem().toString()).getStatAsList(),
                        hero.getStats().get(statsSpinner.getSelectedItem().toString()).getStatsNameAsList(),
                        ASSET_URL);
                rvHeroStats.setAdapter(statAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }



}
