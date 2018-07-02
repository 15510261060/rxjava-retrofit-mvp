package com.senon.mvpretrofitrx.mvp.header;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json")//设置允许请求json数据
                .build();
        return chain.proceed(builder.build());
    }
}
