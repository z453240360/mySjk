package com.dd.mylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class Tool {
    /**
     * 是否大于2.3，大于2.3才让adapter用自己的onClickListener
     */
    public final static boolean versionUpGingerbreadMr1;

    static {
        versionUpGingerbreadMr1 = Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * 判断是否连续点击
     * 对于 startActivity 设置 singletop 无效果
     * 则这样 防止 连续点击跳重复界面
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime <= 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isSpecialFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 添加日历事件、日程
     *
     * @param mActivity 上下文
     */
    public static void closeKeyboard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 判断相机是否可用
     * @return
     */
    @SuppressWarnings("deprecation")
    public static boolean isCamareAviable(Context context) {
        boolean flag = false;
        try {
            Camera camera = null;
            camera = Camera.open();
            camera.release();
            flag = true;

        } catch (Exception e) {
            // TODO: handle exception
            //LogUtil.e("", "");
        }
        return flag;
    }


    /**
     * 开启倒计时
     *
     * @param time 倒计时时间  单位秒
     */
    public static Subscription countTime(final int time, Subscriber<Integer> subscriber) {
        //第一个参数延时时间，第二个参数间隔时间，第三个参数时间单位
        Subscription subscription = Observable.interval(0, 1, TimeUnit.SECONDS) //从0开始，每间隔1秒+1
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long aLong) {
                        return time - aLong.intValue();//通过操作符变成从60秒，每秒减去返回的值；
                    }
                }).take(time + 1)//取前60个数字，1-60；
                .subscribe(subscriber);
        return subscription;
    }


}
