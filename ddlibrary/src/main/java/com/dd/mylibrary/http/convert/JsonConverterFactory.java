package com.dd.mylibrary.http.convert;
import com.dd.mylibrary.http.utils.BRequest;
import com.dd.mylibrary.http.utils.BResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author：fmd on 16/8/22 16
 *
 */
public class JsonConverterFactory extends Converter.Factory{

    private static final String TAG = "JsonConverterFactory/debug";

    public static JsonConverterFactory create() {
        return create(new Gson());
    }

    public static JsonConverterFactory create(Gson gson) {
        return new JsonConverterFactory(gson);

    }

    private final Gson gson;

    private JsonConverterFactory(Gson gson) {
        if (gson == null){
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    /**
     * 服务器返回的数据格式<? extends BResponse>  或者 String
     * @param type
     * @param annotations
     * @param retrofit
     * @return
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<?> typeClass = getRawType(type);
        Type superType = typeClass.getGenericSuperclass();
        TypeAdapter<?> adapter = null;
        if(typeClass == BResponse.class || superType == BResponse.class){
            //返回值转换成BResponse
            adapter = gson.getAdapter(TypeToken.get(type));
        }else if(superType == Object.class){

            throw new IllegalStateException("encrypt response superType must extends BaseBean or PResponseBean or just String!");
        }
        return new JsonResponseBodyConverter<>(adapter);  //响应
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Class<?> typeClass = getRawType(type);
        Type superType = typeClass.getGenericSuperclass();
        TypeAdapter<?> adapter = null;
        if(typeClass == RequestBody.class){
        }else if(superType == BRequest.class){
            //返回值转换成Bean
            adapter = gson.getAdapter(TypeToken.get(type));
        }else if(typeClass != Map.class && typeClass != HashMap.class){

            throw new IllegalStateException("encrypt positionRequest params must be map or extends BaseRequestBean!");
        }
        return new JsonRequestBodyConverter<>(adapter); //请求
    }

    public static Class<?> getRawType(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (type instanceof Class<?>) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;

            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        }

        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }
}
