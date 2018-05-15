package com.dd.mylibrary.http.apiService;


/**
 * author：fmd on 16/9/12
 * use:
 */
public class ApiModel {

    private static final String TAG = "ApiModel";

    private static volatile ApiModel apiModel;

    public static ApiModel create() {
        if (apiModel == null) {
            synchronized (ApiModel.class) {
                if (apiModel == null) {
                    apiModel = new ApiModel();
                }
            }
        }
        return apiModel;
    }

    private ApiModel() {
    }


//    //获取验证码
//    public Observable<BResponse> getCode(RequestBody body) {
//        return RetroUtils.getApi().getCode2(body).flatMap(FlatProvider.<BResponse>flat());
//    }



}
