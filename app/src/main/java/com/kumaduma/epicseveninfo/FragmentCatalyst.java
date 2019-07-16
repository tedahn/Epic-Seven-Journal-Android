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

import com.kumaduma.epicseveninfo.Activity.CatalystActivity;
import com.kumaduma.epicseveninfo.DataManager.CatalystManager;
import com.kumaduma.epicseveninfo.Model.Catalyst.Catalyst;
import com.kumaduma.epicseveninfo.RecyclerView.MainCatalystListAdapter;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentCatalyst extends Fragment implements OnItemClickListener {

    RecyclerView mRecyclerView ;
    List<Catalyst> catalystList = CatalystManager.getInstance().getCatalystList();
    MainCatalystListAdapter mainCatalystListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view;
        Bundle bundle = getArguments();

        view = inflater.inflate(R.layout.list_content, container, false);

        mainCatalystListAdapter = new MainCatalystListAdapter(getContext(), catalystList, this, bundle.getString("assetUrl"));
        mRecyclerView = view.findViewById(R.id.main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setAdapter(mainCatalystListAdapter);


        return view;
    }

    @Override
    public void onItemClick(int position, String type) {
        //Toast.makeText(this,"Catalyst " + mainCatalystListAdapter.getList().get(position).getName() + " Pressed", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), CatalystActivity.class);
        String id = mainCatalystListAdapter.getList().get(position).getFileId();
        intent.putExtra("catalyst_id", id);

        startActivity(intent);

    }

    public void updateFilter(String searchText){
        List<Catalyst> newList = new ArrayList<>();
        for (Catalyst ca : catalystList)
            if (searchText.isEmpty() || ca.getName().toLowerCase().contains(searchText)) newList.add(ca);
        mainCatalystListAdapter.updateList(newList);
    }
}
