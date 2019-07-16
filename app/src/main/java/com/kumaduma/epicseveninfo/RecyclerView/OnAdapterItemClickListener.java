package com.kumaduma.epicseveninfo.RecyclerView;

import android.support.v7.widget.RecyclerView;

public interface OnAdapterItemClickListener {
    void onItemClick(int position, String type, int adapterId);
}
