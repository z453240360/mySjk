package com.dd.mylibrary.http.utils;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fangmingdong on 2016/12/27.
 */

public class FlatProvider {

    public static <R> Observable<R> flatResponse(final R t){

        return Observable.create(new Observable.OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {

                APIException exception = null;
                if (t == null) {
                    exception = new APIException(APIException.ERROR_SERVER_CONNECTION, t, APIException.DATA_ERROR_MSG);
                }
                if (t instanceof String) {
                    try {
                        JSONObject jsonObject = new JSONObject((String) t);
                        String code = JSONUtils.getString(jsonObject , "code");
                        String msg = JSONUtils.getString(jsonObject , "msg");


                        if(code != null && code.equals(BResponse.SUCCESS_COED)){
                            exception = new APIException(code , t , msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        exception = new APIException(APIException.ERROR_JSON_PARSE , t , APIException.DATA_ERROR_MSG);
                    }
                }else{
                    BResponse baseBean = (BResponse) t;
                    if(!baseBean.isSuccess()){

                        String errorMsg = baseBean.msg;
                        exception = new APIException(baseBean.code, t, errorMsg);
                    }
                }
                if(checkSubScribed(subscriber)){
                    if(exception == null){
                        subscriber.onNext(t);
                    }else{
                        subscriber.onError(exception);
                    }

                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T> Func1<T, Observable<T>> flat() {
        return new Func1<T, Observable<T>>() {

            @Override
            public Observable<T> call(T t) {
                return flatResponse(t);
            }
        };
    }

    public static boolean checkSubScribed(Subscriber subscriber) {

        return !subscriber.isUnsubscribed();
    }
}
