package com.dd.mylibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by yanghongyu on 2018/1/31.
 * 屏幕相关工具类
 */

public class ScreenUtil {

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {

        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }

    /***********
     * 获取屏幕高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        int height = metric.heightPixels;     // 屏幕高度（像素）
        return height;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarH(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * sp转px
     * @param
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int[] getViewLocation(View view) {
        int[] location = new int[2];
//        view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        return location;
    }

    /**
     * @param pxVal
     * @return 根据设备的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Context context, float pxVal) {
        return Math.round(pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
