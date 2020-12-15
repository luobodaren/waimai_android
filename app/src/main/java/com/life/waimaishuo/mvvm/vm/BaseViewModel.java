package com.life.waimaishuo.mvvm.vm;

import com.life.waimaishuo.mvvm.model.BaseModel;

public abstract class BaseViewModel {

    protected BaseModel mMode;

    void setMode(BaseModel mode){
        this.mMode = mode;
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
