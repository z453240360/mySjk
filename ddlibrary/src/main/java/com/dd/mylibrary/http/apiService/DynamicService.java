package com.dd.mylibrary.http.apiService;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author：fangmingdong on 16/12/27
 * use:
 */
public interface DynamicService {


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
