package com.sanjianke.mysjk.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.dd.mylibrary.http.constant.Constant;
import com.dd.mylibrary.utils.WeakHandler;
import com.sanjianke.mysjk.R;

import butterknife.ButterKnife;


public class App extends MultiDexApplication {
    public static final String MAIN_PROCESS = "com.sanjianke.sjk";
    public static App mApp;
    public static boolean isFirstLoginActivity;
    public static WeakHandler mainHandler;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (MAIN_PROCESS.equals(getCurrentProcessName())) {
            mApp = this;
            mainHandler = new WeakHandler(Looper.getMainLooper());
            isFirstLoginActivity = true;
            ButterKnife.setDebug(Constant.showDebug);
        }
    }

    //获取当前进程名称
    private String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

    public static App getmApp() {
        return mApp;
    }

    public static boolean getFirstLoginActivity() {
        return isFirstLoginActivity;
    }

    public static void setIsFirstLoginActivity(boolean isFirstLoginActivity) {
        App.isFirstLoginActivity = isFirstLoginActivity;
    }


    public static Context getContext() {
        return mApp.getApplicationContext();
    }

    public static String getAPPName() {
        return getmApp().getResources().getString(R.string.app_name);
    }

    public static WeakHandler getMainHandler() {
        return mainHandler;
    }

}
