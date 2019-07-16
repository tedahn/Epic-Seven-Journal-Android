package com.kumaduma.epicseveninfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Activity.ArtifactActivity;
import com.kumaduma.epicseveninfo.DataManager.ArtifactManager;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.Model.SimpleArtifactTier;
import com.kumaduma.epicseveninfo.RecyclerView.MainArtifactListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentArtifact extends Fragment implements OnItemClickListener {

    RecyclerView mRecyclerView ;
    List<SimpleArtifactTier> artifactList = ArtifactManager.getInstance().getArtifactTierList();
    MainArtifactListAdapter mainArtifactListAdapter;

    final String ORDER= "SABCDEFGHIJKLMNOPQRSTUVWXYZ- ";

    Comparator<SimpleArtifactTier> byNameAsc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->o1.getName().compareTo(o2.getName());
    Comparator<SimpleArtifactTier> byNameDesc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->o2.getName().compareTo(o1.getName());
    Comparator<SimpleArtifactTier> byRarityAsc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->o1.getRarity() - o2.getRarity();
    Comparator<SimpleArtifactTier> byRarityDesc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->o2.getRarity() - o1.getRarity();
    Comparator<SimpleArtifactTier> byPvPAsc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->ORDER.indexOf(o2.getPvpAvg()) - ORDER.indexOf(o1.getPvpAvg());
    Comparator<SimpleArtifactTier> byPvPDesc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->ORDER.indexOf(o1.getPvpAvg()) - ORDER.indexOf(o2.getPvpAvg());
    Comparator<SimpleArtifactTier> byPvEAsc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->ORDER.indexOf(o2.getPveAvg()) - ORDER.indexOf(o1.getPveAvg());
    Comparator<SimpleArtifactTier> byPvEDesc =
            (SimpleArtifactTier o1, SimpleArtifactTier o2)->ORDER.indexOf(o1.getPveAvg()) - ORDER.indexOf(o2.getPveAvg());
    Map<Integer, Comparator<SimpleArtifactTier>> comparatorMap  = new HashMap<Integer, Comparator<SimpleArtifactTier>>() {{
        put(0, byNameAsc);
        put(1, byNameDesc);
        put(2, byRarityAsc);
        put(3, byRarityDesc);
        put(4, byPvPAsc);
        put(5, byPvPDesc);
        put(6, byPvEAsc);
        put(7, byPvEDesc);
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.list_content, container, false);

        mainArtifactListAdapter = new MainArtifactListAdapter(getContext(), artifactList, this, bundle.getString("assetUrl"));
        mRecyclerView = view.findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(mainArtifactListAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position, String type) {
        //Toast.makeText(this,"Artifact " + mainArtifactListAdapter.getList().get(position).getName() + " Pressed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), ArtifactActivity.class);
        String id = mainArtifactListAdapter.getList().get(position).getFileId();
        intent.putExtra("artifact_id", id);

        startActivity(intent);
    }

    public void updateFilter(String searchText, int orderByPosition, ArrayList<String> filRarity, ArrayList<String> filClass){
        List<SimpleArtifactTier> newList = new ArrayList<>();
        for (SimpleArtifactTier ar : artifactList) {
            if (searchText.isEmpty() || ar.getName().toLowerCase().contains(searchText)) {
                if (filRarity.isEmpty() || filRarity.contains(Integer.toString(ar.getRarity()))) {
                    if (filClass.isEmpty() || !ar.getExclusive().isEmpty() && filClass.containsAll(ar.getExclusive())) {
                        newList.add(ar);
                    }
                }
            }
        }
        Collections.sort(newList, comparatorMap.get(orderByPosition));
        mainArtifactListAdapter.updateList(newList);
    }
}
