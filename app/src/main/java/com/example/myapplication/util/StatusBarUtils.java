package com.example.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import com.example.base.views.UiAdapterLinearLayout;
import com.example.myapplication.mvvm.view.activity.BaseActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;

public class StatusBarUtils {

    public static int STATUS_BAR_MODE_LIGHT = 0;
    public static int STATUS_BAR_MODE_DARK = 1;

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
     * 需要布局最外层布局为LinerLayout且竖直排序
     * 通过添加占位view实现
     * 配合状态栏透明，实现渐变式状态栏
     */
    public static void fitStatusBarHeight(BaseActivity baseActivity){    // FIXME: 2020/12/7 添加占位后，重新计算布局高度
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseActivity);

        /*//设置 paddingTop
        ViewGroup rootView = (ViewGroup) baseActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)rootView.getLayoutParams();
        layoutParams.topMargin = statusBarHeight;
        rootView.setLayoutParams(layoutParams);*/
        //根布局添加占位状态栏
        ViewGroup decorView = (ViewGroup) baseActivity.getWindow().getDecorView();
        View statusBarView = new View(baseActivity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusBarView.setBackgroundColor(Color.TRANSPARENT);
        decorView.addView(statusBarView,0,lp);
/*        //添加占位view
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
     * 同上
     * @param baseFragment
     */
    public static void fitStatusBarHeight(BaseFragment baseFragment){
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseFragment.getContext());
//
        ViewGroup rootView = (ViewGroup) baseFragment.getRootView();

//        根布局添加占位状态栏
        View statusBarView = new View(baseFragment.getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusBarView.setBackgroundColor(Color.TRANSPARENT);
        rootView.addView(statusBarView,0,lp);
        //设置 paddingTop
//        ViewGroup rootView = (ViewGroup) baseFragment.getRootView();
//
//        ViewGroup decorView = (ViewGroup) baseFragment.getWindow().getDecorView();
//        View statusBarView = new View(baseFragment);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                statusBarHeight);
//        statusBarView.setBackgroundColor(Color.TRANSPARENT);
//        decorView.addView(statusBarView, lp);

//        ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
//        layoutParams.height -= statusBarHeight;
//        rootView.setPadding(0,statusBarHeight,0,0);
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

    /**
     * 隐藏状态栏
     * @param window
     */
    public static void hideStatusBar(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
    }

    /**
     * 显示状态栏
     * @param window
     */
    public static void showStatusBar(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
    }
}
