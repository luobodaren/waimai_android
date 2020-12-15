package com.life.base;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;
import com.xuexiang.xui.XUI;


public class BaseApplication implements IComponentApplication {
    @Override
    public void init(Application application) {
        LogUtil.d("BaseApplication 初始化");

        MultiDex.install(application);

        XUI.init(application); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

        UIUtils.getInstance(application);   //初始化UI适配工具
    }

    private void initXPage(Application application){

    }
}
