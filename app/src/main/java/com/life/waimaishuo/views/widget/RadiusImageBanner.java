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

package com.life.waimaishuo.views.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseImageBanner;

/**
 * 带圆角的图片轮播
 */
public class RadiusImageBanner extends BaseImageBanner<RadiusImageBanner> {

    private int itemLayoutId = R.layout.adapter_banner_image_item_buyers_show;

    public RadiusImageBanner(Context context) {
        super(context);
    }

    public RadiusImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadiusImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @return 轮播布局的ID
     */
    @Override
    protected int getItemLayoutId() {
        return itemLayoutId;
    }

    /**
     * @return 图片控件的ID
     */
    @Override
    protected int getImageViewId() {
        return R.id.iv_item;
    }

    @Override
    public int getItemWidth() {
        //需要距离边一点距离
        return super.getItemWidth() - DensityUtils.dp2px(getContext(), 32);
    }

    @Override
    protected void loadingImageView(ImageView iv, BannerItem item) {
        super.loadingImageView(iv, item);
        Glide.with(iv.getContext())
                .load(item.getImgUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_waimai_brand)
                .into(iv);
    }

    public void setItemLayoutId(@LayoutRes int itemLayoutId) {
        this.itemLayoutId = itemLayoutId;
    }
}
