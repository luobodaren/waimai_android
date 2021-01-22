package com.life.waimaishuo.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;

public class TextUtil {

    /**
     * 添加删除线
     * @param s 字符串
     * @return
     */
    public static SpannableString getStrikeThroughSpanSpannable(String s){
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new StrikethroughSpan(),0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 修改局部字体大小
     */
    public static SpannableString getAbsoluteSpannable(String s, int textSize, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new AbsoluteSizeSpan(textSize,false),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    /**
     * 修改局部字体颜色
     */
    public static SpannableString getColorSpannable(String s, @ColorInt int color, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static void setFakeBoldText(TextView textView, boolean bold){
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(bold);
    }

    /**
     * 隐藏手机号码中间几位
     * @param phone 手机号码
     * @return  返回处理的结果
     */
    public static String phoneHide(String phone) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            for (int i = 0; i < phone.length() ;i++) {
                char c = phone.charAt(i);
                if (3 <= i && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

}
