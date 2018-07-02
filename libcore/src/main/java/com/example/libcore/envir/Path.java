package com.example.libcore.envir;


import android.content.Context;

import com.example.libcore.utils.FileUtils;

import java.io.File;

public class Path {
    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    public static final String SHARE_PREFS = "common";
    public static final String SHARE_PREFS_VERSION_UPDATE = "update";
    public static final String AVATAR = "avatar";
    public static final String CRASH = "crash";
    public static final String LOG = "log";
    public static final String DUMP = "dump";
    public static final String IMAGE = "image";
    public static final String HTTP_CACHE = "http_cache";
    public static final String BLOCK = "block";

    private Context mContext;

    public Path(Context context) {
        this.mContext = context;
    }

    public void init() {

    }

    public File getCacheDir(String dir) {
        return FileUtils.getCacheDir(mContext, AppInfo.getInstance().mAPPLabel.concat(File.separator).concat(dir));
    }

    public File getInnerCacheDir(String dir) {
        return FileUtils.getInternalCacheFileDir(mContext, dir);
    }
}
