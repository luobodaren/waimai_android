package com.example.myapplication;

import android.app.Application;

import com.example.base.IComponentApplication;
public class MyApplication extends Application {

    private static final String[] MODULESLIST =
            {"com.example.base.BaseApplication","com.example.thirdlib.ThirdApplication"};


    @Override
    public void onCreate() {
        super.onCreate();

        //Module类的APP初始化
        modulesApplicationInit();
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
