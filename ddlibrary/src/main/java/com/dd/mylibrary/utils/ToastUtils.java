package com.dd.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
