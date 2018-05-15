package com.dd.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedPreferencesUtil {
    private static final String TAG = SharedPreferencesUtil.class.getSimpleName();

    public static final String FILE_NAME = "share_data";

    private SharedPreferencesUtil() {
    }

    public static void put(Context context, String key, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(object);
                String bytesToHexString = bytesToHexString(bos.toByteArray());
                editor.putString(key, bytesToHexString);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "saving object failed!");
            }
        }
        editor.apply();
    }

    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        if (!contains(context, key)) {
            return defValue;
        }
        if (defValue instanceof String) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defValue);
        } else {
            String string = sharedPreferences.getString(key, "");
            if (TextUtils.isEmpty(string))
                return defValue;
            try {
                byte[] stringToBytes = StringToBytes(string);
                ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                ObjectInputStream is = new ObjectInputStream(bis);
                return is.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }


    public static void remove(Context context, String key) {
        if (!contains(context, key)) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public static boolean contains(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }


    private static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 方法描述:将16进制的数据转为数组
     */
    private static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); // 0 Ascii - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; // A Ascii - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }
}
