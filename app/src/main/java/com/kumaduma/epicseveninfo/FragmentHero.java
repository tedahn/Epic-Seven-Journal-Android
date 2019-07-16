package com.kumaduma.epicseveninfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kumaduma.epicseveninfo.Activity.HeroActivity;
import com.kumaduma.epicseveninfo.DataManager.HeroManager;
import com.kumaduma.epicseveninfo.Model.SimpleHero;
import com.kumaduma.epicseveninfo.Model.SimpleHeroTier;
import com.kumaduma.epicseveninfo.RecyclerView.MainHeroListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FragmentHero extends Fragment implements OnItemClickListener {

    private static final String TAG = "FragmentHero";
    RecyclerView mRecyclerView ;
    List<SimpleHeroTier> heroTierList = HeroManager.getInstance().getHeroTierList();
    MainHeroListAdapter mainHeroListAdapter;
    String url;
    FloatingActionButton myFab;

    Comparator<SimpleHeroTier> byNameAsc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->o1.getName().compareTo(o2.getName());
    Comparator<SimpleHeroTier> byNameDesc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->o2.getName().compareTo(o1.getName());
    Comparator<SimpleHeroTier> byRarityAsc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->o1.getRarity() - o2.getRarity();
    Comparator<SimpleHeroTier> byRarityDesc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->o2.getRarity() - o1.getRarity();
    Comparator<SimpleHeroTier> byPvPAsc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->Double.compare(o1.getPvpAvgAsDouble(), o2.getPvpAvgAsDouble());
    Comparator<SimpleHeroTier> byPvPDesc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->Double.compare(o2.getPvpAvgAsDouble(), o1.getPvpAvgAsDouble());
    Comparator<SimpleHeroTier> byPvEAsc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->Double.compare(o1.getPveAvgAsDouble(), o2.getPveAvgAsDouble());
    Comparator<SimpleHeroTier> byPvEDesc =
            (SimpleHeroTier o1, SimpleHeroTier o2)->Double.compare(o2.getPveAvgAsDouble(), o1.getPveAvgAsDouble());
    Map<Integer, Comparator<SimpleHeroTier>> comparatorMap  = new HashMap<Integer, Comparator<SimpleHeroTier>>() {{
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
        url = bundle.getString("assetUrl");
        mainHeroListAdapter = new MainHeroListAdapter(view.getContext(), heroTierList, this, url);
        mRecyclerView = view.findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), OrientationHelper.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(mainHeroListAdapter);


        return view;
    }

    @Override
    public void onItemClick(int position, String type) {
        Intent intent = new Intent(getContext(), HeroActivity.class);
        String id = mainHeroListAdapter.getList().get(position).getFileId();
        intent.putExtra("hero_id", id);
        startActivity(intent);
    }

    public void updateFilter(String searchText, int orderByPosition, ArrayList<String> filRarity, ArrayList<String> filClass, ArrayList<String> filElement, ArrayList<String> filZodiac, ArrayList<String> filBuff, ArrayList<String> filDebuff){
        List<SimpleHeroTier> newList = new ArrayList<>();
        for (SimpleHeroTier hr: heroTierList) {
            if (searchText.isEmpty() || hr.getName().toLowerCase().contains(searchText)){
                if (filRarity.isEmpty() || filRarity.contains(Integer.toString(hr.getRarity()))) {
                    if (filClass.isEmpty() || filClass.contains(hr.getClassType().toLowerCase())) {
                        if (filElement.isEmpty() || filElement.contains(hr.getElement().toLowerCase())) {
                            if (filZodiac.isEmpty() || filZodiac.contains(hr.getZodiac().toLowerCase())) {
                                if (filBuff.isEmpty() || hr.getBuffs().containsAll(filBuff)) {
                                    if (filDebuff.isEmpty() || hr.getDebuffs().containsAll(filDebuff)) {
                                        newList.add(hr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(newList,comparatorMap.get(orderByPosition));
        mainHeroListAdapter.updateList(newList);
    }
}
