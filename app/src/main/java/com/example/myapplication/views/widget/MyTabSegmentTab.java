package com.example.myapplication.views.widget;

import android.graphics.drawable.Drawable;

import com.example.base.utils.UIUtils;
import com.example.myapplication.MyApplication;
import com.xuexiang.xui.widget.tabbar.TabSegment;

public class MyTabSegmentTab extends TabSegment.Tab {
    
    public MyTabSegmentTab(CharSequence text) {
        super(text);
    }

    public MyTabSegmentTab(Drawable normalIcon, Drawable selectedIcon, CharSequence text, boolean dynamicChangeIconColor) {
        this(normalIcon, selectedIcon, text, dynamicChangeIconColor,true);
    }

    public MyTabSegmentTab(Drawable normalIcon, Drawable selectedIcon, CharSequence text, boolean dynamicChangeIconColor, boolean setIntrinsicSize) {
        super(normalIcon, selectedIcon, text, dynamicChangeIconColor, setIntrinsicSize);
        if(setIntrinsicSize){
            UiAdapter(normalIcon);
            UiAdapter(selectedIcon);
        }
    }
    
    private void UiAdapter(Drawable drawable){
        UIUtils.autoAdapterDrawable(MyApplication.getMyApplication(),drawable);
    }
    
}
