package com.dd.mylibrary.http.apiService;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public interface ApiService {

    //测试
    @POST("app/api")
    Call<ResponseBody> getCode2(@Body RequestBody verifyCode);
}
