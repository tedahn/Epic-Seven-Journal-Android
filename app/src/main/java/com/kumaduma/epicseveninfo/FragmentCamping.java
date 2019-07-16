package com.kumaduma.epicseveninfo;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.Activity.CamperSelectActivity;
import com.kumaduma.epicseveninfo.DataManager.CampManager;
import com.kumaduma.epicseveninfo.Model.CamperReactions;
import com.kumaduma.epicseveninfo.RecyclerView.CamperResultAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.MainCampingListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.List;

public class FragmentCamping extends Fragment implements OnItemClickListener {

    RecyclerView mRecyclerView, resultRecyclerView;
    MainCampingListAdapter mainAdapter;
    CamperResultAdapter resultAdapter;
    TextView helpText, resultText;
    String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.list_content_camping, container, false);
        url = bundle.getString("assetUrl");

        mainAdapter = new MainCampingListAdapter(getContext(), this, url);
        mRecyclerView = view.findViewById(R.id.main_list);
        GridLayoutManager grid;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            grid = new GridLayoutManager(getContext(), 4);
        } else {
            grid = new GridLayoutManager(getContext(), 2);
        }
        mRecyclerView.setLayoutManager(grid);
        mRecyclerView.setNestedScrollingEnabled(true);
        mainAdapter.updateList();
        mRecyclerView.setAdapter(mainAdapter);


        resultRecyclerView = view.findViewById(R.id.camper_result_list);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultText = view.findViewById(R.id.camping_results);
        resultText.setVisibility(View.GONE);
        helpText = view.findViewById(R.id.camping_help);
        helpText.setText(getResources().getString(R.string.camper_select_helper_short));

        return view;
    }

    @Override
    public void onItemClick(int position, String type) {
        Intent intent = new Intent(getContext(), CamperSelectActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager grid = new GridLayoutManager(getContext(), 4);
            mRecyclerView.setLayoutManager(grid);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            GridLayoutManager grid = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(grid);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mainAdapter.updateList();
        if (CampManager.getInstance().calculatable()) {
            List<CamperReactions> reactions = CampManager.getInstance().bestTwo();
            if (reactions != null) {
                resultAdapter = new CamperResultAdapter(getContext(), reactions, url);
                resultRecyclerView.setAdapter(resultAdapter);
                helpText.setText("Best Results:");
                int bestSum = reactions.get(0).getPoints() + reactions.get(1).getPoints();
                resultText.setText("Highest morale recoverable: " + bestSum);

                resultRecyclerView.setVisibility(View.VISIBLE);
                resultText.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "Not enough data to calculate!", Toast.LENGTH_SHORT).show();
            }
        } else {
            resultRecyclerView.setVisibility(View.GONE);
            resultText.setVisibility(View.GONE);
            helpText.setText(getResources().getString(R.string.camper_select_helper_long));
        }
    }

}
