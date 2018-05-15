package com.dd.mylibrary.http.utils.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author：fmd on 16/9/7
 * use:
 */
public class DownloadProgressInterceptor implements Interceptor{

    private DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}
