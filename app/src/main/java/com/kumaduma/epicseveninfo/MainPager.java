package com.kumaduma.epicseveninfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainPager extends FragmentStatePagerAdapter {
    final String ASSET_URL = "https://assets.epicsevendb.com/";

    public MainPager(FragmentManager fm){
        super (fm);
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", i);
        bundle.putString("assetUrl", ASSET_URL);
        if (i == 0){
            Fragment pageFragment = new FragmentHero();
            pageFragment.setArguments(bundle);
            return pageFragment;
        } else if ( i == 1 ){
            Fragment pageFragment = new FragmentArtifact();
            pageFragment.setArguments(bundle);
            return pageFragment;
        } else if ( i == 2 ){
            Fragment pageFragment = new FragmentCatalyst();
            pageFragment.setArguments(bundle);
            return pageFragment;
        } else if ( i == 3){
            Fragment pageFragment = new FragmentCamping();
            pageFragment.setArguments(bundle);
            return pageFragment;
        } else {
            Fragment pageFragment = new FragmentMenu();
            pageFragment.setArguments(bundle);
            return pageFragment;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

}
