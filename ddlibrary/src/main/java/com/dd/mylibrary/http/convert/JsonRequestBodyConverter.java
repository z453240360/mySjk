package com.dd.mylibrary.http.convert;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * author：fmd on 16/8/22 16
 * use: 加密请求数据处理
 * 请求参数类型有三种Map,RequestBody,<? extends BaseRequestBean>
 * 方便进行统一加密
 */
public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final TypeAdapter<T> adapter;
    private static final String TAG = "JsonRequestBodyConverter";

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {

        if (value instanceof RequestBody) {

            return (RequestBody) value;
        }
        String content;
        if (adapter != null) {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = new JsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            content = buffer.readUtf8();
        } else if (value == null) {

            content = "";
        } else {

            Map paramMap = (Map) value;
            content = new JSONObject(paramMap).toString();
        }
//        if (BasicConfig.isEncrypt) {
//            if (content.contains("userCenter/updateUserInfo") || content.contains("communityCenter/proft/authAudit")) {
//            } else {
//                if (!TextUtils.isEmpty(content)) {
//                    content = DES3Utils.encryptMode(content);
//                }
//            }
//        }

        return RequestBody.create(MEDIA_TYPE, content);
    }
}
