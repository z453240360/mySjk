package com.dd.mylibrary.http.utils;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by fangmingdong on 2016/12/27.
 */

public class BResponse<T> implements Serializable {

    public static final String SUCCESS_COED = "0";
    public String code;
    public String msg;
    public T data;

    public BResponse() {
        code = "";
        msg = "";
        data = null;
    }

    public static String getSuccessCoed() {
        return SUCCESS_COED;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return !TextUtils.isEmpty(code) && code.equals(SUCCESS_COED);
    }
}
