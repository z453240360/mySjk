package com.dd.mylibrary.http.apiService;

/**
 * Created by helin on 2016/10/10 11:52.
 */

public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;

    public static final int NO_API = 1;//不存在的服务请求
    public static final int ERROR_PARAM = 2;//参数非法
    public static final int NO_APP_ID = 3;//缺少应用键参数
    public static final int ERROR_APP_ID = 4;//应用键参数无效
    public static final int NO_SIGN_PARAM = 5;//缺少签名参数
    public static final int ERROR_SIGN = 6;//签名无效
    public static final int TIMEOUT = 7;//业务逻辑出错
    public static final int ERROR_BUSI = 8;//业务逻辑出错
    public static final int SERVICE_INVALID = 9;//服务不可用
    public static final int TIME_INVALID = 10;//请求时间格式错误
    public static final int NO_FORMATTER = 11;//序列化格式不存在
    public static final int SYS_ERROR  = -9;//系统错误
    public static final int TOKEN_EXPIRED  = 41;//token已失效
    public static final int TOKEN_INVALID  = 42;//token不正确

    private String message;
    private int code = -1;
    private String time;

    public ApiException(String message) {
        this.message = message;
    }

    public ApiException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public ApiException(String message, int code, String time) {
        this.message = message;
        this.code = code;
        this.time = time;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private String getApiExceptionMessage(int code) {
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case 1000:
                message = "取消dialog";
                break;
            default:
                message = "未知错误";
        }
        return message;
    }
}
