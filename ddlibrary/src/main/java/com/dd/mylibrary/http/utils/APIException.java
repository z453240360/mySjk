package com.dd.mylibrary.http.utils;

/**
 * author：fmd on 16/9/12
 * use:
 */
public class APIException extends IllegalStateException {

    public static final String ERROR_JSON_PARSE = "-100";
    public static final String ERROR_TIME_OUT = "-101";
    public static final String ERROR_SERVER_CONNECTION = "-102";
    public static final String ERROR_UNKNOWN = "-109";
    public static final String NO_NETWORK = "-104";
    public static final String ERROR_DATA = "-105";
    public static final String DATA_ERROR_MSG = "服务器数据错误";
    public static final String TIME_OUT_MSG = "请求超时";
    public static final String CONNECT_ERROR_MSG = "无法连接服务器，请检查网络";
    public static final String NO_NETWORK_MSG = "无网络连接";
    public static final String UNKNOWN_MSG = "出现未知错误";

    public String code;
    public Object data;

    public Object getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //jSON 解析异常 或者服务器异常
    public APIException(String code, Object t, String message) {

        super(message);
        this.code = code;
        this.data = t;
    }

    public APIException(String code, Object t) {

        super("请求错误，状态码：" + code);
        this.code = code;
        this.data = t;
    }

    public APIException(String code, String message) {

        super(message);
        this.code = code;
    }

    public APIException(Throwable throwable) {
        super(throwable);
        this.code = ERROR_UNKNOWN;
    }

}
