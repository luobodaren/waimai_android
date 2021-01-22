package com.life.waimaishuo.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.life.waimaishuo.R;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseImageBanner;

/**
 * 简单的图片轮播
 * 由于适配屏幕的问题使得原有高宽比配置出现问题
 * 解决：不再使用高宽比，而是手动设置布局文件
 */
public class SimpleImageBanner extends BaseImageBanner<SimpleImageBanner> {

    int itemLayoutId = R.layout.xui_adapter_simple_image;

    public SimpleImageBanner(Context context) {
        super(context);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getItemLayoutId() {
        return itemLayoutId;
    }

    @Override
    protected int getImageViewId() {
        return R.id.iv_item;
    }

    @Override
    protected void loadingImageView(ImageView iv, BannerItem item) {
//        super.loadingImageView(iv, item);
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
