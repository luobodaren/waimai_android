package com.life.waimaishuo.listener;

import android.view.View;
import android.view.ViewGroup;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.bean.ui.LinkageMallShopClassificationGroupedItemInfo;

public interface OnSecondaryMallShopClassificationItemClickListener {

    void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item);
    void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item);
    void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageMallShopClassificationGroupedItemInfo> item);
}
