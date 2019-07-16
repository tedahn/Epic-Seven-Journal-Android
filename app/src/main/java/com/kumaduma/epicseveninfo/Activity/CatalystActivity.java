package com.kumaduma.epicseveninfo.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.Activity.Catalyst.ApShopAdapter;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.RecyclerView.HeroSmallListAdapter;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.Activity.Catalyst.LocationAdapter;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CatalystActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG = "CatalystActivity";
    Catalyst cata;
    String cataId;
    CatalystManager dataManager = CatalystManager.getInstance();
    private static final String ASSET_URL = "https://assets.epicsevendb.com/";
    LocationAdapter locationAdapter;
    ApShopAdapter apShopAdapter;
    HeroSmallListAdapter usedByHeroAdapter;
    RecyclerView rvLocation, rvApShop, rvUsedByHero;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalyst_activity);

        String title = null;

        TextView catalystName = findViewById(R.id.catalyst_act_name);
        ImageView catalystImage = findViewById(R.id.catalyst_act_image);
        ImageView catalystBgImage = findViewById(R.id.catalyst_act_image_bg);
        TextView catalystDesc = findViewById(R.id.catalyst_description);
        rvLocation = findViewById(R.id.catalyst_location_list);
        rvLocation.setLayoutManager(new LinearLayoutManager(this));

        rvApShop = findViewById(R.id.catalyst_apshop_list);
        rvApShop.setLayoutManager(new LinearLayoutManager(this));

        rvUsedByHero = findViewById(R.id.used_by_hero_recycle_view);

        Bundle extras = getIntent().getExtras();
        cataId = extras.getString("catalyst_id");
        cata = dataManager.getCatalystById(cataId);
        if (cata == null) {
            Toast toast = Toast.makeText(this, "Can't load catalyst!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            finish();
        } else {
            title = cata.getName();

            //PROFILE
            catalystName.setText(cata.getName());
            int bgImg = R.drawable._itembg_stuff_1;
            if (cata.getRarity().equals("2")){
                bgImg = R.drawable._itembg_stuff_2;
            } else if (cata.getRarity().equals("3")){
                bgImg = R.drawable._itembg_stuff_3;
            } else if (cata.getRarity().equals("4")){
                bgImg = R.drawable._itembg_stuff_4;
            } else if (cata.getRarity().equals("5")){
                bgImg = R.drawable._itembg_stuff_5;
            }
            Picasso.get().load(bgImg)
                    .into(catalystBgImage);
            Picasso.get().load(ASSET_URL+"/item/" + cata.getFileId() + ".png")
                    .error(R.drawable.ic_catalyst)
                    .into(catalystImage);

            catalystDesc.setText(cata.getDescription());

            //LOCATIONS
            locationAdapter = new LocationAdapter(this, cata.getLocations(),this);
            rvLocation.setAdapter(locationAdapter);
            Log.d(TAG, "onCreate: location list size : " +  cata.getLocations().size());

            //AP SHOPS
            apShopAdapter = new ApShopAdapter(this, cata.getApShops(),this);
            rvApShop.setAdapter(apShopAdapter);
            rvApShop.setVisibility(View.VISIBLE);
            Log.d(TAG, "onCreate: apshop list size : " +  cata.getApShops().size());


            if (cata.getLocations().isEmpty())
                findViewById(R.id.catalyst_no_locations_hint).setVisibility(View.VISIBLE);
            if (cata.getApShops().isEmpty())
                findViewById(R.id.catalyst_no_apshop_hint).setVisibility(View.VISIBLE);


            String zodiac = dataManager.getCatalystZodiacById(cata.getFileId());
            List<SimpleHero> tempHeroList = HeroManager.getInstance().getHeroList();
            List<SimpleHero> heroList = new ArrayList<>();
            Log.d(TAG, "onCreate: " + zodiac);
            for (SimpleHero h: tempHeroList){
                if (!h.getFileId().contains("phantasma") && h.getZodiac().equals(zodiac)){
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

            usedByHeroAdapter = new HeroSmallListAdapter(this, heroList,this, ASSET_URL,spanCount);
            rvUsedByHero.setAdapter(usedByHeroAdapter);
            usedByHeroAdapter.expandAll();

        }

        Toolbar t = findViewById(R.id.catalyst_act_toolbar);

        setSupportActionBar(t);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public void onItemClick(int position, String type) {
        if (type.equals("location")){
            //location is pressed
        } else if (type.equals("apshop")){
            //apshop is pressed
        } else if (type.equals("hero")){
            String id = usedByHeroAdapter.getList().get(position).getFileId();
            Log.d(TAG, "onItemClick: " + id);
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
