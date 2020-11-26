package com.example.base.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.base.views.UiAdapterRelativeLayout;

import java.lang.reflect.Field;
import java.util.Random;

public class UIUtils {

    public static final int SCALE_KEY = new Random(System.currentTimeMillis()).nextInt();
    //标准值
    private static final float STANDRD_WIDTH = 750f;
    private static final float STANDRD_HEIGHT = 1624f;
    //实际值 保存MMKV中
    private static float displayMetricsWidth;
    private static float displayMetricsHeight;
    private static float scaledDensity;
    private static float density;
    private static float horValue;
    private static float verValue;

    private Context context;

    //单例
    private static UIUtils instance;

    private UIUtils(Context context) {
        if(context == null){
            LogUtil.e("context == null");
            return;
        }

        this.context = context;
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

        LogUtil.d("display Width=" + displayMetricsWidth + " height=" + displayMetricsHeight);
    }

    public synchronized static UIUtils getInstance(Context context){
        if (instance == null){
            instance = new UIUtils(context);
        }
        return instance;
    }

    public static void autoAdapterUI(Context context,View view){
        if(view.getTag(UIUtils.SCALE_KEY) == null) {    //防止多次放大
            float scaleX = UIUtils.getInstance(context).getHorValue();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//                LogUtil.e("tag=" + child.getTag() + "  begin width=" + layoutParams.width + " height=" + layoutParams.height);
            if(layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT)
                layoutParams.width = (int) (layoutParams.width * scaleX);
            if(layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT)
                layoutParams.height = (int) (layoutParams.height * scaleX);
            view.setTag(UIUtils.SCALE_KEY,1);
        }
    }


    public static void autoAdapterUI(Context context,ViewGroup viewGroup) {
        float scaleX = UIUtils.getInstance(context).getHorValue();
//        float scaleY = UIUtils.getInstance(getContext()).getVerValue();
        int count = viewGroup.getChildCount();
        for(int i = 0; i < count; i++){
            View child = viewGroup.getChildAt(i);
            if(child.getTag(UIUtils.SCALE_KEY) == null){    //防止多次放大
                ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
//                LogUtil.e("tag=" + child.getTag() + "  begin width=" + layoutParams.width + " height=" + layoutParams.height);
                if(layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT)
                    layoutParams.width = (int) (layoutParams.width * scaleX);
                if(layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT)
                    layoutParams.height = (int) (layoutParams.height * scaleX);
//                LogUtil.e("tag=" + child.getTag() + "  end   width=" + layoutParams.width + " height=" + layoutParams.height);
                if(layoutParams instanceof ViewGroup.MarginLayoutParams){
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    marginLayoutParams.leftMargin = (int) (marginLayoutParams.leftMargin * scaleX);
                    marginLayoutParams.rightMargin = (int) (marginLayoutParams.rightMargin * scaleX);
                    marginLayoutParams.topMargin = (int) (marginLayoutParams.topMargin * scaleX);
                    marginLayoutParams.bottomMargin = (int) (marginLayoutParams.bottomMargin * scaleX);
                }
                if(child instanceof TextView){
                    ((TextView) child).setTextSize
                            (UIUtils.pxTosp(context,((TextView) child).getTextSize() * scaleX));
                }
                child.setTag(UIUtils.SCALE_KEY,1);
                if(child instanceof ViewGroup){
                    autoAdapterUI(context,(ViewGroup) child);
                }
            }
        }
    }

    public static void autoAdapterDrawable(Context context,Drawable drawable){
        if (drawable != null ) {
            drawable.setBounds(0, 0
                    , (int)(drawable.getIntrinsicWidth()*getInstance(context).getHorValue())
                    , (int)(drawable.getIntrinsicHeight()*getInstance(context).getHorValue()));
        }
    }

    /**
     * 获得横向缩放倍数
     * @return
     */
    public float getHorValue(){
        if(horValue == 0.0f){
            horValue = displayMetricsWidth/STANDRD_WIDTH;
            LogUtil.d("horValue=" + horValue);
        }
        return horValue;
    }

    /**
     * 获得竖直缩放倍数
     * @return
     */
    public float getVerValue(){
        if(verValue == 0.0f){
            verValue = displayMetricsHeight/(STANDRD_HEIGHT-getSystemBarHeight(context));
            LogUtil.d("verValue=" + verValue);
        }
        return verValue;
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
            LogUtil.e("Util error " + a.getMessage());
        }
        return defaultValue;
    }

}
