package com.dd.mylibrary.http.utils.interceptor;

import android.text.TextUtils;


import com.dd.mylibrary.utils.BasicConfig;
import com.dd.mylibrary.utils.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * author：fmd on 16/8/22 17
 * use:设置通用HTTP请求头以及日志打印
 */
public class CommonInterceptor implements Interceptor {

    private static final String TAG = "CommonInterceptor";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oldRequest = chain.request();
        Request.Builder requestBuilder = oldRequest.newBuilder();
//                .addHeader("version", "1.0.2");
//                .addHeader("content-type","application/json;charset=UTF-8");
        //如果请求参数没有则添加通用参数
//        if(oldRequest.body().contentLength() <= 0){
//            requestBuilder.post(RequestBody.create(MEDIA_TYPE, DES3Utils.buildEncryptRequestParams(null , RetroUtils.APP)));
//        }

        Request newRequest = requestBuilder.build();
        logForRequest(newRequest);
        Response response = chain.proceed(newRequest);

        return logForResponse(response);
    }

    private Response logForResponse(Response response) {

        if (!BasicConfig.DEBUG) {

            return response;
        }
        try {
            LogUtils.d(TAG, "========response'log=======");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtils.d(TAG, "url : " + clone.request().url());
            LogUtils.d(TAG, "code : " + clone.code());
            LogUtils.d(TAG, "protocol : " + clone.protocol());
            if (!TextUtils.isEmpty(clone.message()))
                LogUtils.d(TAG, "message : " + clone.message());
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    LogUtils.d(TAG, "responseBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        String resp = body.string();
                        LogUtils.d(TAG, "responseBody's content : " + resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        LogUtils.d(TAG, "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }

            LogUtils.d(TAG, "========response'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        if (!BasicConfig.DEBUG) {

            return;
        }
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtils.d(TAG, "========positionRequest'log=======");
            LogUtils.d(TAG, "method : " + request.method());
            LogUtils.d(TAG, "url : " + url);
            if (headers != null && headers.size() > 0) {
                LogUtils.d(TAG, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtils.d(TAG, "requestBody's contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        LogUtils.d(TAG, "requestBody's content : " + bodyToString(request));
                    } else {
                        LogUtils.d(TAG, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtils.d(TAG, "========positionRequest'log=======end");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
