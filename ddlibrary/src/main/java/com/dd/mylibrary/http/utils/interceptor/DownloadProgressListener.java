package com.dd.mylibrary.http.utils.interceptor;

/**
 * author：fmd on 16/9/7
 * use:
 */
public interface DownloadProgressListener {

    void update(long bytesRead, long contentLength, boolean done);
}
