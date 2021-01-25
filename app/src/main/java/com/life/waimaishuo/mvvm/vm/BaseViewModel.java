package com.life.waimaishuo.mvvm.vm;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.life.waimaishuo.mvvm.model.BaseModel;

public abstract class BaseViewModel  {  //extends ViewModel

    private boolean hasInit = false;
    protected BaseModel mMode;

    void setMode(BaseModel mode){
        this.mMode = mode;
    }

    /**
     * 初始化 在initView之前会被调用
     */
    public void init(){
        if(!hasInit){   //当一个viewModel被多个Fragment使用时，可控制只初始化一次
            hasInit = true;
            mMode = getModel();
            initData();
        }
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
