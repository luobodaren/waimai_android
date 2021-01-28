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
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;

import java.lang.ref.WeakReference;

public class CustomLinkagePrimaryMallShopClassificationAdapterConfig<T extends BaseGroupedItem.ItemInfo> implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemClickListener mItemClickListener;

    private WeakReference<LinkageRecyclerView<T>> mLinkageRecyclerView;

    public CustomLinkagePrimaryMallShopClassificationAdapterConfig(OnPrimaryItemClickListener itemClickListener, LinkageRecyclerView<T> linkageRecyclerView) {
        mItemClickListener = itemClickListener;
        mLinkageRecyclerView = new WeakReference<>(linkageRecyclerView);
    }

    public CustomLinkagePrimaryMallShopClassificationAdapterConfig setOnItemClickListner(OnPrimaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_linkage_primary_mall_shop_classification;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    int maxPosition = -1;
    Drawable drawable = null;
    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        if(drawable == null){
            drawable = mContext.getResources().getDrawable(R.drawable.sr_widget_vertical_bar_black);
        }

        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title);
        int selectedPosition = mLinkageRecyclerView.get().getPrimaryAdapter().getSelectedPosition();
        if(maxPosition == -1){
            maxPosition = mLinkageRecyclerView.get().getPrimaryAdapter().getItemCount() - 1;
        }

        View viewSelectMark = holder.mLayout.findViewById(R.id.iv_selected);
        if(selected){
            holder.mLayout.findViewById(R.id.layout_content).setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_selected));
            tvTitle.setTextColor(tvTitle.getContext().getResources().getColor(R.color.text_deep_black));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.getInstance().scalePx(30));
            TextPaint tp = tvTitle.getPaint();
            tp.setFakeBoldText(true);
            if(viewSelectMark.getVisibility() == View.GONE){
                viewSelectMark.setVisibility(View.VISIBLE);
            }
        }else{
            holder.mLayout.findViewById(R.id.layout_content).setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            tvTitle.setTextColor(tvTitle.getContext().getResources().getColor(R.color.text_normal));
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.getInstance().scalePx(28));
            TextPaint tp = tvTitle.getPaint();
            tp.setFakeBoldText(false);
            if(viewSelectMark.getVisibility() == View.VISIBLE){
                viewSelectMark.setVisibility(View.GONE);
            }
        }

//        if(holder.getAdapterPosition() == (selectedPosition - 1)){  //上一个
//            holder.mLayout.setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_before_select));
//        }else if(holder.getAdapterPosition() == (selectedPosition + 1)){    //下一个
//            holder.mLayout.setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_after_select));
//        }
        tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
        tvTitle.setFocusable(selected);
        tvTitle.setFocusableInTouchMode(selected);
        tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
        if (mItemClickListener != null) {
            mItemClickListener.onPrimaryItemClick(holder, view, title);
        }
    }

}
