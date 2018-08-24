package com.dd.mylibrary.http.apiService;


import com.dd.mylibrary.http.convert.JsonConverterFactory;
import com.dd.mylibrary.http.utils.interceptor.CommonInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * author：fmd on 16/10/17
 * use:
 */
public class DynamicAPI{

    protected static final int DEFAULT_TIMEOUT = 60;
    public Retrofit retrofit;
    protected String url;
    private int successCode;

    protected DynamicAPI(CommonBuilder builder){

        this.url = builder.url;
        this.successCode = builder.code;
        retrofit = builder.mRetroBuilder.client(builder.mClientBuilder.build()).build();
    }

    public static class CommonBuilder<T extends CommonBuilder>{
        protected String url;
        protected Retrofit.Builder mRetroBuilder;
        protected OkHttpClient.Builder mClientBuilder;
        //请求成功状态码
        protected int code = 1;
        public CommonBuilder(){
            mRetroBuilder = new Retrofit.Builder();
            mClientBuilder = new OkHttpClient.Builder();
            mClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            mClientBuilder.readTimeout(DEFAULT_TIMEOUT , TimeUnit.SECONDS);
            mClientBuilder.writeTimeout(DEFAULT_TIMEOUT , TimeUnit.SECONDS);
            mRetroBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        public T url(String url){

            mRetroBuilder.baseUrl(getBaseUrl(url));
            this.url = url;
            return (T)this;
        }

        public T timeOut(int timeOutSeconds){
            mClientBuilder.connectTimeout(timeOutSeconds, TimeUnit.SECONDS);
            return (T)this;
        }

        public T encrypt(){
            mRetroBuilder.addConverterFactory(JsonConverterFactory.create());
            return (T)this;
        }

        public T intercept(){

            mClientBuilder.addInterceptor(new CommonInterceptor());
            return (T)this;
        }

        public T code(int code){
            this.code = code;
            return (T)this;
        }

        public T retryOnConnectionFailure(boolean retry){
            mClientBuilder.retryOnConnectionFailure(retry);
            return (T)this;
        }

        public <R extends DynamicAPI> R build(){
            return (R)new DynamicAPI(this);
        }

        protected String getBaseUrl(String url){
            if(url==null || url.trim().equals("")){
                return "";
            }
            String host = "";
            Pattern p =  Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
            Matcher matcher = p.matcher(url);
            if(matcher.find()){
                host = matcher.group();
            }
            return "http://" + host + "/";
        }
    }
}
