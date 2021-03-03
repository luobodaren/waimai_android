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

package com.life.waimaishuo.adapter.config;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.life.base.utils.UIUtils;
import com.life.waimaishuo.R;
import com.life.waimaishuo.listener.OnPrimaryItemClickListener;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.life.waimaishuo.views.MyLinkageRecyclerView;

import java.lang.ref.WeakReference;

public class CustomLinkagePrimaryShopGoodsAdapterConfig<T extends BaseGroupedItem.ItemInfo> extends ILinkagePrimaryAdapterConfig {

    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;
    private Context mContext;
    private OnPrimaryItemClickListener mItemClickListener;

    private WeakReference<MyLinkageRecyclerView<T>> mLinkageRecyclerView;

    public CustomLinkagePrimaryShopGoodsAdapterConfig(OnPrimaryItemClickListener itemClickListener, MyLinkageRecyclerView<T> linkageRecyclerView) {
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
    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String[] title) {
        TextView tvTitle = ((TextView) holder.mGroupTitle);
        tvTitle.setText(title[0]);
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

        setDrawableTop(title[1], tvTitle);

        View viewSelectMark = holder.mLayout.findViewById(R.id.iv_selected);
        if(selected){
            holder.mLayout.findViewById(R.id.layout_content).setBackground(mContext.getResources().getDrawable(R.drawable.sr_linkage_primary_item_selected));
            TextPaint tp = tvTitle.getPaint();
            tp.setFakeBoldText(true);
            if(viewSelectMark.getVisibility() == View.GONE){
                viewSelectMark.setVisibility(View.VISIBLE);
            }
        }else{
            holder.mLayout.findViewById(R.id.layout_content).setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
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

    int drawableSize;
    /**
     * 获得groupIcon
     * @return
     */
    private void setDrawableTop(String url, TextView tvTitle){
        if(url != null && !"".equals(url)){
            Glide.with(mContext)
                    .load(url)
                    .into(new ImageViewTarget<Drawable>(new ImageView(mContext)) {
                        @Override
                        protected void setResource(@Nullable Drawable resource) {   // TODO: 2021/3/2 判断是否再主线程？
                            if(drawableSize == -1){
                                drawableSize = (int)UIUtils.getInstance().scalePx(
                                        mContext.getResources().getDimensionPixelSize(R.dimen.linkagePrimary_top_icon_size));
                            }
                            resource.setBounds(0,0,drawableSize, drawableSize);
                            tvTitle.setCompoundDrawablesRelative(null,resource,null,null);
                        }
                    });
        }
    }

    @Override
    public void onItemSelectedChange(int position) {
        if (mItemClickListener != null) {
            mItemClickListener.onPrimaryItemChange(position);
        }
    }
}
