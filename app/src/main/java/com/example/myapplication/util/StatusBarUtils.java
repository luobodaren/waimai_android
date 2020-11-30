package com.example.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import com.example.myapplication.mvvm.view.activity.BaseActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;

public class StatusBarUtils {


    /**
     * 默认颜色值0x40000000
     * @param baseActivity
     */
    public static void translucent(BaseActivity baseActivity) {
        com.xuexiang.xui.utils.StatusBarUtils.translucent(baseActivity);
    }

    public static void translucent(BaseActivity baseActivity,@ColorInt int color) {
        com.xuexiang.xui.utils.StatusBarUtils.translucent(baseActivity,color);
    }

    /**
     * 通过添加padding的方式，布局下移
     * 配合状态栏透明，实现渐变式状态栏
     */
    public static void fitStatusBarHight(BaseActivity baseActivity){
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseActivity);

        //设置 paddingTop
        ViewGroup rootView = (ViewGroup) baseActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, statusBarHeight, 0, 0);
        /*
        //添加占位view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            baseActivity.getWindow().setStatusBarColor(color);
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) baseActivity.getWindow().getDecorView();
            View statusBarView = new View(baseActivity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight);
            statusBarView.setBackgroundColor(color);
            decorView.addView(statusBarView, lp);
        }*/
    }

    /**
     * 通过添加padding的方式，布局下移
     * 配合状态栏透明，实现渐变式状态栏
     */
    public static void fitStatusBarHight(BaseFragment baseFragment){
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseFragment.getContext());

        //设置 paddingTop
        ViewGroup rootView = (ViewGroup) baseFragment.getRootView();
        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
        layoutParams.height -= statusBarHeight;
        rootView.setPadding(0,statusBarHeight,0,0);
        /*ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        marginLayoutParams.topMargin = statusBarHeight;
        rootView.setLayoutParams(marginLayoutParams);*/
    }


    public static void setStatusBarLightMode(Activity activity) {
        com.xuexiang.xui.utils.StatusBarUtils.setStatusBarLightMode(activity);
    }

    public static void setStatusBarDarkMode(Activity activity) {
        com.xuexiang.xui.utils.StatusBarUtils.setStatusBarDarkMode(activity);
    }

    public static void setStatusBarColor(Activity activity,@ColorInt int color) {
        com.xuexiang.xui.utils.StatusBarUtils.translucent(activity,color);
    }
}
