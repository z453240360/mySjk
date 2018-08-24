package com.dd.mylibrary.http.download;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by fangmingdong on 17/03/02.
 */
public abstract class BaseRequest<T> extends AsyncTask<Void, Integer, T> {

    public static final int TIME_OUT_THRESHOLD = 20000;
    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_POST = "POST";
    protected String mRequestType;
    protected boolean mIsEncry;
    private String mRequestUrl;
    private Map<String, String> mRequestParams;
    private OnRequestSuccessListener mSuccessListener;
    private OnRequestFailedListener mFailedListener;

    public BaseRequest(String requestUrl) {
        this(requestUrl, null, REQUEST_TYPE_GET);
    }

    public BaseRequest(String requestUrl, Map<String, String> requestParams) {
        this(requestUrl, requestParams, REQUEST_TYPE_POST);
    }

    public BaseRequest(String requestUrl, Map<String, String> requestParams, String requestType) {
        this(requestUrl, requestParams, requestType, true);
    }

    public BaseRequest(String requestUrl, Map<String, String> requestParams, boolean isEncry) {
        this(requestUrl, requestParams, REQUEST_TYPE_POST, isEncry);
    }

    public BaseRequest(String requestUrl, Map<String, String> requestParams, String requestType, boolean isEncry) {
        this.mRequestUrl = requestUrl;
        this.mRequestParams = requestParams;
        this.mRequestType = requestType;
        this.mIsEncry = isEncry;
    }

    public static String convertUnicode(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        int end = utfString.length();
        if (pos < end) {
            sb.append(utfString.substring(pos, end));
        }
        return sb.toString();
    }

    public BaseRequest<T> setOnSuccessListener(OnRequestSuccessListener successListener) {
        this.mSuccessListener = successListener;
        return this;
    }

    public BaseRequest<T> setOnFailedListener(OnRequestFailedListener failedListener) {
        this.mFailedListener = failedListener;
        return this;
    }

    public void setRequestUrl(String requestUrl) {
        this.mRequestUrl = requestUrl;
    }

    @Override
    protected T doInBackground(Void... params) {

        URL url;
        HttpURLConnection conn;
        PrintWriter printWriter = null;
        OutputStream os = null;
        try {
            //初始化conn
            url = new URL(mRequestUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(mRequestType);
            conn.setUseCaches(false);
            conn.setConnectTimeout(TIME_OUT_THRESHOLD);
            conn.setReadTimeout(TIME_OUT_THRESHOLD);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setDoInput(true);
            if (TextUtils.equals(mRequestType, REQUEST_TYPE_POST)) {
                conn.setDoOutput(true);
                if (null != this.mRequestParams) {
                    //输出流写参数
                    os = conn.getOutputStream();
                    // 获取URLConnection对象对应的输出流
                    printWriter = new PrintWriter(os);
                    String paramsStr = parseRequestParams();
                    // 发送请求参数
                    printWriter.write(paramsStr);
                    // flush输出流的缓冲
                    printWriter.flush();
                }
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                //输入流读响应
                InputStream is = conn.getInputStream();
                int size =  conn.getContentLength();//有时返回-1？
                if (size<=0){
                    size =is.available();
                }
                T response = handleResponse(is, size);
                return response;
            } else {
                v(responseCode + "");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);

        if (mSuccessListener != null) {
            mSuccessListener.onRequestSuccess(t);
        }
    }

    public abstract T handleResponse(InputStream inputStream, int contentLength);

    protected abstract String parseRequestParams();

    protected Map<String, String> getRequestParams() {
        return this.mRequestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.mRequestParams = requestParams;
    }

    public void v(String msg) {
        if (null != msg) {
            Log.v(getClass().getCanonicalName(), msg);
        }
    }

    public interface OnRequestSuccessListener<T> {
        void onRequestSuccess(T result);
    }

    public interface OnRequestFailedListener {
        void onRequestFailed(Exception e);
    }
}
