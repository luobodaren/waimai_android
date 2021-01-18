package com.life.waimaishuo.listener;

import android.view.View;
import android.view.ViewGroup;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.bean.ui.LinkageGroupedItemMallShopClassification;
import com.life.waimaishuo.bean.ui.LinkageGroupedItemShopGoods;

public interface OnSecondaryMallShopClassificationItemClickListener {

    void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemMallShopClassification.ItemInfo> item);
    void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGroupedItemMallShopClassification.ItemInfo> item);
    void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageGroupedItemMallShopClassification.ItemInfo> item);
}
