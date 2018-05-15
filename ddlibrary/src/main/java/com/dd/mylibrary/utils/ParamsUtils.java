package com.dd.mylibrary.utils;

import com.dd.mylibrary.bean.ParamsBeam;
import com.dd.mylibrary.bean.PhoneBean;
import com.dd.mylibrary.http.constant.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class ParamsUtils {


    public static String test() {
        ParamsBeam.DataBean dataBean = new ParamsBeam.DataBean();
        dataBean.setIsEvaluated("0");
        dataBean.setStatus(0);
        ParamsBeam.DataBean.PagebeanBean pagebeanBean = new ParamsBeam.DataBean.PagebeanBean();
        pagebeanBean.setPageNumber(1);
        pagebeanBean.setPageSize(10);
        dataBean.setPagebean(pagebeanBean);


        HashMap<String, Object> param = new HashMap<>();
        param.put("name", "appOrder.getOrderPage");
        param.put("version", "1.0");
        param.put("format", "json");
        param.put("app_key", "app_id_2");
        param.put("timestamp", System.currentTimeMillis());
        param.put("token", Constant.token);
        param.put("nonce", AppUtils.getUUID());
        param.put("data", new Gson().toJson(dataBean));
        param.put("sign", buildSign(param, Constant.sign));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        return new Gson().toJson(param);
    }



    //带token请求
    public static RequestBody getParams(PhoneBean phoneBean, String name, String token) {
        HashMap<String, Object> param = getPublicParams(name, new Gson().toJson(phoneBean),token);
        param.put("sign", buildSign(param, Constant.sign));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        return body;
    }

    //直接Map转String
    public static RequestBody getParams(String beans, String name, String token) {
        HashMap<String, Object> param = getPublicParams(name, beans,token);
        param.put("sign", buildSign(param, Constant.sign));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        return body;
    }

    //不带token
    public static RequestBody getParams(PhoneBean phoneBean,String name) {
        HashMap<String, Object> param = getPublicParams(name, new Gson().toJson(phoneBean));
        param.put("sign", buildSign(param, Constant.sign));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
        return body;
    }

    //公共参数，不带token
    private static HashMap<String, Object> getPublicParams(String name, String data) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("data", data);
        param.put("name", name);
        param.put("version", "1.0");
        param.put("format", "json");
        param.put("app_key", "app_id_2");
        param.put("timestamp", System.currentTimeMillis());
        param.put("nonce", AppUtils.getUUID());
        return param;
    }

    //公共参数，带token
    private static HashMap<String, Object> getPublicParams(String name, String data, String token) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("data", data);
        param.put("name", name);
        param.put("token", token);
        param.put("version", "1.0");
        param.put("format", "json");
        param.put("app_key", "app_id_2");
        param.put("timestamp", System.currentTimeMillis());
        param.put("nonce", AppUtils.getUUID());
        return param;
    }

    //按字母排序后加密，返回sign
    public static String buildSign(Map<String, ?> paramsMap, String secret) {
        Set<String> keySet = paramsMap.keySet();
        List<String> paramNames = new ArrayList<String>(keySet);
        Collections.sort(paramNames);
        List<String> list = new ArrayList<>();
        for (String paramName : paramNames) {
            String key = paramName;
            String value = paramsMap.get(paramName).toString();
            if (!key.equals("") && !value.equals("")) {
                list.add(key + "=" + (value != null ? value : ""));
            }
        }
        String source = join(list) + secret;
        return EncoderUtils.encoder(source);
    }

    private static String join(List<String> array) {
        if (array.size() == 0) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.size(); i++) {
            if (i == array.size() - 1) {
                buffer.append(array.get(i));
            } else {
                buffer.append(array.get(i) + "&");
            }
        }

        return buffer.toString();
    }

}
