package com.cegrano.android.test;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by cegrano on 16/3/6.
 * 空adapter，某些情景用来释放资源
 */
public class EmptyAdapter extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
