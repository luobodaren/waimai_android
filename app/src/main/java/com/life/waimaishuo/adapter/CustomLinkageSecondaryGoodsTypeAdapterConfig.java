/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.life.waimaishuo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;
import com.life.waimaishuo.bean.ui.LinkageGroupedItemGoodsType;
import com.life.waimaishuo.listener.OnSecondaryItemClickListener;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;

import java.lang.ref.WeakReference;

public class CustomLinkageSecondaryGoodsTypeAdapterConfig implements ILinkageSecondaryAdapterConfig<LinkageGroupedItemGoodsType.ItemInfo> {

    private static final int SPAN_COUNT = 3;

    private Context mContext;

    private OnSecondaryItemClickListener mItemClickListener;

    private WeakReference<View> selectView;

    public CustomLinkageSecondaryGoodsTypeAdapterConfig(OnSecondaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public CustomLinkageSecondaryGoodsTypeAdapterConfig setOnItemClickListner(OnSecondaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getGridLayoutId() {
        return R.layout.adapter_linkage_secondary_grid;
    }

    @Override
    public int getLinearLayoutId() {
        return R.layout.adapter_linkage_secondary_linear;
    }

    @Override
    public int getHeaderLayoutId() {
        return R.layout.adapter_secondary_header_goods_type;
    }

    @Override
    public int getFooterLayoutId() {
        return R.layout.adapter_linkage_empty_footer;
    }

    @Override
    public int getHeaderTextViewId() {
        return R.id.secondary_header;
    }

    @Override
    public int getSpanCountOfGridMode() {
        return SPAN_COUNT;
    }

    @Override
    public void onBindViewHolder(final LinkageSecondaryViewHolder holder,
                                 final BaseGroupedItem<LinkageGroupedItemGoodsType.ItemInfo> item) {

        ((TextView) holder.getView(R.id.iv_goods_name)).setText(item.info.getTitle());
        Glide.with(mContext).load(R.mipmap.ic_food_all_subtype).into((ImageView) holder.getView(R.id.iv_goods_img));
//        Glide.with(mContext).load(item.info.getImgUrl()).into((ImageView) holder.getView(R.id.iv_goods_img)); // FIXME: 2020/12/15 暂时修改为读取本地图片

        ViewGroup viewGroup = holder.getView(R.id.iv_goods_item);
        viewGroup.setOnClickListener(v -> {
            if(selectView != null){
                selectView.get().setSelected(false);
                selectView.clear();
            }
            holder.itemView.setSelected(true);
            selectView = new WeakReference<>(holder.itemView);

            if (mItemClickListener != null) {
                mItemClickListener.onSecondaryItemClick(holder, viewGroup, item);
            }
        });
    }

    @Override
    public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                       BaseGroupedItem<LinkageGroupedItemGoodsType.ItemInfo> item) {
        ((TextView) holder.getView(R.id.secondary_header)).setText(item.header);
        holder.getView(R.id.secondary_header_right).setOnClickListener(v ->{
            if(mItemClickListener != null){
                mItemClickListener.onSecondaryHeadClick(holder,item);
            }
        });
    }

    @Override
    public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                       BaseGroupedItem<LinkageGroupedItemGoodsType.ItemInfo> item) {

    }

}
