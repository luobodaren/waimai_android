package com.example.base;

import android.app.Application;

import com.example.base.utils.LogUtil;

public class BaseApplication implements IComponentApplication {
    @Override
    public void init(Application application) {
        LogUtil.d("BaseApplication 初始化");
    }
}
