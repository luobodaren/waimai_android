package com.life.waimaishuo;

import android.app.Application;
import android.content.Context;

import com.life.base.IComponentApplication;

import com.life.base.utils.LogUtil;
import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.model.PageInfo;

import java.util.List;

public class MyApplication extends Application {

    private static final String[] MODULESLIST =
            {"com.life.base.BaseApplication","com.life.thirdlib.ThirdApplication"};

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initPage();

        //Module类的APP初始化
        modulesApplicationInit();
        // TODO: 2021/2/3  mmkv获取本地备份数据进行初始化
        //mmkv获取本地备份数据进行初始化
        //Global UIUtil
    }

    public static MyApplication getMyApplication(){
        return myApplication;
    }

    public void initPage(){
        PageConfig.getInstance()
                .setPageConfiguration(new PageConfiguration() {
                    @Override
                    public List<PageInfo> registerPages(Context context) {
                        return AppPageConfig.getInstance().getPages();
                    }
                })
                .debug("PageLog")       //开启调试
                .setContainActivityClazz(XPageActivity.class) //设置默认的容器Activity
                .enableWatcher(false)   //设置是否开启内存泄露监测
                .init(this);            //初始化页面配置
    }

    private void modulesApplicationInit(){
        for (String moduleImpl : MODULESLIST){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication){
                    ((IComponentApplication) obj).init(this);
                }
            } catch (ClassNotFoundException e) {
                LogUtil.e("model application 初始化方法调用失败   1");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LogUtil.e("model application 初始化方法调用失败   2");
                e.printStackTrace();
            } catch (InstantiationException e) {
                LogUtil.e("model application 初始化方法调用失败   3");
                e.printStackTrace();
            }
        }
    }

}
