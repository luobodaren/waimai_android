package com.life.thirdlib;

import android.app.Application;

import com.life.base.IComponentApplication;
import com.life.base.utils.LogUtil;

public class ThirdApplication implements IComponentApplication {
    
    @Override
    public void init(Application application) {
        LogUtil.d("thirdApplication 初始化");
    }
    
}
