package com.dd.mylibrary.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


import com.dd.mylibrary.bean.TextBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20.
 */

public class TextViewUtil {
    public static void applyFromTextBaen(TextView tv, TextBean textBean) {
        tv.setText(textBean.getName());
        tv.setTextColor(textBean.getColours());
        tv.setTextSize(textBean.getSize());
        tv.setBackgroundColor(textBean.getBackgroundColours());
    }

    /**
     * 方法描述：修改TextView部分字体的颜色
     *
     * @param color 颜色值为int类型
     * @param start 要修改TextView的内容起始位置
     * @param end   要修改TextView的内容结束位置
     */
    public static void modifySomeFontColor(TextView textView, int color, int start, int end) {
        String content = textView.getText().toString();
        if (end > content.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range:" + content.length());
        }
        ForegroundColorSpan modifySpan = new ForegroundColorSpan(color);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(modifySpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 方法描述：修改TextView部分字体的颜色
     *
     * @param color  颜色值为int类型
     * @param string 需要修改的字符串
     */
    public static void modifySomeFontColorByString(TextView textView, int color, String string) {
        String content = textView.getText().toString();
        if (!content.contains(string)) {
            throw new StringNotFoundException("字符串====>" + content + "不包含字符串：" + string);
        }
        int start = content.indexOf(string);
        int end = string.length() + start;
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        ForegroundColorSpan modifySpan = new ForegroundColorSpan(color);
        builder.setSpan(modifySpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 方法描述：修改TextView部分字体的背景颜色
     *
     * @param color 颜色值为int类型
     * @param start 要修改TextView的内容起始位置
     * @param end   要修改TextView的内容结束位置
     */

    public static void modifySomeFontBackground(TextView textView, int color, int start, int end) {
        String content = textView.getText().toString();
        if (end > content.length()) {
            throw new StringIndexOutOfBoundsException("String index out of range:" + content.length());
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        BackgroundColorSpan modifySpan = new BackgroundColorSpan(color);
        builder.setSpan(modifySpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 方法描述：修改TextView部分字体的背景颜色
     *
     * @param color  颜色值为int类型
     * @param string 需要修改的字符串
     */
    public static void modifySomeFontBackgroundByString(TextView textView, int color, String string) {
        String content = textView.getText().toString();
        if (!content.contains(string)) {
            throw new StringNotFoundException("字符串====>" + content + "不包含字符串：" + string);
        }
        int start = content.indexOf(string);
        int end = string.length() + start;
        BackgroundColorSpan modifySpan = new BackgroundColorSpan(color);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(modifySpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    /**
     * 字体颜色 位置 字体大小 动态修改
     * 开始 到 结束
     **/
    public static void TextSpannable(Context context, TextView textView, String text, String color, int start, int
            end) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
        textView.setText(style);
    }

    public static class StringNotFoundException extends RuntimeException implements Serializable {
        public StringNotFoundException(String msg) {
            super(msg);
        }
    }
}
