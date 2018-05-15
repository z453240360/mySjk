package com.dd.mylibrary.http.utils;




import com.dd.mylibrary.http.utils.httpstrust.SSLSocketFactoryUtils;
import com.dd.mylibrary.http.utils.httpstrust.TrustAllHostnameVerifier;
import com.dd.mylibrary.http.utils.interceptor.CommonInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author：fmd on 16/8/31 17
 * use:
 */
public class OkClientProvider {


    private static volatile OkClientProvider okClientProvider;

    private OkHttpClient okHttpClient;


    private OkClientProvider() {
        //日志拦截器
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //添加全局统一请求头
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("content-type", "application/json;charset=UTF-8");
                builder.addHeader("version", "1.0.2");
                return chain.proceed(builder.build());
            }
        };
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory()) //信任所有证书 上线时去除
                .hostnameVerifier(new TrustAllHostnameVerifier())                 //信任所有证书 上线时去除
//                .cookieJar(new CookieJarImpl(PersistentCookieStore.singleInstance()))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(new CommonInterceptor())
//                .addInterceptor(logging)
                .build();
    }

    private static OkClientProvider get() {

        if (okClientProvider == null) {
            synchronized (OkClientProvider.class) {
                if (okClientProvider == null) {
                    okClientProvider = new OkClientProvider();
                }
            }
        }

        return okClientProvider;
    }

    public static OkHttpClient create() {

        return get().okHttpClient;
    }
}
