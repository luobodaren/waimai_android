package com.example.myapplication.listener;

import android.view.ViewGroup;

import com.example.myapplication.bean.LinkageGroupedItemWaimaiType;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;

public interface OnSecondaryItemClickListener {

    void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item);

}
