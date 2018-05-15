package com.dd.mylibrary.http.convert;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * author：fmd on 16/9/28
 * use:
 */
public class StringConvertFactory extends Converter.Factory{

    public static StringConvertFactory create() {
        return new StringConvertFactory();
    }

    @Override
    public Converter<ResponseBody, String> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        Class<?> typeClass = JsonConverterFactory.getRawType(type);
        if(typeClass != String.class){
            return null;
        }
        return new StringResponseBodyConverter();
    }

    public class StringResponseBodyConverter implements Converter<ResponseBody , String> {

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
}
