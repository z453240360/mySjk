package com.dd.mylibrary.http.utils;

/**
 * Created by fangmingdong on 2017/1/11.
 */

public interface Process {
    void start();
    void success();
    void error();
    void cancel();
    boolean isProcessing();
}
