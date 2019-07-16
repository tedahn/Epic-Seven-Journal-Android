package com.kumaduma.epicseveninfo;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kumaduma.epicseveninfo.Activity.CamperSelectActivity;
import com.kumaduma.epicseveninfo.Activity.SettingsActivity;
import com.kumaduma.epicseveninfo.Model.Settings.Contributor;
import com.kumaduma.epicseveninfo.RecyclerView.ContributorCardAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnAdapterItemClickListener;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class FragmentMenu extends Fragment implements OnItemClickListener, OnAdapterItemClickListener {

    private static final String TAG = "FragmentMenu";
    RecyclerView mRecyclerView;
    String url;

    private List<Contributor> contributorsList;
    ContributorCardAdapter contributorAdapter;
    NavigationView menuProfileNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.list_content_menu, container, false);
        url = bundle.getString("assetUrl");

        InputStream inputStream = view.getContext().getResources().openRawResource(R.raw.contributors);
        String text = null;
        try (final Reader reader = new InputStreamReader(inputStream)) {
            text = CharStreams.toString(reader);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Contributor>>(){}.getType();
            contributorsList = gson.fromJson(text, type);
        } catch (Exception e){
            Log.e(TAG, "onCreateView: ", e);
        }

        menuProfileNavigationView = view.findViewById(R.id.menu_profile);
        menuProfileNavigationView.setNavigationItemSelectedListener(settingNavigationItemSelectedListener);

        mRecyclerView = view.findViewById(R.id.menu_contributors_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        contributorAdapter = new ContributorCardAdapter(getContext(), contributorsList, 0, this);
        mRecyclerView.setAdapter(contributorAdapter);

        return view;
    }

    private NavigationView.OnNavigationItemSelectedListener settingNavigationItemSelectedListener = (MenuItem item) -> {
        switch(item.getItemId()){
            case R.id.menu_setting:
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    };

    @Override
    public void onItemClick(int position, String type) {

    }

    @Override
    public void onItemClick(int position, String type, int adapterId) {
        String tempUrl = contributorAdapter.getList().get(position).getUrl();
        if (tempUrl.equals("")){
            Toast.makeText(getContext(),getString(R.string.no_url_hint), Toast.LENGTH_SHORT).show();
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempUrl));
            startActivity(browserIntent);
        }
    }
}
