package com.life.waimaishuo.listener;

import android.view.View;
import android.view.ViewGroup;

import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.bean.LinkageGroupedItemShopGoods;

public interface OnSecondaryShopGoodsItemClickListener {

    void onSecondaryItemClick(LinkageSecondaryViewHolder holder, ViewGroup view, BaseGroupedItem<LinkageGroupedItemShopGoods.ItemInfo> item);
    void onSecondaryHeadClick(LinkageSecondaryHeaderViewHolder holder, BaseGroupedItem<LinkageGroupedItemShopGoods.ItemInfo> item);
    void onSecondaryChildClick(LinkageSecondaryViewHolder holder, View view, BaseGroupedItem<LinkageGroupedItemShopGoods.ItemInfo> item);
}
