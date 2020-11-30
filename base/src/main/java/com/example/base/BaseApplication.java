package com.example.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.base.utils.LogUtil;
import com.example.base.utils.UIUtils;
import com.xuexiang.xpage.BasePageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xui.XUI;

import java.util.List;

public class BaseApplication implements IComponentApplication {
    @Override
    public void init(Application application) {
        LogUtil.d("BaseApplication 初始化");

        MultiDex.install(application);

        XUI.init(application); //初始化UI框架
        XUI.debug(true);  //开启UI框架调试日志

        initXPage(application);

        UIUtils.getInstance(application);   //初始化UI适配工具
    }

    private void initXPage(Application application){
        PageConfig.getInstance()
                .setPageConfiguration(new PageConfiguration() {
                    @Override
                    public List<PageInfo> registerPages(Context context) {
                        return BasePageConfig.getInstance().getPages();
                    }
                })
                .debug("PageLog")       //开启调试
                .setContainActivityClazz(XPageActivity.class) //设置默认的容器Activity
                .enableWatcher(false)   //设置是否开启内存泄露监测
                .init(application);            //初始化页面配置
    }
}
