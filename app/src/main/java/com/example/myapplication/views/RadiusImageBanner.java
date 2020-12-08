package com.example.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;

import com.example.myapplication.R;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.widget.banner.widget.banner.base.BaseImageBanner;

public class RadiusImageBanner extends BaseImageBanner<RadiusImageBanner> {

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
        return R.layout.widget_radius_image;
    }

    /**
     * @return 图片控件的ID
     */
    @Override
    protected int getImageViewId() {
        return R.id.riv;
    }

    @Override
    public int getItemWidth() {
        //需要距离边一点距离
        return super.getItemWidth() - DensityUtils.dp2px(getContext(), 32);
    }

}
