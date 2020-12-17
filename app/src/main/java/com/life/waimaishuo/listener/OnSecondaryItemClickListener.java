package com.life.waimaishuo.listener;

import android.view.ViewGroup;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.life.waimaishuo.bean.LinkageGroupedItemWaimaiType;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;

public interface OnSecondaryItemClickListener {

    void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item);
    void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGroupedItemWaimaiType.ItemInfo> item);
}
