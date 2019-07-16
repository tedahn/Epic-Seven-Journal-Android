package com.kumaduma.epicseveninfo.Drawer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.squareup.picasso.Picasso;
import com.kumaduma.epicseveninfo.R;

import java.util.ArrayList;

public class FilterDrawerListAdapter extends RecyclerView.Adapter<FilterDrawerListViewHolder>{

    private static final String TAG = "FilterDrawerListAdapter";
    private ArrayList<FilterDrawerItem> dataSet;
    private Context c;
    private boolean textless;
    private boolean isIconOnly;

    public FilterDrawerListAdapter(Context c, ArrayList<FilterDrawerItem> dataSet, boolean textless, boolean isIconOnly){
        this.c = c;
        this.dataSet = dataSet;
        this.textless = textless;
        this.isIconOnly = isIconOnly;

    }

    @Override
    @NonNull public FilterDrawerListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (textless) {
            View view = LayoutInflater.from(c).inflate(R.layout.filter_drawer_list_item_textless
                    , viewGroup, false);
            return new FilterDrawerListViewHolder(view);
        } else if (isIconOnly){
            View view = LayoutInflater.from(c).inflate(R.layout.filter_drawer_list_item_icons
                    , viewGroup, false);
            return new FilterDrawerListViewHolder(view);
        } else {
            View view = LayoutInflater.from(c).inflate(R.layout.filter_drawer_list_item
                    , viewGroup, false);
            return new FilterDrawerListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FilterDrawerListViewHolder filterViewHolder, int i) {
        FilterDrawerItem filter = dataSet.get(i);

        if (filter.hasUrl()){
            int resId = c.getResources().getIdentifier(filter.getTagText(), "drawable", c.getPackageName());
            resId = (resId == 0) ? R.drawable.ic_broken_image_black_24dp : resId;
            Picasso.get().load(filter.getIconUrl())
                    .placeholder(resId)
                    .error(resId)
                    .into(filterViewHolder.icon);
        } else {
            filterViewHolder.icon.setImageResource(filter.getIcon());
        }

        filterViewHolder.name.setText(filter.getText());
        filterViewHolder.checkBox.setChecked(filter.isSelected());
        filterViewHolder.checkBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)->{
            filter.setSelected(isChecked);
        });
        filterViewHolder.setOnClickListener((View view) -> {
            if (!filterViewHolder.checkBox.isChecked()) {
                filterViewHolder.checkBox.setChecked(true);
            } else {
                filterViewHolder.checkBox.setChecked(false);
            }
            filterViewHolder.checkBox.callOnClick();
        });
    }
    @Override
    public int getItemCount() { return dataSet.size(); }

    public ArrayList<String> getCheckedSet(){
        ArrayList<String> tempList = new ArrayList<>();
        for (FilterDrawerItem item : dataSet)
            if (item.isSelected()) tempList.add(item.getTagText());
        return tempList;
    }

    public void deselctAll(){
        for (FilterDrawerItem item : dataSet)
            item.setSelected(false);
        this.notifyDataSetChanged();
    }

}