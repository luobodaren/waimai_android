package com.example.thirdlib;

import android.app.Application;

import com.example.base.IComponentApplication;
import com.example.base.utils.LogUtil;

public class ThirdApplication implements IComponentApplication {
    
    @Override
    public void init(Application application) {
        LogUtil.d("thirdApplication 初始化");
    }
    
}
