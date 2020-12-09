package com.example.myapplication.listener;

import android.view.View;

import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;

public interface OnPrimaryItemClickListener {
    /**
     * we suggest you get position by holder.getAdapterPosition
     *
     * @param holder primaryHolder
     * @param view   view
     * @param title  groupTitle
     */
    void onPrimaryItemClick(LinkagePrimaryViewHolder holder, View view, String title);
}
