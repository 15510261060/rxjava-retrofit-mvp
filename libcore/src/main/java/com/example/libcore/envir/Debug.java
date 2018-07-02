package com.example.libcore.envir;

import android.content.Context;

import com.example.libcore.utils.AppUtils;
import com.example.libcore.utils.LogUtil;


/**
 * Created by hebin 开发debug模式管理,不要使用系统的BuildConfig.Debug
 * 在aar release模式下永远为true
 */

public class Debug {
    private static final String EXTRA_DEBUG = "debug";
    private static boolean isDebug = false;

    public static void setDebug(boolean isDebug) {
        Debug.isDebug = isDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void init(Context context) {
        isDebug = AppUtils.getMetaData(context, EXTRA_DEBUG, true);
        if (isDebug) {
            LogUtil.e("debug mode:" + isDebug);
        }
    }
}
