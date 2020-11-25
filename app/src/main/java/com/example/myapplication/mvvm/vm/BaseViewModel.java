package com.example.myapplication.mvvm.vm;

import android.view.View;

import com.example.myapplication.mvvm.model.BaseModel;

public abstract class BaseViewModel {

    private BaseModel mMode;

    void setMode(BaseModel mode){
        this.mMode = mode;
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 初始化
     */
    public void init(){
        mMode = getModel();
        initData();
    }

    /**
     * 初始化model
     */
    public abstract BaseModel getModel();

    /**
     * 设置数据
     */
    public abstract void initData();

}
