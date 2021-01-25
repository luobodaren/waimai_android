package com.life.waimaishuo.views;

import android.graphics.drawable.Drawable;

import com.life.base.utils.UIUtils;
import com.life.waimaishuo.MyApplication;
import com.xuexiang.xui.widget.tabbar.TabSegment;

public class MyTabSegmentTab extends TabSegment.Tab {
    
    public MyTabSegmentTab(CharSequence text) {
        super(text);
    }

    public MyTabSegmentTab(Drawable normalIcon, Drawable selectedIcon, CharSequence text, boolean dynamicChangeIconColor) {
        this(normalIcon, selectedIcon, text, dynamicChangeIconColor,false);
    }

    public MyTabSegmentTab(Drawable normalIcon, Drawable selectedIcon, CharSequence text, boolean dynamicChangeIconColor, boolean setIntrinsicSize) {
        super(normalIcon, selectedIcon, text, dynamicChangeIconColor, setIntrinsicSize);
        if(setIntrinsicSize){
            UiAdapter(normalIcon);
            UiAdapter(selectedIcon);
        }
    }
    
    private void UiAdapter(Drawable drawable){
        UIUtils.getInstance().autoAdapterDrawable(drawable);
    }

}
