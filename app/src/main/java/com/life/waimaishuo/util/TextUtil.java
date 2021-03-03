package com.life.waimaishuo.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.life.base.utils.LogUtil;
import com.life.base.utils.UIUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TextUtil {

    /**
     * 设置局部点击
     */
    public static SpannableString getClickableSpan(String s, View.OnClickListener onClickListener, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        setClickableSpan(spannableString,onClickListener,start,end);
        return spannableString;
    }
    public static SpannableString setClickableSpan(SpannableString s, View.OnClickListener onClickListener, int start, int end){
        s.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                onClickListener.onClick(widget);
            }
        },start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    /**
     * 添加删除线
     */
    public static SpannableString getStrikeThroughSpanSpannable(String s, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        setStrikeThroughSpanSpannable(spannableString,start,end);
        return spannableString;
    }
    public static SpannableString setStrikeThroughSpanSpannable(SpannableString s,int start,int end){
        s.setSpan(new StrikethroughSpan(),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }
    public static void setStrikeThrough(TextView textView){
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线（删除线）
        textView.getPaint().setAntiAlias(true);
    }

    /**
     * 修改局部字体大小
     */
    public static SpannableString getAbsoluteSpannable(String s, int textSize, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        setAbsoluteSpannable(spannableString, textSize, start,end);
        return spannableString;
    }
    public static SpannableString setAbsoluteSpannable(SpannableString s, int textSize, int start, int end){
        s.setSpan(new AbsoluteSizeSpan(textSize,false),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    /**
     * 修改局部字体颜色
     */
    public static SpannableString getColorSpannable(String s, @ColorInt int color, int start, int end){
        SpannableString spannableString = new SpannableString(s);
        setColorSpannable(spannableString,color,start,end);
        return spannableString;
    }
    public static SpannableString setColorSpannable(SpannableString s, @ColorInt int color, int start, int end){
        s.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
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

    /**
     * 大陆号码或香港号码都可以
     * @param str
     * @return 符合规则返回true
     * @throws PatternSyntaxException
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     *
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     * @param str
     * @return 正确返回true
     * @throws PatternSyntaxException
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     * @param str
     * @return 正确返回true
     * @throws PatternSyntaxException
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
