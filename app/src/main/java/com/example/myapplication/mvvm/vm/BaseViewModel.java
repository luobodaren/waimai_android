package com.example.myapplication.mvvm.vm;

import android.view.View;

import com.example.myapplication.mvvm.model.BaseModel;

public abstract class BaseViewModel {

    private BaseModel mMode;

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
