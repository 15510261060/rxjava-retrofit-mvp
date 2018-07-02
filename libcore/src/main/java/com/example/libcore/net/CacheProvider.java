package com.example.libcore.net;


import android.content.Context;

import com.example.libcore.envir.AppInfo;

import okhttp3.Cache;

public class CacheProvider {
    private static final int HTTP_CACHE_SIZE = 10 * 240 * 1024; // 10M
    private Context mContext;

    public CacheProvider(Context context) {
        mContext = context;
    }

    public Cache provideCache() {//使用应用缓存文件路径，缓存大小为10MB
        return new Cache(AppInfo.getInstance().getHttpCacheDir(), HTTP_CACHE_SIZE);
    }

}
