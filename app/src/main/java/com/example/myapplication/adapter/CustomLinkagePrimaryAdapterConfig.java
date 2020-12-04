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

package com.example.myapplication.adapter;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.base.utils.UIUtils;
import com.example.myapplication.R;
import com.example.myapplication.bean.ElemeGroupedItem;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;

import java.lang.ref.WeakReference;

/**
 * 自定义主菜单适配器
 *
 * @author xuexiang
 * @since 2019-11-25 17:17
 */
public class  CustomLinkagePrimaryAdapterConfig<T extends BaseGroupedItem.ItemInfo> implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemClickListener mItemClickListener;

    private WeakReference<LinkageRecyclerView<T>> mLinkageRecyclerView;

    public CustomLinkagePrimaryAdapterConfig(OnPrimaryItemClickListener itemClickListener, LinkageRecyclerView<T> linkageRecyclerView) {
        mItemClickListener = itemClickListener;
        mLinkageRecyclerView = new WeakReference<>(linkageRecyclerView);
    }

    public CustomLinkagePrimaryAdapterConfig setOnItemClickListner(OnPrimaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_linkage_primary;
    }

    @Override
    public int getGroupTitleViewId() {
        return R.id.tv_group;
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_group;
    }

    Drawable drawable = null;
    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
        if(drawable == null){
            drawable = mContext.getResources().getDrawable(R.drawable.sr_widght_vertical_bar);
            drawable.setBounds(0,0,
                    (int) (mContext.getResources().getDimensionPixelOffset(R.dimen.linkagePrimary_left_icon_width)
                            * UIUtils.getInstance(mContext).getHorValue()),
                    (int) (mContext.getResources().getDimensionPixelOffset(R.dimen.linkagePrimary_left_icon_height)
                            * UIUtils.getInstance(mContext).getHorValue()));
        }

        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title);

        int selectedPosition = mLinkageRecyclerView.get().getPrimaryAdapter().getSelectedPosition();

        if(selected){
            tvTitle.setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_selected ));

            tvTitle.setCompoundDrawablesRelative(drawable,null,null,null);
        }else{
            tvTitle.setBackground(mContext.getResources().getDrawable(R.color.linkage_primary_item_bg_default));
            tvTitle.setCompoundDrawablesRelative(null,null,null,null);
        }
        if(holder.getAdapterPosition() == (selectedPosition - 1)){  //上一个
            tvTitle.setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_before_select));
        }else if(holder.getAdapterPosition() == (selectedPosition + 1)){    //下一个
            tvTitle.setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_after_select));
        }
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
}
