package com.kumaduma.epicseveninfo.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.DataManager.CampManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.CamperSelectAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CamperSelectActivity extends AppCompatActivity implements OnItemClickListener, SearchView.OnQueryTextListener{
    RecyclerView rvSelectView;
    private static final String ASSET_URL = "https://assets.epicsevendb.com/";
    CamperSelectAdapter adapter;
    CampManager manager = CampManager.getInstance();
    List<SimpleHero> heroList = HeroManager.getInstance().getHeroList();
    ImageView preview1, preview2, preview3, preview4;
    String searchText;
    Button calculate;
    CardView reset;
    SearchView search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camper_select_activity);

        rvSelectView = findViewById(R.id.camper_select_list);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvSelectView.setLayoutManager(new GridLayoutManager(this, 7));
        } else {
            rvSelectView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        adapter = new CamperSelectAdapter(this, heroList, this, ASSET_URL);
        rvSelectView.setAdapter(adapter);

        calculate = findViewById(R.id.camper_select_ok_button);
        reset = findViewById(R.id.camper_select_reset_button);
        reset.setOnClickListener((view)->{
            CampManager.getInstance().resetCampers();
            adapter.refreshSelected();
            refreshHeroPreview();
        });
        calculate.setOnClickListener((view)->{
            onBackPressed();
        });

        preview1 = findViewById(R.id.camper_list_preview_1);
        preview2 = findViewById(R.id.camper_list_preview_2);
        preview3 = findViewById(R.id.camper_list_preview_3);
        preview4 = findViewById(R.id.camper_list_preview_4);
        preview1.setOnClickListener((view)->{
            manager.removeAtIndex(0);
            adapter.refreshSelected();
            refreshHeroPreview();
        });
        preview2.setOnClickListener((view)->{
            manager.removeAtIndex(1);
            adapter.refreshSelected();
            refreshHeroPreview();
        });
        preview3.setOnClickListener((view)->{
            manager.removeAtIndex(2);
            adapter.refreshSelected();
            refreshHeroPreview();
        });
        preview4.setOnClickListener((view)->{
            manager.removeAtIndex(3);
            adapter.refreshSelected();
            refreshHeroPreview();
        });

        search = findViewById(R.id.camper_select_search);
        search.setOnQueryTextListener(this);
        search.setIconifiedByDefault(true);
        search.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEditText = search.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorText));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorText));
        ImageView searchClose = search.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close_white_24dp);

        refreshHeroPreview();
    }

    @Override
    public void onItemClick(int position, String type) {
        int result = manager.toggleHero(adapter.getHeroAt(position).getFileId());
        if (result == 2) {
            adapter.removeSelected(position);
        } else if (result == 0){
            Toast.makeText(this,"There are 4 campers already!",Toast.LENGTH_SHORT).show();
        }  else if (result == -1){
            Toast.makeText(this,"Error loading this Hero!",Toast.LENGTH_SHORT).show();
        } else if (result == 1){
            //successfully added hero
            adapter.setSelected(position);
        }
        refreshHeroPreview();
    }

    @Override
    public void onBackPressed() {
        if (search.isIconified()){
            super.onBackPressed();
        } else {
            search.setQuery("", false);
            search.setIconified(true);
        }
    }

    private void refreshHeroPreview(){
        LinkedList<ImageView> previewImages = new LinkedList<>();
        previewImages.push(preview1);
        previewImages.push(preview2);
        previewImages.push(preview3);
        previewImages.push(preview4);

        List<String> listS = manager.getSelectedHeroIds();
        for(String s : listS){
            ImageView iv = previewImages.pollLast();
            Picasso.get().load(ASSET_URL + "hero/" + s + "/icon.png").into(iv);
        }

        for(ImageView img : previewImages)
            img.setImageDrawable(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchText = newText.toLowerCase();
        List<SimpleHero> newList = new ArrayList<>();
        for (SimpleHero hero: heroList)
            if(hero.getFileId().contains(searchText))
                newList.add(hero);
        adapter.updateList(newList);
        return true;
    }
}
