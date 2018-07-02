package com.example.libcore.net;

import android.content.Context;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpRetrofit {

    private GsonConverterFactory gsonConverterFactory;
    private RxJavaCallAdapterFactory rxJavaCallAdapterFactory;
    private static Retrofit retrofit;
    private static volatile HttpRetrofit mHttpRetrofit;
    private static final int CONNECT_TIME = 6_1000; // ms
    private static final int READ_TIME_OUT = 6_1000; // ms
    private static final int WRITE_TIME_OUT = 6_1000; // ms
    private Context mContext;
    private String mBaseUrl;
    private int mConnectTimeout;
    private int mReadTimeOut;
    private int mWriteTimeOut;
    private boolean mInit;
    private OkHttpClient mOkHttpClient;
    private ArrayList<Interceptor> mInterceptor = new ArrayList<>();
    private ArrayList<Interceptor> mNetworkInterceptor = new ArrayList<>();
    private final AtomicBoolean mStarted = new AtomicBoolean(false);

    public HttpRetrofit initConfig(RetrofitConfig retrofitConfig) {
        mNetworkInterceptor.add(new LoggerInterceptor());
        if (retrofitConfig == null) {
            return this;
        }
        synchronized (this) {
            if (mInit) {
                return this;
            }
        }
        mInit = true;
        mBaseUrl = retrofitConfig.getBaseUrl();
        mConnectTimeout = retrofitConfig.getConnectionTimeout();
        mReadTimeOut = retrofitConfig.getReadTimeout();
        mWriteTimeOut = retrofitConfig.getWriteTimeout();
        return this;
    }

    public static HttpRetrofit getInstance(Context context) {
        if (mHttpRetrofit == null) {
            synchronized (HttpRetrofit.class) {
                if (mHttpRetrofit == null) {
                    mHttpRetrofit = new HttpRetrofit(context);
                }
            }
        }
        return mHttpRetrofit;
    }

    /**
     * 添加app拦截器
     */
    public HttpRetrofit addInterceptor(Interceptor interceptor) {
        mInterceptor.add(interceptor);
        return this;
    }

    /**
     * 添加网络拦截器
     */
    public HttpRetrofit addNetworkInterceptor(Interceptor interceptor) {
        mNetworkInterceptor.add(interceptor);
        return this;
    }

    /**
     * 取消所有请求
     */
    public void cancelAllRequest() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    /**
     * application初始化网络组件
     */
    public void startUp() {
        if (mStarted.compareAndSet(false, true)) {
            gsonConverterFactory = GsonConverterFactory.create();
            rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
            //创建一个Retrofit对象，并且指定api的域名：
            mOkHttpClient = getDefaultHttpClient();
            retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(mBaseUrl)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();
        }
    }

    private HttpRetrofit(Context context) {
        this.mContext = context;
    }

    /**
     * 获取接口服务对象
     */
    public <T> T getObj(Class<T> t) {
        if (retrofit != null) {
            return retrofit.create(t);
        }
        return null;
    }

    private OkHttpClient getDefaultHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : mInterceptor) {
            builder.addInterceptor(interceptor);
        }
        for (Interceptor interceptor : mNetworkInterceptor) {
            builder.addNetworkInterceptor(interceptor);
        }
        builder.retryOnConnectionFailure(true);
        // set http cache缓存目录
        builder.cache(new CacheProvider(mContext).provideCache());
        //time out
        builder.connectTimeout(mConnectTimeout == 0 ? CONNECT_TIME : mConnectTimeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(mReadTimeOut == 0 ? READ_TIME_OUT : mReadTimeOut, TimeUnit.MILLISECONDS);
        builder.writeTimeout(mWriteTimeOut == 0 ? WRITE_TIME_OUT : mWriteTimeOut, TimeUnit.MILLISECONDS);
        return builder.build();
    }

}
