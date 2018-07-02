package com.example.libcore.net;


import com.example.libcore.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class LoggerInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        printRequest(request);
        Response response = chain.proceed(request);
        printResponse(response);
        return response;
    }

    private void printRequest(Request request) {
        String method = request.method();
        String url = request.url().toString();
        String body = "";
        if (request.body() != null) {
            body = request.body().toString();
        }
        LogUtil.e("request-method:" + method + "url:" + url + "body" + body);

    }

    private void printResponse(Response response) {

    }
}
