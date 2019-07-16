package com.kumaduma.epicseveninfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.PopHead.PopHeadService;
import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Drawer.FilterDrawerItem;
import com.kumaduma.epicseveninfo.Drawer.FilterDrawerListAdapter;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.Model.SimpleArtifact;
import com.kumaduma.epicseveninfo.Model.SimpleHero;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    boolean doubleBackToExitPressedOnce = false;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    RecyclerView mRecyclerView;
    ViewPager mViewPager;
    DrawerLayout menuDrawer;
    SwitchCompat popHeadSwitch;
    SearchView searchBar;
    String searchText = "";

    FloatingActionButton myFab;
    Button filterReset;
    View mainContainer;
    BottomNavigationView navigation;
    MenuItem prevMenuItem;
    MainPager mainPager;
    NavigationView navigationFilter;

    RecyclerView filterRarityView, filterElementView, filterZodiacView, filterClassView, filterBuffView, filterDebuffView;
    ArrayList<FilterDrawerItem> filterRarity, filterElements, filterZodiacs, filterClasses, filterBuffs, filterDebuffs;
    FilterDrawerListAdapter filterRarityAdapter, filterElementAdapter, filterZodiacAdapter, filterClassAdapter, filterBuffAdapter, filterDebuffAdapter;
    LinearLayout filterRarityLayout, filterElementLayout, filterZodiacLayout, filterClassLayout, filterBuffLayout, filterDebuffLayout;

    String[] orderByItems;
    Spinner orderBySpinner;

    boolean prefHiddenTierList;

    List<String> pages;
    int currentPage = 0;
    Map<String,String> buffAndDebuffs = new HashMap<>();

    final String url = "https://assets.epicsevendb.com/";

    private ViewPager.OnPageChangeListener viewPagerChangeListener;

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            closeAllDrawers();
            switch (item.getItemId()) {
                case R.id.navigation_hero:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_artifact:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_catalyst:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_camping:
                    mViewPager.setCurrentItem(3);
                    break;
                case R.id.navigation_menu:
                    mViewPager.setCurrentItem(4);
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContainer = findViewById(R.id.main_container);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mainContainer.setBackground(getResources().getDrawable(R.drawable.bg_main));
        } else {
            mainContainer.setBackground(getResources().getDrawable(R.drawable.bg_main_portrait));
        }


        //DEFINE STATICS
        pages = Arrays.asList(getResources().getStringArray(R.array.main_menu_items));
        filterBuffLayout = findViewById(R.id.filter_list_buff_layout);
        filterClassLayout = findViewById(R.id.filter_list_type_layout);
        filterDebuffLayout = findViewById(R.id.filter_list_debuff_layout);
        filterElementLayout = findViewById(R.id.filter_list_element_layout);
        filterRarityLayout = findViewById(R.id.filter_list_rarity_layout);
        filterZodiacLayout = findViewById(R.id.filter_list_zodiac_layout);

        //NAVIGATION
        navigation = findViewById(R.id.navigation_bottom);
        navigation.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener);


        viewPagerChangeListener = new ViewPager.OnPageChangeListener(){

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
                    currentPage = pages.indexOf(getResources().getString(R.string.title_hero));
                    filterRarityLayout.setVisibility(View.VISIBLE);
                    filterElementLayout.setVisibility(View.VISIBLE);
                    filterZodiacLayout.setVisibility(View.VISIBLE);
                    filterClassLayout.setVisibility(View.VISIBLE);
                    filterBuffLayout.setVisibility(View.VISIBLE);
                    filterDebuffLayout.setVisibility(View.VISIBLE);
                    menuDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    myFab.show();
                    updateFilter();
                } else if (i == 1){
                    currentPage = pages.indexOf(getResources().getString(R.string.title_artifact));
                    filterRarityLayout.setVisibility(View.VISIBLE);
                    filterElementLayout.setVisibility(View.GONE);
                    filterZodiacLayout.setVisibility(View.GONE);
                    filterClassLayout.setVisibility(View.VISIBLE);
                    filterBuffLayout.setVisibility(View.GONE);
                    filterDebuffLayout.setVisibility(View.GONE);
                    menuDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    myFab.show();
                    updateFilter();
                } else if (i == 2){
                    currentPage = pages.indexOf(getResources().getString(R.string.title_catalyst));
                    filterRarityLayout.setVisibility(View.GONE);
                    filterElementLayout.setVisibility(View.GONE);
                    filterZodiacLayout.setVisibility(View.GONE);
                    filterClassLayout.setVisibility(View.GONE);
                    filterBuffLayout.setVisibility(View.GONE);
                    filterDebuffLayout.setVisibility(View.GONE);
                    menuDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    myFab.hide();
                    updateFilter();
                } else if (i == 3){
                    currentPage = pages.indexOf(getResources().getString(R.string.title_camping));
                    filterRarityLayout.setVisibility(View.GONE);
                    filterElementLayout.setVisibility(View.GONE);
                    filterZodiacLayout.setVisibility(View.GONE);
                    filterClassLayout.setVisibility(View.GONE);
                    filterBuffLayout.setVisibility(View.GONE);
                    filterDebuffLayout.setVisibility(View.GONE);
                    menuDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    myFab.hide();
                    updateFilter();
                } else {
                    currentPage = pages.indexOf(getResources().getString(R.string.title_menu));
                    filterRarityLayout.setVisibility(View.GONE);
                    filterElementLayout.setVisibility(View.GONE);
                    filterZodiacLayout.setVisibility(View.GONE);
                    filterClassLayout.setVisibility(View.GONE);
                    filterBuffLayout.setVisibility(View.GONE);
                    filterDebuffLayout.setVisibility(View.GONE);
                    menuDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    myFab.hide();
                    updateFilter();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };


        //VIEW
        mViewPager = findViewById(R.id.main_view_pager);
        mViewPager.setOffscreenPageLimit(1);
        mainPager = new MainPager(this.getSupportFragmentManager());
        mViewPager.setAdapter(mainPager);
        mViewPager.addOnPageChangeListener(viewPagerChangeListener);
        mViewPager.setCurrentItem(0);
        menuDrawer = this.findViewById(R.id.main_menu_drawer);

        //CREATE FILTER
        filterRarityView = findViewById(R.id.filter_list_rarity);
        filterElementView = findViewById(R.id.filter_list_element);
        filterZodiacView = findViewById(R.id.filter_list_zodiac);
        filterClassView = findViewById(R.id.filter_list_type);
        filterBuffView = findViewById(R.id.filter_list_buff);
        filterDebuffView = findViewById(R.id.filter_list_debuff);
        filterRarityView.setLayoutManager(new LinearLayoutManager(this));
        filterElementView.setLayoutManager(new GridLayoutManager(this, 5));
        filterZodiacView.setLayoutManager(new GridLayoutManager(this, 6));
        filterClassView.setLayoutManager(new GridLayoutManager(this, 7));
        filterBuffView.setLayoutManager(new LinearLayoutManager(this));
        filterDebuffView.setLayoutManager(new LinearLayoutManager(this));

        filterRarity = createFilterList(R.array.rarityText, R.array.rarityInt, R.array.rarityIcons);
        filterElements = createFilterList(R.array.elements,R.array.elementTag,R.array.elementIcons);
        filterZodiacs = createFilterList(R.array.zodiacs,R.array.zodiacTag,R.array.zodiacIcons);
        filterClasses = createFilterList(R.array.classType,R.array.classTypeTag,R.array.classTypeIcons);

        filterRarityAdapter = new FilterDrawerListAdapter(this, filterRarity,true,false);
        filterElementAdapter = new FilterDrawerListAdapter(this, filterElements,false, true);
        filterZodiacAdapter = new FilterDrawerListAdapter(this, filterZodiacs,false, true);
        filterClassAdapter = new FilterDrawerListAdapter(this, filterClasses,false, true);

        filterRarityView.setAdapter(filterRarityAdapter);
        filterElementView.setAdapter(filterElementAdapter);
        filterZodiacView.setAdapter(filterZodiacAdapter);
        filterClassView.setAdapter(filterClassAdapter);

        //Getting Buff/Debuff list
        List<String> buffList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.buffList)));
        List<String> debuffList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.debuffList)));
        List<String> buffNameList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.buffName)));
        List<String> debuffNameList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.debuffName)));
        for (int i = 0;  i < buffList.size(); i++)
            buffAndDebuffs.put(buffList.get(i), buffNameList.get(i));
        for (int i = 0;  i < debuffList.size(); i++)
            buffAndDebuffs.put(debuffList.get(i), debuffNameList.get(i));
        ArrayList<String> buffs = HeroManager.getInstance().getBuffList();
        ArrayList<String> debuffs = HeroManager.getInstance().getDebuffList();

        filterBuffs = new ArrayList<>();
        filterDebuffs = new ArrayList<>();
        for(String buff : buffs){
            String name = buff;
            if (buffAndDebuffs.containsKey(buff))
                name = buffAndDebuffs.get(buff);
            else name = name.replace("stic_","").replace("_"," ");
            filterBuffs.add(new FilterDrawerItem(name, buff,
                    getResources().getString(R.string.esdb_asset_url)+"buff/"+buff+".png", false));
        }
        for(String debuff : debuffs){
            String name = debuff;
            if (buffAndDebuffs.containsKey(debuff))
                name = buffAndDebuffs.get(debuff);
            else name = name.replace("stic_","").replace("_"," ");
            filterDebuffs.add(new FilterDrawerItem(name, debuff,
                    getResources().getString(R.string.esdb_asset_url)+"buff/"+debuff+".png", false));
        }
        filterBuffAdapter = new FilterDrawerListAdapter(this, filterBuffs,false,false);
        filterDebuffAdapter = new FilterDrawerListAdapter(this, filterDebuffs,false,false);
        filterBuffView.setAdapter(filterBuffAdapter);
        filterDebuffView.setAdapter(filterDebuffAdapter);

        filterReset = findViewById(R.id.filter_list_reset_btn);
        filterReset.setOnClickListener((View)-> deselectAllFilter());

