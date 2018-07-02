package com.example.libcore.envir;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import com.example.libcore.utils.AppUtils;
import com.example.libcore.utils.Utils;

import java.io.File;


/**
 * Created by hebin
 * <p/>
 * 用于存储设备信息&& 屏幕信息 && 单例 等.在app 初始化调用
 */
public class AppInfo {
    private static final String TAG = AppInfo.class.getSimpleName();
    private Application app;
    private Path mPath;
    private static volatile AppInfo instance = null;
    private boolean isDebug = true;               //当前是debug 模式
    public static String screen = "";           // 屏幕信息
    public static float density;                // 屏幕密度
    public static float scaledDensity;
    public static int screenResolution;         // 屏幕分辨率
    public static int screenWidthForPortrait;   // 屏幕宽度
    public static int screenHeightForPortrait;  // 屏幕高度
    public static int screenStatusBarHeight;    //状态栏高度
    public static boolean needRemoveTitleLikeiOS;
    public static String IMEI;     //IMEI
    public String operator;  //移动网络操作码
    public String mac;       //mac 地址
    public String versionName;  //版本名称
    public int versionCode;     //版本号
    public String packageNames; //包名
    public static String cityCode;
    //请求参数
    private static String model;
    private static String brand;
    private static int sdkVersion;
    public static String requestParm;
    private static final String EXTRA_TITLE_HEIGHT = "titleHeight";
    private static final String EXTRA_WIDTH = "width";
    private static final String EXTRA_HEIGHT = "height";
    private static final String EXTRA_BT_HEIGHT = "headerBtHeight";
    private static final String EXTRA_BT_WIDTH = "headerBtWidth";
    private static final String EXTRA_BT_MARGIN = "headerBtMargin";
    private static final String EXTRA_BT_PADDING = "headerBtPadding";
    private static final String LOADING_WIDTH = "loadingwidth";
    private static final String LOADING_HEIGHT = "loadingheight";
    private static final String TITLE_TEXT_SIZE = "titletextsize";
    private static final String TITLE_TEXTBT_MARGIN = "titletextbtmargin";
    private static int base_width;
    private static int base_height;
    private static int base_bt_width;
    private static int base_bt_height;
    private static int base_bt_margin;
    private static int base_bt_padding;
    private static int loading_width;
    private static int loading_height;
    private static int title_text_size;
    private static int title_textbt_margin;
    public String mAPPLabel;


    private float mBaseX, mBaseY;
    private static final int DEFAULT_TITLE_HEIGHT = 100; // px
    private int mTitleHeight = DEFAULT_TITLE_HEIGHT;
    private DisplayMetrics metrics;

    private boolean isBaseValidate() {
        return base_width != 0 && base_height != 0;
    }

    public float getX(int px) {
        if (!isBaseValidate()) {
            return px;
        }
        return mBaseX * px;
    }

    public float getY(int px) {
        if (!isBaseValidate()) {
            return px;
        }
        return mBaseY * px;
    }

    public float getTitleHeight() {
        if (!isBaseValidate()) {
            return mTitleHeight;
        }
        return mBaseY * mTitleHeight;
    }

    public float getHeaderBtWidth() {
        if (!isBaseValidate()) {
            return base_bt_width;
        }
        return mBaseY * base_bt_width;
    }

    public float getHeaderBtHeigh() {
        if (!isBaseValidate()) {
            return base_bt_height;
        }
        return mBaseY * base_bt_height;
    }

    public float getHeaderTextSize() {
        if (!isBaseValidate()) {
            return title_text_size;
        }
        return mBaseY * title_text_size;
    }

    public float getHeaderBtMargin() {
        if (!isBaseValidate()) {
            return base_bt_margin;
        }
        return mBaseY * base_bt_margin;
    }

    public float getHeaderBtPadding() {
        if (!isBaseValidate()) {
            return base_bt_padding;
        }
        return mBaseY * base_bt_padding;
    }

    public float getLoadingWidth() {
        if (!isBaseValidate()) {
            return loading_width;
        }
        return mBaseY * loading_width;
    }

    public float getLoadingHeight() {
        if (!isBaseValidate()) {
            return loading_height;
        }
        return mBaseY * loading_height;
    }

    public float getTitletextbtmargin(){
        if (!isBaseValidate()) {
            return title_textbt_margin;
        }
        return mBaseY * title_textbt_margin;
    }

    private AppInfo() {
    }

