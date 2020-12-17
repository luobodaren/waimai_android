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
import android.view.View;
import android.widget.TextView;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
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
public class CustomLinkagePrimaryShopGoodsAdapterConfig<T extends BaseGroupedItem.ItemInfo> implements ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemClickListener mItemClickListener;

    private WeakReference<LinkageRecyclerView<T>> mLinkageRecyclerView;

    public CustomLinkagePrimaryShopGoodsAdapterConfig(OnPrimaryItemClickListener itemClickListener, LinkageRecyclerView<T> linkageRecyclerView) {
        mItemClickListener = itemClickListener;
        mLinkageRecyclerView = new WeakReference<>(linkageRecyclerView);
    }

    public CustomLinkagePrimaryShopGoodsAdapterConfig setOnItemClickListner(OnPrimaryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_linkage_primary_shop_goods;
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
            drawable = mContext.getResources().getDrawable(R.drawable.sr_widght_vertical_bar);
        }

        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title);
        int selectedPosition = mLinkageRecyclerView.get().getPrimaryAdapter().getSelectedPosition();
        if(maxPosition == -1){
            maxPosition = mLinkageRecyclerView.get().getPrimaryAdapter().getItemCount() - 1;
        }

        View space = holder.mLayout.findViewById(R.id.layout_room_for_shopping_cart_bar);
        if(maxPosition == holder.getAdapterPosition()){   //最后一个腾出购物车空间
            if(space.getVisibility() == View.GONE){
                space.setVisibility(View.VISIBLE);
            }
        }else{
            if(space.getVisibility() == View.VISIBLE){
                space.setVisibility(View.GONE);
            }
        }

        Drawable drawableTop = getDrawableTop();

        View viewSelectMark = holder.mLayout.findViewById(R.id.iv_selected);
        if(selected){
            holder.mLayout.findViewById(R.id.layout_content).setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_selected));
            tvTitle.setCompoundDrawablesRelative(drawable,drawableTop,null,null);
            TextPaint tp = tvTitle.getPaint();
            tp.setFakeBoldText(true);
            if(viewSelectMark.getVisibility() == View.GONE){
                viewSelectMark.setVisibility(View.VISIBLE);
            }
        }else{
            holder.mLayout.findViewById(R.id.layout_content).setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            tvTitle.setCompoundDrawablesRelative(null,drawableTop,null,null);
            TextPaint tp = tvTitle.getPaint();
            tp.setFakeBoldText(false);
            holder.mLayout.findViewById(R.id.iv_selected).setVisibility(View.GONE);
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

    Drawable groupIcon;
    /**
     * 获得groupIcon
     * @return
     */
    private Drawable getDrawableTop(){  // TODO: 2020/12/9 此处应该从服务获取icon?
        if(groupIcon == null){
            groupIcon = mContext.getResources().getDrawable(R.drawable.ic_hot_drinks);
            int drawableSize = (int)UIUtils.getInstance(mContext).scalePx(
                    mContext.getResources().getDimensionPixelSize(R.dimen.linkagePrimary_top_icon_size));
            groupIcon.setBounds(0,0,drawableSize, drawableSize);
        }
        return groupIcon;
    }

}