//        //BUILD MAIN RECYCLER VIEW
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, OrientationHelper.VERTICAL));

        //LOAD HERO FIRST
        navigation.setSelectedItemId(currentPage);
        if (currentPage == pages.indexOf(getResources().getString(R.string.title_hero)))
            mViewPager.setCurrentItem(0);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_artifact)))
            mViewPager.setCurrentItem(1);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_catalyst)))
            mViewPager.setCurrentItem(2);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_camping)))
            mViewPager.setCurrentItem(3);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_menu)))
            mViewPager.setCurrentItem(4);
        //onHeroLoad();

        //SET FLOATING BUTTON LISTENER
        myFab = this.findViewById(R.id.filter_floating_button);
        myFab.setOnClickListener((onClick)-> {
                if (menuDrawer.isDrawerOpen(Gravity.END)) {
                    menuDrawer.closeDrawer(Gravity.END);
                } else {
                    menuDrawer.openDrawer(Gravity.END);
                }
        });

        // Now retrieve the DrawerLayout so that we can set the status bar color.
        // This only takes effect on Lollipop, or when using translucentStatusBar
        // on KitKat.
        Toolbar toolbar = findViewById(R.id.main_menu_toolbar);
        setSupportActionBar(toolbar);
        menuDrawer.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));

        prefHiddenTierList = !PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_tier_list", false);
        createOrderByList();

        LinearLayout filterDrawerLayout = this.findViewById(R.id.filter_drawer_layout);
        ViewCompat.setOnApplyWindowInsetsListener(mainContainer, (v, insets) -> {
            filterDrawerLayout.setPadding(0,insets.getSystemWindowInsetTop(),0,insets.getSystemWindowInsetBottom());
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        createOrderByList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        /*
        popHeadSwitch = (SwitchCompat) menu.findItem(R.id.pophead_switch).getActionView();

        popHeadSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getApplicationContext())) {
                        //If the draw over permission is not available open the settings screen
                        //to grant the permission.
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                    } else {
                        startService(new Intent(MainActivity.this, PopHeadService.class));
                        finish();
                    }
                }
            }
        });
        if (android.os.Build.VERSION.SDK_INT < 26)
            popHeadSwitch.setVisibility(View.GONE);
        else
            popHeadSwitch.setVisibility(View.GONE);
            //Toast.makeText(this,""+android.os.Build.VERSION.SDK_INT,Toast.LENGTH_SHORT).show();
         */


        searchBar = (SearchView) menu.findItem(R.id.navigation_search).getActionView();
        searchBar.setIconifiedByDefault(true);
        searchBar.setMaxWidth(Integer.MAX_VALUE);
        EditText searchEditText = searchBar.findViewById(R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorText));
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorText));

        searchBar.setOnQueryTextListener(this);
        searchBar.setOnCloseListener(this);
        return super.onCreateOptionsMenu(menu);
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
//            case R.id.pophead_switch:
//                popHeadSwitch.setChecked(!popHeadSwitch.isChecked());
//                return true;
            default:
                return false;
        }
    }

    private void closeAllDrawers(){
        menuDrawer.closeDrawer(Gravity.END);
    }

    @Override
    public void onBackPressed() {
        if (menuDrawer.isDrawerOpen(Gravity.END)) {
            menuDrawer.closeDrawer(Gravity.END);
        } else if (!searchBar.isIconified()){
            //clear search
            searchBar.setQuery("", false);
            searchBar.setIconified(true);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
        }
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //boolean drawerOpen = menuDrawer.isDrawerOpen(filterElementView);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private ArrayList<FilterDrawerItem> createFilterList(int nameText, int tagText, int drawable){
        String[] navMenuTitles = getResources().getStringArray(nameText);
        String[] menuTags = getResources().getStringArray(tagText);
        TypedArray navMenuIcons = getResources().obtainTypedArray(drawable);

        ArrayList<FilterDrawerItem> list = new ArrayList<>();
        for (int i = 0; i < navMenuTitles.length; i++){
            list.add(new FilterDrawerItem(navMenuTitles[i], menuTags[i],
                    navMenuIcons.getResourceId(i, -1), false));
        }

        navMenuIcons.recycle();

        return list;
    }


    public void updateFilter(){

        if (mViewPager.getCurrentItem() == 0) {
            FragmentHero fragment = (FragmentHero) mainPager.instantiateItem(mViewPager, 0);

            fragment.updateFilter(searchText, orderBySpinner.getSelectedItemPosition(), filterRarityAdapter.getCheckedSet(), filterClassAdapter.getCheckedSet(),
                    filterElementAdapter.getCheckedSet(), filterZodiacAdapter.getCheckedSet(), filterBuffAdapter.getCheckedSet(),
                    filterDebuffAdapter.getCheckedSet());
        } else if (mViewPager.getCurrentItem() == 1) {
            FragmentArtifact fragment = (FragmentArtifact) mainPager.instantiateItem(mViewPager, 1);

            fragment.updateFilter(searchText, orderBySpinner.getSelectedItemPosition(), filterRarityAdapter.getCheckedSet(), filterClassAdapter.getCheckedSet());
        } else if (mViewPager.getCurrentItem() == 2) {
            FragmentCatalyst fragment = (FragmentCatalyst) mainPager.instantiateItem(mViewPager, 2);

            fragment.updateFilter(searchText);
        } else {

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (currentPage == pages.indexOf(getResources().getString(R.string.title_hero)))
            mViewPager.setCurrentItem(0);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_artifact)))
            mViewPager.setCurrentItem(1);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_catalyst)))
            mViewPager.setCurrentItem(2);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_camping)))
            mViewPager.setCurrentItem(3);
        else if (currentPage == pages.indexOf(getResources().getString(R.string.title_menu)))
            mViewPager.setCurrentItem(4);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mainContainer.setBackground(getResources().getDrawable(R.drawable.bg_main));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            mainContainer.setBackground(getResources().getDrawable(R.drawable.bg_main_portrait));
        }
    }

    public void deselectAllFilter(){
        filterRarityAdapter.deselctAll();
        filterClassAdapter.deselctAll();
        filterElementAdapter.deselctAll();
        filterZodiacAdapter.deselctAll();
        filterBuffAdapter.deselctAll();
        filterDebuffAdapter.deselctAll();
        updateFilter();
    }

    public void onFilterCheckboxClicked(View view){
        updateFilter();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                startService(new Intent(MainActivity.this, PopHeadService.class));
                finish();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available.",
                        Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void createOrderByList() {
        boolean isHiddenTierList = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("dtl_tier_list", false);
        if (prefHiddenTierList != isHiddenTierList){
            orderByItems = getResources().getStringArray(R.array.filter_order_by_items);
            if (!isHiddenTierList) {
                String[] orderByItemsTier = getResources().getStringArray(R.array.filter_order_by_items_tier);
                orderByItems = ArrayUtils.addAll(orderByItems, orderByItemsTier);
            }

            orderBySpinner = findViewById(R.id.filter_order_by_spinner);
            orderBySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, orderByItems));
            orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    updateFilter();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        prefHiddenTierList = isHiddenTierList;
    }


}
