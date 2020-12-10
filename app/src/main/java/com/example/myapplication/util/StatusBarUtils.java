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

import com.example.base.utils.LogUtil;
import com.example.base.utils.UIUtils;
import com.example.base.views.UiAdapterLinearLayout;
import com.example.myapplication.R;
import com.example.myapplication.mvvm.view.activity.BaseActivity;
import com.example.myapplication.mvvm.view.fragment.BaseFragment;

import java.lang.reflect.Field;

public class StatusBarUtils {

    public static int STATUS_BAR_MODE_LIGHT = 0;
    public static int STATUS_BAR_MODE_DARK = 1;
    public static int STATUS_BAR_MODE_NO_HANDLE = 1;

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
     * 需要布局中存在id为my_ll_content_view的垂直LinearLayout
     * 通过添加占位view实现
     * 配合状态栏透明，实现渐变式状态栏
     */
    public static void fitStatusBarHeight(BaseActivity baseActivity){    // FIXME: 2020/12/7 添加占位后，重新计算布局高度
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseActivity);
        //根布局添加占位状态栏
        ViewGroup decorView = (ViewGroup) baseActivity.getWindow().getDecorView();
        ViewGroup contentView = decorView.findViewById(R.id.my_ll_content_view);

        if(contentView == null){
            LogUtil.e("Activity FitStatusBarHeight Fail，contentView == null");
            return;
        }

        View statusBarView = new View(baseActivity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusBarView.setBackgroundColor(Color.TRANSPARENT);
        //这里获取的为屏幕状态栏高度 不需要在绘制时被放大
        UIUtils.getInstance(baseActivity.context).setScaleTag(statusBarView);
        contentView.addView(statusBarView,0,lp);
    }

    /**
     * 同上
     * @param baseFragment
     */
    public static void fitStatusBarHeight(BaseFragment baseFragment){
        int statusBarHeight = com.xuexiang.xui.utils.StatusBarUtils.getStatusBarHeight(baseFragment.getContext());
        ViewGroup rootView = (ViewGroup) baseFragment.getRootView();
        ViewGroup contentView = rootView.findViewById(R.id.my_ll_content_view);

        if(contentView == null){
            LogUtil.e("Fragment FitStatusBarHeight Fail，contentView == null");
            return;
        }

//        根布局添加占位状态栏
        View statusBarView = new View(baseFragment.getContext());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusBarView.setBackgroundColor(Color.TRANSPARENT);
        //这里获取的为屏幕状态栏高度 不需要在绘制时被放大
        UIUtils.getInstance(baseFragment.getContext()).setScaleTag(statusBarView);
        contentView.addView(statusBarView,0,lp);
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
