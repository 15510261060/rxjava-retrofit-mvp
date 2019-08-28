package com.senon.mvpretrofitrx.mvp;

import android.content.Context;

import com.example.libcore.envir.AppInfo;
import com.example.libcore.net.MNetWorkingHelper;
import com.example.libcore.net.RetrofitConfig;
import com.example.libcore.utils.ApplicationUtil;
import com.senon.mvpretrofitrx.mvp.api.Api;
import com.senon.mvpretrofitrx.mvp.header.HeaderInterceptor;

public class MVPApplication extends ApplicationUtil {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppInfo.getInstance().init(this);
        MNetWorkingHelper.getInstance().init(this, new RetrofitConfig.Builder()
                        .setBaseUrl(Api.baseUrl).build())
                .addInterceptor(new HeaderInterceptor())//添加header
                .startUp();
    }
}
