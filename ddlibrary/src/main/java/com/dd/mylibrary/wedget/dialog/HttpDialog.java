package com.dd.mylibrary.wedget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.dd.mylibrary.R;


/**
 * Created by yanghongyu on 2017/12/25.
 */

public class HttpDialog extends Dialog implements DialogInterface.OnKeyListener {

    private String message = "";
    private static HttpDialog dialog;

    public static HttpDialog getInstance(Context context){
        if (dialog==null){
            dialog = new HttpDialog(context);
        }
        return dialog;
    }

    public HttpDialog(Context context) {
        this(context, R.style.dialog);
        setCanceledOnTouchOutside(false);
    }

    public HttpDialog(Context context, int theme) {
        super(context, theme);
        setContentView(R.layout.layout_loading);
        setOnKeyListener(this);

    }


    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mOnKeyBackListener != null) {
                mOnKeyBackListener.keyBack();
                dismiss();
            }
        }
        return false;
    }

    onKeyBackListener mOnKeyBackListener;

    public void setOnKeyBackListener(onKeyBackListener listener) {
        mOnKeyBackListener = listener;
    }

    public interface onKeyBackListener {
        void keyBack();
    }
}
