package com.dd.mylibrary.http.utils;


import android.content.Context;
import android.text.TextUtils;

import com.dd.mylibrary.http.retrofit.RetroUtils;
import com.dd.mylibrary.utils.NetWorkUtils;
import com.dd.mylibrary.utils.WeakHandler;
import com.dd.mylibrary.wedget.dialog.HttpDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * author：fmd on 16/9/2 10
 * use:只能用于网络请求
 */
public abstract class ResponseSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "ResponseSubscriber/debug";
    private ProcessWrapper mProcess;
    private boolean toastError;

    /**
     * 请求的dialog
     */
    private HttpDialog progressDialog;
    /**
     * 请求的上下文
     */
    public Context context;

    public static String MESSAGE = "";
    private String msg;

    public ResponseSubscriber(Process process) {
        if (process != null)
            mProcess = new ProcessWrapper(process);
    }

    public ResponseSubscriber(Process process, boolean toastError) {
        this(process);
        this.toastError = toastError;
    }

    public ResponseSubscriber(Context context) {
        this(context, MESSAGE);
    }

    public ResponseSubscriber(Context context, String msg) {
        setContext(context);
        this.msg = msg;
        startTime = 0;
        endTime = 0;
        if (!TextUtils.isEmpty(msg)) {
            setDialog(context,msg);
        }
    }


    @Override
    public void onNext(T t) {
//        dismiss();
        apiSuccess(t);
    }

    /**
     * 设置progressDialog
     *
     * @param pg
     */
    public void setDialog(Context context, String pg) {
        progressDialog = new HttpDialog(context);
//        progressDialog = HttpDialog.getInstance(context);
        if (!TextUtils.isEmpty(pg)) {
//            progressDialog.setMessage(pg);
//            progressDialog.show();
        }
    }

    /**
     * 设置上下文
     *
     * @param ct
     */
    public void setContext(Context ct) {
        if (ct != null) {
            context = ct;
        }
    }

    private long startTime, endTime;
    private boolean isShow = true;

    private WeakHandler handler;
    private Runnable runnable;

    public void showDialog() {
        if (!TextUtils.isEmpty(msg)) {
            startTime = System.currentTimeMillis();
            handler = new WeakHandler();
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (isShow) {
                            if (progressDialog != null && !progressDialog.isShowing()) {
                                progressDialog.show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 500);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        showDialog();
        //检查网络
        if (!NetWorkUtils.isNetworkEnable(context)) {
//            APIException exception = new APIException(APIException.NO_NETWORK , APIException.NO_NETWORK_MSG);
//            throw exception;
            if (mProcess != null) {
                mProcess.error();
            }
            return;
        }
        if (mProcess != null) {
            mProcess.start();
        }
    }


    private void dismiss() {
        if (!TextUtils.isEmpty(msg)) {
            endTime = System.currentTimeMillis();
            if (endTime - startTime <= 500) {
                isShow = false;
                handler.removeCallbacks(runnable);
            } else {
                isShow = true;
            }
        }
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        progressDialog = null;
        handler = null;
    }

    @Override
    public void onCompleted() {
        dismiss();
        if (mProcess != null) {
            mProcess.cancel();
        }
        mProcess = null;
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onError(Throwable e) {
        dismiss();
        if (mProcess != null) {
            mProcess.cancel();
        }
        mProcess = null;
        APIException exception;
        if (e instanceof APIException) {
            exception = (APIException) e;
        } else if (e instanceof SocketTimeoutException
                || isTimeout(e.getMessage())) {
            exception = new APIException(APIException.ERROR_TIME_OUT, APIException.TIME_OUT_MSG);
        } else if (e instanceof ConnectException
                || e instanceof HttpException) {
            exception = new APIException(APIException.ERROR_SERVER_CONNECTION, APIException.CONNECT_ERROR_MSG);
        } else if (!NetWorkUtils.isNetworkEnable(RetroUtils.APP)) {
            exception = new APIException(APIException.NO_NETWORK, APIException.NO_NETWORK_MSG);
        } else {
            exception = new APIException(e);
        }
        apiError(exception);
        if (!isUnsubscribed()) {
            unsubscribe();
        }
    }


    private boolean isTimeout(String error) {
        if (!TextUtils.isEmpty(error)) {
            return error.contains("timeout") || error.contains("timeOut");
        }
        return false;
    }

    public abstract void apiSuccess(T t);

    public abstract void apiError(APIException e);
}
