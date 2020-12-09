package com.example.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.xuexiang.xui.widget.banner.widget.banner.BannerItem;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseImageBanner;

public class MySimpleImageBanner extends BaseImageBanner<MySimpleImageBanner> {
    public MySimpleImageBanner(Context context) {
        super(context);
    }

    public MySimpleImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.adapter_simple_image;
    }

    @Override
    protected int getImageViewId() {
        return R.id.iv;
    }

    @Override
    protected void loadingImageView(ImageView iv, BannerItem item) {
        String imgUrl = item.imgUrl;
        Glide.with(getContext()).load(imgUrl).placeholder(R.drawable.ic_waimai_brand).centerCrop().into(iv);
    }
}