    public static AppInfo getInstance() {
        if (null == instance) {
            synchronized (AppInfo.class) {
                if (null == instance) {
                    instance = new AppInfo();
                }
            }
        }
        return instance;
    }

    public void init(Application app) {
        this.app = app;
        mPath = new Path(app);
        mPath.init();
        initDeviceInfo();
        initScreenInfo();
        initApplicationInfo();
    }

    public File getImageCacheDir() {
        if (Utils.hasWriteSdcardPermission(app)) {
            return mPath.getCacheDir(Path.IMAGE);
        } else {
            return mPath.getInnerCacheDir(Path.IMAGE);
        }
    }

    public File getCrashCacheDir() {
        if (Utils.hasWriteSdcardPermission(app)) {
            return mPath.getCacheDir(Path.CRASH);
        }
        return mPath.getInnerCacheDir(Path.CRASH);
    }

    public File getHttpCacheDir() {
        if (Utils.hasWriteSdcardPermission(app)) {
            return mPath.getCacheDir(Path.HTTP_CACHE);
        } else {
            return mPath.getInnerCacheDir(Path.HTTP_CACHE);
        }
    }

    public File getBlockCacheDir() {
        if (Utils.hasWriteSdcardPermission(app)) {
            return mPath.getCacheDir(Path.BLOCK);
        } else {
            return mPath.getInnerCacheDir(Path.BLOCK);
        }
    }

    private void initApplicationInfo() {
        mAPPLabel = AppUtils.getAppName(app);
    }


    private void initDeviceInfo() {

        try {
            PackageInfo info = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
            versionName = info.versionName;
            versionCode = info.versionCode;
            packageNames = info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    private void initScreenInfo() {
        Context context = app;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        this.metrics = metric;
        wm.getDefaultDisplay().getMetrics(metric);
        density = metric.density;
        scaledDensity = metric.scaledDensity;
        screenWidthForPortrait = metric.widthPixels;
        screenHeightForPortrait = metric.heightPixels;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null) {
                int count = 0;
                if (bundle.containsKey(EXTRA_WIDTH)) {
                    try {
                        base_width = bundle.getInt(EXTRA_WIDTH);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(EXTRA_HEIGHT)) {
                    try {
                        base_height = bundle.getInt(EXTRA_HEIGHT);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(EXTRA_BT_HEIGHT)) {
                    try {
                        base_bt_height = bundle.getInt(EXTRA_BT_HEIGHT);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(EXTRA_BT_WIDTH)) {
                    try {
                        base_bt_width = bundle.getInt(EXTRA_BT_WIDTH);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(EXTRA_BT_MARGIN)) {
                    try {
                        base_bt_margin = bundle.getInt(EXTRA_BT_MARGIN);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(EXTRA_BT_PADDING)) {
                    try {
                        base_bt_padding = bundle.getInt(EXTRA_BT_PADDING);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(LOADING_WIDTH)) {
                    try {
                        loading_width = bundle.getInt(LOADING_WIDTH);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(LOADING_HEIGHT)) {
                    try {
                        loading_height = bundle.getInt(LOADING_HEIGHT);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(TITLE_TEXT_SIZE)) {
                    try {
                        title_text_size = bundle.getInt(TITLE_TEXT_SIZE);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (bundle.containsKey(TITLE_TEXTBT_MARGIN)) {
                    try {
                        title_textbt_margin = bundle.getInt(TITLE_TEXTBT_MARGIN);
                        count++;
                    } catch (NumberFormatException ex) {

                    }
                }
                if (count == 10) {
                    mBaseX = change(screenWidthForPortrait * 1.0f / base_width);
                    mBaseY = change(screenHeightForPortrait * 1.0f / base_height);
                    if (bundle.containsKey(EXTRA_TITLE_HEIGHT)) {
                        try {
                            mTitleHeight = bundle.getInt(EXTRA_TITLE_HEIGHT);
                        } catch (NumberFormatException ex) {

                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        screenResolution = metric.widthPixels * metric.heightPixels;
        screen = metric.widthPixels + "*" + metric.heightPixels;
        screenStatusBarHeight = Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
        model = android.os.Build.MODEL;
        brand = android.os.Build.BRAND;
        sdkVersion = android.os.Build.VERSION.SDK_INT;
        requestParm = brand + "-" + model + "-" + sdkVersion + "," + screenWidthForPortrait + "x" + screenHeightForPortrait;

    }
}
