package com.dd.mylibrary.http.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fangmingdong on 2016/12/27.
 * json tools
 */

public class JSONUtils {
    public static final String TAG = "JSONUtils/TAG";

    public static final int getInt(JSONObject json , String key){
        if(json == null){
            return 0;
        }
        try {
            if(json.has(key)) {
                return json.getInt(key);
            }
            return 0;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static final String getString(JSONObject json , String key){
        if(json == null){
            return "";
        }
        try {
            if(json.has(key)) {
                return json.get(key).toString();
            }
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final JSONObject getJSONObject(JSONObject json , String key){
        if(json == null){
            return new JSONObject();
        }
        try {
            if(json.has(key)) {
                return json.getJSONObject(key);
            }
            return new JSONObject();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static final long getLong(JSONObject json , String key){
        if(json == null){
            return 0L;
        }

        try{
            return NumberUtils.string2Long(json.get(key).toString());
        }catch (JSONException e){
            e.printStackTrace();
            return 0L;
        }
    }

    public static final void put(JSONObject json , String key , Object value){

        if(json == null)return;

        try {
            json.put(key , value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final JSONObject build(String jsonStr){
        if(TextUtils.isEmpty(jsonStr)){
            return new JSONObject();
        }else{
            try {
                return new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                return new JSONObject();
            }
        }
    }
}
