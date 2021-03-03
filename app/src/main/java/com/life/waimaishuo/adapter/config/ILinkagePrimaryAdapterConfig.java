package com.life.waimaishuo.adapter.config;

import android.content.Context;
import android.view.View;

import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;

public class ILinkagePrimaryAdapterConfig implements com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig {

    /**
     * 选中item改变
     * @param position
     */
    public void onItemSelectedChange(int position){}

    /**
     * titles[] index 0:title 1:带ImgUrl
     * 可实现次方法 或 实现下边onBindViewHolder（... String title）方法
     * @param holder
     * @param selected
     * @param title
     */
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String[] title){
        onBindViewHolder(holder,selected,title[0]);
    }

    @Override
    public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {

    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public int getGroupTitleViewId() {
        return 0;
    }

    @Override
    public int getRootViewId() {
        return 0;
    }

    @Override
    public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {

    }

}
