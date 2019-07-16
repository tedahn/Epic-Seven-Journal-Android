package com.kumaduma.epicseveninfo.PopHead;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.FragmentArtifact;
import com.kumaduma.epicseveninfo.FragmentCatalyst;
import com.kumaduma.epicseveninfo.FragmentHero;
import com.kumaduma.epicseveninfo.MainPager;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopHeadMainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    boolean doubleBackToExitPressedOnce = false;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    ViewPager mViewPager;
    String searchText = "";

    BottomNavigationView navigation;
    MenuItem prevMenuItem;
    MainPager mainPager;

    List<String> pages;
    int currentPage = 0;

    final String url = "https://assets.epicsevendb.com/";

    private ViewPager.OnPageChangeListener viewPagerChangeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            if (prevMenuItem != null)
                prevMenuItem.setChecked(false);
            else
                navigation.getMenu().getItem(0).setChecked(false);

            navigation.getMenu().getItem(i).setChecked(true);
            prevMenuItem = navigation.getMenu().getItem(i);

            if (i == 0){
                currentPage = pages.indexOf("hero");
                updateFilter();
            } else if (i == 1){
                currentPage = pages.indexOf("catalyst");
                updateFilter();
            } else if (i == 2){
                currentPage = pages.indexOf("artifact");
                updateFilter();
            } else {
                currentPage = pages.indexOf("camping");
                updateFilter();
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_hero:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_catalyst:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_artifact:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_camping:
                    mViewPager.setCurrentItem(3);
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_menu);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
        } else {
        }


        //DEFINE STATICS
        pages = Arrays.asList(getResources().getStringArray(R.array.main_menu_items));

        //NAVIGATION
        navigation = findViewById(R.id.service_navigation_bottom);
        navigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener);


        //VIEW
        mViewPager = findViewById(R.id.service_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mainPager = new MainPager(this.getSupportFragmentManager());
        mViewPager.setAdapter(mainPager);
        mViewPager.addOnPageChangeListener(viewPagerChangeListener);
        mViewPager.setCurrentItem(0);

//        //BUILD MAIN RECYCLER VIEW
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));

        //LOAD HERO FIRST
        navigation.setSelectedItemId(currentPage);
        if (currentPage == pages.indexOf("hero"))
            mViewPager.setCurrentItem(0);
        else if (currentPage == pages.indexOf("catalyst"))
            mViewPager.setCurrentItem(1);
        else if (currentPage == pages.indexOf("artifact"))
            mViewPager.setCurrentItem(2);
        else if (currentPage == pages.indexOf("camping"))
            mViewPager.setCurrentItem(3);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onQueryTextChange(String newText) {
        searchText = newText.toLowerCase();
        updateFilter();
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        //mStatusView.setText("Query = " + query + " : submitted");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    public boolean onClose() {
        updateFilter();
        return false;
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //boolean drawerOpen = menuDrawer.isDrawerOpen(filterElementView);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    public void updateFilter(){

        if (mViewPager.getCurrentItem() == 0) {
            FragmentHero fragment = (FragmentHero) mainPager.instantiateItem(mViewPager, 0);

//            fragment.updateFilter(searchText, filterRarityAdapter.getCheckedSet(), filterClassAdapter.getCheckedSet(),
//                    filterElementAdapter.getCheckedSet(), filterZodiacAdapter.getCheckedSet(), filterBuffAdapter.getCheckedSet(),
//                    filterDebuffAdapter.getCheckedSet());
        } else if (mViewPager.getCurrentItem() == 1) {
            FragmentCatalyst fragment = (FragmentCatalyst) mainPager.instantiateItem(mViewPager, 1);

            //fragment.updateFilter(searchText);
        } else if (mViewPager.getCurrentItem() == 2) {
            FragmentArtifact fragment = (FragmentArtifact) mainPager.instantiateItem(mViewPager, 2);

            //fragment.updateFilter(searchText, filterRarityAdapter.getCheckedSet(), filterClassAdapter.getCheckedSet());
        } else {

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (currentPage == pages.indexOf("hero"))
            mViewPager.setCurrentItem(0);
        else if (currentPage == pages.indexOf("catalyst"))
            mViewPager.setCurrentItem(1);
        else if (currentPage == pages.indexOf("artifact"))
            mViewPager.setCurrentItem(2);
        else if (currentPage == pages.indexOf("camping"))
            mViewPager.setCurrentItem(3);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}