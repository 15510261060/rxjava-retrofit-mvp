package com.example.libcore.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

    // 获得存储卡的路径
    private static String sdpath = Environment.getExternalStorageDirectory() + "/";
    private final static String savePath = sdpath + "download";
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private final static String saveFileName = savePath + "athena.apk";
    private static Context context;
    private static int progress;
    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_OVER:
                    installApk(context);
                    break;

            }
        }
    };

    private static ProgressBar mProgressBar;
    private static Thread downLoadThread;
    private static TextView tv_size;
    private static TextView percent_size;
    private static boolean interceptFlag = false;

    private AppUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    public static String getSharePreferencesDir(Context context) {
        if (context == null) {
            return null;
        }
        String format = "/%s/%s/%s/shared_prefs";
        String dir = String.format(format, "data", "data", context.getPackageName());
        return dir;
    }

    public static int getTargetSdkVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            return applicationInfo.targetSdkVersion;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取平台
     */
    public static int getCurrentSdkVersion(Context context) {
        return Build.VERSION.SDK_INT;
    }

    public static void exit() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * 获取app的PublicKey
     */
    public static PublicKey publicKey(Context context) {
        ObjectUtils.requireNoNull(context, "context must be not null.");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            CertificateFactory certificateFactory
                    = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packageInfo.signatures[0].toByteArray());
            Certificate certificate = certificateFactory.generateCertificate(byteArrayInputStream);
            X509Certificate x509Certificate = (X509Certificate) certificate;
            return x509Certificate.getPublicKey();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PackageInfo getPackInfo(Context context) {
        ObjectUtils.requireNoNull(context, "context==null");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            return packageInfo;
        } catch (NameNotFoundException e) {
            LogUtil.e("getPackageInfo error",e);
        }
        return null;
    }

    public static Signature[] getApkSignature(Context context, String path) {
        ObjectUtils.requireNoNull(context, "context==null");
        ObjectUtils.requireNoNull(path, "path==null");
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_SIGNATURES);
            if (packageInfo == null) {
                return null;
            }
            Signature[] signatures = packageInfo.signatures;
            return signatures;
        } catch (Throwable throwable) {
        }
        return null;
    }

    public static boolean verifyHostApk(Context context, String path) {
        ObjectUtils.requireNoNull(context, "context==null");
        ObjectUtils.requireNoNull(path, "path==null");
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            if (packageInfo == null) {
                return false;
            }
            ActivityInfo[] activities = packageInfo.activities;
            return activities != null && activities.length > 0;
        } catch (Throwable throwable) {
        }
        return false;
    }

    /**
     * 获取清单文件中的元数据
     */
    public static <T> T getMetaData(Context context, String key, T defaultValue) {
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null && bundle.containsKey(key)) {
                T t = (T) bundle.get(key);
                return t;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getAppIconResId(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.applicationInfo.icon;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 检测app是否被安装在手机上
     */
    public static boolean appIsInstalled(Context context, String packageName) {
        ObjectUtils.requireNoNull(context, "context must be not null");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);
            if (packageInfos != null) {
                for (PackageInfo packageInfo : packageInfos) {
                    if (packageInfo != null) {
                        boolean installed = TextUtils.equals(packageInfo.packageName, packageName);
                        if (installed) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResolveInfo queryActivity(Context context, Intent intent) {
        if (context == null || intent == null)
            return null;
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        @SuppressLint("WrongConstant") List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent,
                PackageManager.GET_INTENT_FILTERS);
        if (resolveInfoList == null || resolveInfoList.size() == 0)
            return null;
        int size = resolveInfoList.size();
        if (size == 1)
            return resolveInfoList.get(0);
        String appPackageName = context.getApplicationContext().getPackageName();
        for (int i = 0; i < size; i++) {
            ResolveInfo resolveInfo = resolveInfoList.get(i);
            String activityName = resolveInfo.activityInfo.name;
            if (TextUtils.isEmpty(activityName))
                continue;
            if (activityName.startsWith(appPackageName)) {
                return resolveInfo;
            }
        }
        return resolveInfoList.get(0);
    }

    /**
     * 重启app
     */
    public static void restart(Context context) {
        restart(context, 0);
    }

    public static void startLauncher(Context context) {
        Intent intent = getLauncherIntent(context);
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    private static Intent getLauncherIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(context.getPackageName());
        ResolveInfo resolveInfo = queryActivity(context, intent);
        if (resolveInfo == null) {
            return null;
        }
        try {
            intent.setClass(context, Class.forName(resolveInfo.activityInfo.name));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return intent;
    }

    public static void restart(Context context, int delay) {
        try {
            Intent intent = getLauncherIntent(context);
            if (intent == null) {
                return;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
            Process.killProcess(Process.myPid());
        } catch (Throwable throwable) {
            // ignore
            throwable.printStackTrace();
        }
    }


    /**
     * 获取当前进程名
     */
    public static String getProcessName(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return "";
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
            if (runningAppProcessInfos != null && !runningAppProcessInfos.isEmpty()) {
                int pid = Process.myPid();
                if (runningAppProcessInfos instanceof RandomAccess) {
                    int size = runningAppProcessInfos.size();
                    for (int i = 0; i < size; i++) {
                        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcessInfos.get(i);
                        if (pid == runningAppProcessInfo.pid) {
                            return runningAppProcessInfo.processName;
                        }

                    }
                } else {
                    Iterator<ActivityManager.RunningAppProcessInfo> iterator = runningAppProcessInfos.iterator();
                    while (iterator.hasNext()) {
                        ActivityManager.RunningAppProcessInfo rap = iterator.next();
                        if (pid == rap.pid) {
                            return rap.processName;
                        }
                    }
                }
            }
        } catch (RuntimeException ex) {
            // ignore
            ex.printStackTrace();
        }
        return "";
    }


    /**
     * 安装apk
     */
    private static void installApk(Context context) {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkfile);
            i.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");

        }
        context.startActivity(i);
//        ActivityManager.popAll();

        System.exit(0);

    }

    private static String getFileSize(int size) {
        if (size <= 0) {
            return "0";
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
        float temp = (float) size / 1024;
        if (temp >= 1024) {
            return df.format(temp / 1024) + "MB";
        } else {
            return df.format(temp) + "KB";
        }

    }

}
