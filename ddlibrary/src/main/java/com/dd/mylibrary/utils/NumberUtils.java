package com.dd.mylibrary.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by fangmingdong on 2017/1/18.
 */

public class NumberUtils {
    private static final String TAG = "NumberUtils";
    private static final int DEF_DIV_SCALE=10;
    public static int string2Int(String str) {
        if (TextUtils.isEmpty(str))
            return 0;
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            LogUtils.e(TAG, "parse String " + str + " error");
        }
        return result;
    }

    public static long string2Long(String str) {
        if (TextUtils.isEmpty(str)) return 0;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            LogUtils.e(TAG, "parse String " + str + " error");
        }
        return 0;
    }

    public static float string2Float(String str) {
        if (TextUtils.isEmpty(str)) return 0f;
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            LogUtils.e(TAG, "parse String " + str + " error");
        }
        return 0f;
    }

    public static double string2Double(String str) {
        if (TextUtils.isEmpty(str)) return 0;
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            LogUtils.e(TAG, "parse String " + str + " error");
        }
        return 0;
    }

    public static BigDecimal add(String d1, String d2){
        BigDecimal b1=new BigDecimal(d1);
        BigDecimal b2=new BigDecimal(d2);
        return b1.add(b2);

    }

    public static BigDecimal sub(String d1, String d2){
        BigDecimal b1=new BigDecimal(d1);
        BigDecimal b2=new BigDecimal(d2);
        return b1.subtract(b2);

    }

    public static BigDecimal mul(String d1, String d2){
        BigDecimal b1=new BigDecimal(d1);
        BigDecimal b2=new BigDecimal(d2);
        return b1.multiply(b2);

    }

    public static BigDecimal div(String d1, String d2){

        return div(d1,d2,DEF_DIV_SCALE);

    }

    public static BigDecimal div(String d1, String d2, int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1=new BigDecimal(d1);
        BigDecimal b2=new BigDecimal(d2);
        return b1.divide(b2,scale, BigDecimal.ROUND_HALF_UP);

    }

    public static String numberFormat(float number){
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(number);
    }

    public static String doubleFormat(double number){
        DecimalFormat numberFormat = new DecimalFormat("#####");
        return numberFormat.format(number);
    }

    public static String doubleFormat(String numberStr){
        DecimalFormat numberFormat = new DecimalFormat("#####");
        return numberFormat.format(string2Double(numberStr));
    }

    public static String numberFormat(String numberStr){
        return numberFormat(string2Float(numberStr));
    }



}
