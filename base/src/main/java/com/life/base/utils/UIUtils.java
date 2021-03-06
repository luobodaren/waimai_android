package com.life.base.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.life.base.MMKVConstant;
import com.life.base.R;
import com.tencent.mmkv.MMKV;
import com.xuexiang.xui.utils.StatusBarUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class UIUtils implements Parcelable {

    private final static String DIME_CLASS = "com.android.internal.R$dimen";
    //标准值
    private final static float STANDRD_WIDTH = 750f;
    private final static float STANDRD_HEIGHT = 1624f;
    //实际值 保存MMKV中
    private float displayMetricsWidth;
    private float displayMetricsHeight;
    private float scaledDensity;
    private float systemBarHeight;
    private float density;
    private float horValue;
    private float verValue;
    //单例
    private static UIUtils instance;

    private boolean hasInit = false;

    private UIUtils() { }

    protected UIUtils(Parcel in) {
        displayMetricsWidth = in.readFloat();
        displayMetricsHeight = in.readFloat();
        scaledDensity = in.readFloat();
        systemBarHeight = in.readFloat();
        density = in.readFloat();
        horValue = in.readFloat();
        verValue = in.readFloat();
        hasInit = in.readByte() != 0;
    }

    public static final Creator<UIUtils> CREATOR = new Creator<UIUtils>() {
        @Override
        public UIUtils createFromParcel(Parcel in) {
            return new UIUtils(in);
        }

        @Override
        public UIUtils[] newArray(int size) {
            return new UIUtils[size];
        }
    };

    private void saveToMMKV(){
        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_UI_UTIL);
        mmkv.encode("UIUtils",instance);
    }

    private static void readFormMMKV(){
        MMKV mmkv = MMKV.mmkvWithID(MMKVConstant.MMKV_ID_UI_UTIL);
        instance = mmkv.decodeParcelable("UIUtils",null);
    }

    public synchronized static UIUtils getInstance(){
        if (instance == null){
            readFormMMKV();
            if(instance == null){
                instance = new UIUtils();
            }else{
                instance.hasInit = true;
            }
        }
        return instance;
    }

    /**
     * 注意需要先初始化
     * @param context
     */
    public void init(Context context){
        if(context == null){
            LogUtil.e("context == null");
            return;
        }

        if(hasInit){
            return;
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // TODO: 2020/11/24 从缓存中读取时间屏幕高宽值
        if(displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f
                || scaledDensity == 0.0f || density == 0.0f) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            systemBarHeight = StatusBarUtils.getStatusBarHeight(context);
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

            verValue = displayMetricsHeight/(STANDRD_HEIGHT-getSystemBarHeight());
            horValue = displayMetricsWidth/STANDRD_WIDTH;

            saveToMMKV();
        }

        hasInit = true;

        LogUtil.d("display Width=" + displayMetricsWidth + " height=" + displayMetricsHeight);
    }

    public void autoAdapterUI(View view){
        if(!hasInit){
            LogUtil.e("尚未初始化");
           return;
        }
        if(isNeedScale(view)) {    //防止多次放大
            scaleView(view,getHorValue());
            setScaleTag(view);
        }
    }

    public void autoAdapterUI(ViewGroup viewGroup) {
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return;
        }
        if(isNeedScale(viewGroup)){//防止多次放大
            scaleView(viewGroup,getHorValue());
            setScaleTag(viewGroup);
        }

        int count = viewGroup.getChildCount();
        for(int i = 0; i < count; i++){
            View child = viewGroup.getChildAt(i);
            if(isNeedScale(child)){    //防止多次放大
                scaleView(child,getHorValue());
                setScaleTag(child);
                if(child instanceof ViewGroup){
                    autoAdapterUI((ViewGroup) child);
                }
            }
        }
    }

    public void autoAdapterDrawable(Drawable drawable){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return;
        }
        if (drawable != null ) {
            drawable.setBounds(0, 0
                    , (int)(drawable.getIntrinsicWidth() * getHorValue())
                    , (int)(drawable.getIntrinsicHeight() * getHorValue()));
        }
    }

    /**
     * 自动缩放像素
     * @param px
     * @return
     */
    public float scalePx(float px){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return px;
        }
        return px * getHorValue();
    }

    /**
     * 自动缩放dp
     * @param dp
     * @return
     */
    public float scaleDp(float dp){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return dp;
        }
        return dp * getHorValue();
    }

    /**
     * 自动缩放Sp
     * @param sp
     * @return
     */
    public float scaleSp(float sp){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return sp;
        }
        return sp * getHorValue();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dp      dp的值
     * @return px的值
     */
    public int dpToPx(float dp) {
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return (int) dp;
        }
        return (int) (dp * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param px      px的值
     * @return dp的值
     */
    public int pxToDp(float px) {
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return (int) px;
        }
        return (int) (px / density + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param px      px的值
     * @return sp的值
     */
    public int pxToSp(float px) {
        //DisplayMetrics类中属性scaledDensity
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return (int)px;
        }
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param sp      sp的值
     * @return px的值
     */
    public int spToPx(float sp) {
        //DisplayMetrics类中属性scaledDensity
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return (int)sp;
        }
        final float fontScale = scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    /**
     *  设置字体大小，自动缩放（UiUtil）
     * @param textView
     * @param size pixel
     */
    public void setTextPxSizeAutoScale(TextView textView, int size) {
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return;
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,size * getHorValue());
    }

    /**
     * 同上
     * @param textView
     * @param size sp
     */
    public void setTextSpSizeAutoScale(TextView textView, int size) {
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return;
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,size * getHorValue());
    }

    /**
     * 获得横向缩放倍数
     * @return
     */
    public float getHorValue(){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return 1;
        }
        return horValue;
    }

    /**
     * 获得竖直缩放倍数
     * @return
     */
    public float getVerValue(){
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return 1;
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
        if(!hasInit){
            LogUtil.e("尚未初始化");
            return;
        }
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
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textView.getTextSize() * scaleX);
        }
    }

    //用于反射系统的属性
    /*private int getSystemBarHeight(Context context){
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
    }*/

    /**
     * 设置已经过放大的标志
     * @param view
     */
    public void setScaleTag(View view){
        if(view.getTag(R.id.tag_scale) == null){
            view.setTag(R.id.tag_scale,1);
        }
    }

    public boolean isNeedScale(View view){
        if(view.getTag(R.id.tag_scale) == null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(displayMetricsWidth);
        dest.writeFloat(displayMetricsHeight);
        dest.writeFloat(scaledDensity);
        dest.writeFloat(systemBarHeight);
        dest.writeFloat(density);
        dest.writeFloat(horValue);
        dest.writeFloat(verValue);
        dest.writeByte((byte) (hasInit ? 1 : 0));
    }
}
