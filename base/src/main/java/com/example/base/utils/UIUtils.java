package com.example.base.utils;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class UIUtils {

    //标准值
    private static final float STANDRD_WIDTH = 750F;
    private static final float STANDRD_HEIGHT = 1624F;
    //实际值 保存MMKV中
    private static float displayMetricsWidth;
    private static float displayMetricsHeight;
    private static float scaledDensity;
    private static float density;
    //单例
    private static UIUtils instance;

    private UIUtils(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // TODO: 2020/11/24 从缓存中读取时间屏幕高宽值
        if(displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f
                || scaledDensity == 0.0f || density == 0.0f) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int systemBarHeight = getSystemBarHeight(context);
            // TODO: 2020/11/24 横竖屏切换时改变大小
            displayMetricsWidth = (float)displayMetrics.widthPixels;
            displayMetricsHeight = (float) displayMetrics.heightPixels - systemBarHeight;
            scaledDensity = displayMetrics.scaledDensity;
            density = displayMetrics.density;

            if(displayMetrics.widthPixels > displayMetrics.heightPixels){   //当前屏幕宽高
                //横屏
                displayMetricsWidth = (float) displayMetrics.heightPixels;
                displayMetricsHeight = (float) displayMetrics.widthPixels - systemBarHeight;
            }else{
                //竖屏
                displayMetricsWidth = (float)displayMetrics.widthPixels;
                displayMetricsHeight = (float) displayMetrics.heightPixels - systemBarHeight;
            }
        }
    }

    public synchronized static UIUtils getInstance(Context context){
        if (instance == null){
            instance = new UIUtils(context);
        }
        return instance;
    }

    /**
     * 获得横向缩放倍数
     * @return
     */
    public float getHorValue(){
        return ((float)displayMetricsWidth)/STANDRD_WIDTH;
    }

    /**
     * 获得竖直缩放倍数
     * @return
     */
    public float getVerValue(){
        return ((float)displayMetricsHeight)/STANDRD_HEIGHT;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dp      dp的值
     * @return px的值
     */
    public static int dpTopx(Context context, float dp) {
        return (int) (dp * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param px      px的值
     * @return dp的值
     */
    public static int pxTodp(Context context, float px) {
        return (int) (px / density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context 上下文
     * @param px      px的值
     * @return sp的值
     */
    public static int pxTosp(Context context, float px) {
        //DisplayMetrics类中属性scaledDensity
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context 上下文
     * @param sp      sp的值
     * @return px的值
     */
    public static int spTopx(Context context, float sp) {
        //DisplayMetrics类中属性scaledDensity
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    private static final String DIME_CLASS = "com.android.internal.R$dimen";
    //用于反射系统的属性
    private int getSystemBarHeight(Context context){
        return getValue(context,DIME_CLASS,"system_bar_height",48);
    }

    private int getValue(Context context, String dimeClass, String name, int defaultValue) {
        try {
            Class<?> clz = Class.forName(dimeClass);
            Object object = clz.newInstance();
            //反射属性
            Field field = clz.getField(name);
            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelOffset(id);
        }catch (Exception a){
            LogUtil.e(a.getMessage());
        }
        return defaultValue;
    }

}
