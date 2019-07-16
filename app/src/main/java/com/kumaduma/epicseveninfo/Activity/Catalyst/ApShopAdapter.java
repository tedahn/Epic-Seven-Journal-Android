package com.kumaduma.epicseveninfo.Activity.Catalyst;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kumaduma.epicseveninfo.Model.Catalyst.ApShop;
import com.kumaduma.epicseveninfo.Model.Catalyst.Locations;
import com.kumaduma.epicseveninfo.R;
import com.kumaduma.epicseveninfo.RecyclerView.OnItemClickListener;

import java.util.List;

public class ApShopAdapter extends RecyclerView.Adapter<ApShopViewHolder> implements OnItemClickListener {

    private Context c;
    private List<ApShop> apshopList;
    private OnItemClickListener mOnItemClickListener;

    public ApShopAdapter(Context c, List<ApShop> apshopList, OnItemClickListener mOnItemClickListener){
        this.c = c;
        this.apshopList = apshopList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ApShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(c).inflate(R.layout.catalyst_apshop_rv_item
                , viewGroup, false);
        return new ApShopViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ApShopViewHolder apShopViewHolder, int i) {
        ApShop shop = apshopList.get(i);
        String chapter = shop.getChapter();
        apShopViewHolder.chapter.setText(chapter);
        String cost = ""+shop.getCost();
        apShopViewHolder.cost.setText(cost);
        String qt = "Quantity: "+shop.getQuantity();
        apShopViewHolder.quantity.setText(qt);
    }

    @Override
    public int getItemCount() {
        return apshopList.size();
    }

    @Override
    public void onItemClick(int position, String type) {

    }
}
