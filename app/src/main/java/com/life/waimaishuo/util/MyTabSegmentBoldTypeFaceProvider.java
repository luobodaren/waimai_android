package com.life.waimaishuo.util;

import android.graphics.Typeface;

import androidx.annotation.Nullable;

import com.xuexiang.xui.widget.tabbar.TabSegment;

/**
 * 共TabSegment设置字体样式使用
 * 若改变该类的位置，记得再xml引用的地方也进行修改
 */
public class MyTabSegmentBoldTypeFaceProvider implements TabSegment.TypefaceProvider {

    @Override
    public boolean isNormalTabBold() {
        return false;
    }

    @Override
    public boolean isSelectedTabBold() {
        return true;
    }

    @Nullable
    @Override
    public Typeface getTypeface() {
        return null;
    }

}
