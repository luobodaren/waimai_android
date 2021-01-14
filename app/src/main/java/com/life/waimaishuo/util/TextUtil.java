package com.life.waimaishuo.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;

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
    public static SpannableString getAbsoluteSpannable(String s, int size, int start, int end){
        int textSize = (int) UIUtils.getInstance().scalePx(size);
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new AbsoluteSizeSpan(textSize,false),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


}
