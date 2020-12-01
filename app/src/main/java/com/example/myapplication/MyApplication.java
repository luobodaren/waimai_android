package com.example.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.base.IComponentApplication;
import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xpage.model.PageInfo;

import java.util.List;

public class MyApplication extends Application {

    private static final String[] MODULESLIST =
            {"com.example.base.BaseApplication","com.example.thirdlib.ThirdApplication"};

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initPage();

        //Module类的APP初始化
        modulesApplicationInit();
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
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
