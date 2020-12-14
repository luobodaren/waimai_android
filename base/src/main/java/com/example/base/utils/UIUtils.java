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

import com.example.base.views.UiAdapterRelativeLayout;

import java.lang.reflect.Field;
import java.util.Random;

import static com.xuexiang.xui.XUI.getContext;

public class UIUtils {

    private static final int SCALE_KEY = new Random(System.currentTimeMillis()).nextInt();

    private static final String DIME_CLASS = "com.android.internal.R$dimen";
    //标准值
    private static final float STANDRD_WIDTH = 750f;
    private static final float STANDRD_HEIGHT = 1624f;
    //实际值 保存MMKV中
    private static float displayMetricsWidth;
    private static float displayMetricsHeight;
    private static float scaledDensity;
    private static float systemBarHeight;
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
            systemBarHeight = getSystemBarHeight(context);
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

    // FIXME: 2020/12/10 background圆角角度适配添加
    public void autoAdapterUI(View view){
        if(isScale(view)) {    //防止多次放大
            scaleView(view,getHorValue());
            setScaleTag(view);
        }
    }

    public void autoAdapterUI(ViewGroup viewGroup) {
        if(isScale(viewGroup)){//防止多次放大
            scaleView(viewGroup,getHorValue());
            setScaleTag(viewGroup);
        }

        int count = viewGroup.getChildCount();
        for(int i = 0; i < count; i++){
            View child = viewGroup.getChildAt(i);
            if(isScale(child)){    //防止多次放大
                scaleView(child,getHorValue());
                setScaleTag(child);
                if(child instanceof ViewGroup){
                    autoAdapterUI((ViewGroup) child);
                }
            }
        }
    }

    public void autoAdapterDrawable(Context context,Drawable drawable){
        if (drawable != null ) {
            drawable.setBounds(0, 0
                    , (int)(drawable.getIntrinsicWidth()*getInstance(context).getHorValue())
                    , (int)(drawable.getIntrinsicHeight()*getInstance(context).getHorValue()));
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dp      dp的值
     * @return px的值
     */
    public int dpTopx(float dp) {
        return (int) (dp * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param px      px的值
     * @return dp的值
     */
    public int pxTodp(float px) {
        return (int) (px / density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param px      px的值
     * @return sp的值
     */
    public int pxTosp(float px) {
        //DisplayMetrics类中属性scaledDensity
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param sp      sp的值
     * @return px的值
     */
    public int spTopx(float sp) {
        //DisplayMetrics类中属性scaledDensity
        final float fontScale = scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
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
            verValue = displayMetricsHeight/(STANDRD_HEIGHT-getSystemBarHeight());
            LogUtil.d("verValue=" + verValue);
        }
        return verValue;
    }

    /**
     * 获得屏幕宽度
     * @return
     */
    public float getDisplayMetricsWidth() {
        return displayMetricsWidth;
    }

    /**
     * 获得屏幕高度
     * @return
     */
    public float getDisplayMetricsHeight() {
        return displayMetricsHeight;
    }

    public float getScaledDensity() {
        return scaledDensity;
    }

    /**
     * 获得状态栏高度
     * @return
     */
    public float getSystemBarHeight() {
        return systemBarHeight;
    }

    public float getDensity() {
        return density;
    }

    public int getViewMeasureWidth(View view) {
        view.measure(0,0);
        return view.getMeasuredWidth();
    }

    public int getViewMeasureHeight(View view) {
        view.measure(0,0);
        return view.getMeasuredHeight();
    }


    /**
     * 对View进行缩放
     * @param view view
     * @param scaleX 缩放倍数
     */
    private void scaleView(View view, float scaleX){
        view.setPadding((int)(view.getPaddingStart() * scaleX),
                (int)(view.getPaddingTop() * scaleX),
                (int)(view.getPaddingEnd() * scaleX),
                (int)(view.getPaddingBottom() * scaleX));
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT)
            layoutParams.width = (int) (layoutParams.width * scaleX);
        if(layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT)
            layoutParams.height = (int) (layoutParams.height * scaleX);
        if(layoutParams instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.leftMargin = (int) (marginLayoutParams.leftMargin * scaleX);
            marginLayoutParams.rightMargin = (int) (marginLayoutParams.rightMargin * scaleX);
            marginLayoutParams.topMargin = (int) (marginLayoutParams.topMargin * scaleX);
            marginLayoutParams.bottomMargin = (int) (marginLayoutParams.bottomMargin * scaleX);
        }
        view.setLayoutParams(layoutParams);
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setTextSize(pxTosp(textView.getTextSize() * scaleX));
        }
    }

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

    public void setScaleTag(View view){
        if(view.getTag(SCALE_KEY) == null){
            view.setTag(SCALE_KEY,1);
        }
    }

    public boolean isScale(View view){
        if(view.getTag(SCALE_KEY) == null){
            return true;
        }else{
            return false;
        }
    }
}
