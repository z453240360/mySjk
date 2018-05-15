package com.dd.mylibrary.http.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * Created by fangmingdong on 2017/1/12.
 * 网络请求加载回调接口
 */

public class ProcessWrapper implements Process {
    private Process mProcess;
    private Handler mH;
    private boolean isCanceled;
    private static final int DELAY = 500;
    private static final int HM_START_PROCESS = 100;
    private static final int HM_SUCCESS_PROCESS = 101;
    private static final int HM_ERROR_PROCESS = 102;
    private static final int HM_CANCEL_PROCESS = 103;
    private boolean isStart = false;

    public ProcessWrapper(Process process){
        mProcess = process;
    }

    private Handler getMainHandler(){

        if(mH == null){
            mH = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg) {
                    if(isCanceled) return;
                    switch (msg.what){
                        case HM_START_PROCESS:
                            mProcess.start();
                            break;
                        case HM_SUCCESS_PROCESS:
                            mProcess.success();
                            break;
                        case HM_ERROR_PROCESS:
                            mProcess.error();
                            break;
                        case HM_CANCEL_PROCESS:
                            mProcess.cancel();
                            break;
                    }
                }
            };
        }
        return mH;
    }


    private boolean isMainThread(){

        return Looper.myLooper() == Looper.getMainLooper();
    }

    @Override
    public void start() {
//        if(isMainThread()){
//            mProcess.start();
//        }else{
        isStart = true;
        getMainHandler().sendEmptyMessageDelayed(HM_START_PROCESS , DELAY);
//        }
    }

    @Override
    public void success() {

        if(isMainThread()){
            mProcess.success();
        }else{
            getMainHandler().sendEmptyMessage(HM_SUCCESS_PROCESS);
        }
    }

    @Override
    public void error() {

        if(isMainThread()){
            mProcess.error();
        }else{
            getMainHandler().sendEmptyMessage(HM_ERROR_PROCESS);
        }
    }

    @Override
    public void cancel() {
        if(isMainThread()){
            isCanceled = true;
            if(mH != null) {
                mH.removeMessages(HM_START_PROCESS);
            }
            if(mProcess != null) {
                mProcess.cancel();
            }
        }else{
            getMainHandler().sendEmptyMessage(HM_CANCEL_PROCESS);
        }
    }

    @Override
    public boolean isProcessing() {
        return isStart || mProcess.isProcessing();
    }
}
