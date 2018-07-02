package com.example.libcore.net;

import android.util.SparseArray;

/**
 * 网络本地常量配置表
 */
public class Constants {
    private static final SparseArray<String> sMessageTable = new SparseArray<>();
    public static final int NET_CONNECT_ERROR = 1;
    public static final int UNKNOWN = 2;
    public static final int JSON_SYNTAX_EXCEPTION = 3;
    public static final int JSON_PARSE_EXCEPTION = 4;
    public static final int NET_TIMEOUT = 5;
    public static final int NO_NET = 6;
    public static final int OBJECT_NULL = 7;
    public static final int ILLEGAL_PARAMS = 8;
    public static final int AUTH = 9;

    static {
        sMessageTable.put(NET_CONNECT_ERROR, "网络连接错误");
        sMessageTable.put(UNKNOWN, "数据异常");
        sMessageTable.put(JSON_SYNTAX_EXCEPTION, "数据格式异常");
        sMessageTable.put(JSON_PARSE_EXCEPTION, "数据解析异常");
        sMessageTable.put(NET_TIMEOUT, "网络连接超时");
        sMessageTable.put(NO_NET, "当前没有可用的网络,请设置网络");
        sMessageTable.put(OBJECT_NULL, "空指针异常");
        sMessageTable.put(ILLEGAL_PARAMS,"非法参数异常");
    }

    private Constants() {
    }

    public static String getMessage(int code) {
        return sMessageTable.get(code);
    }

}
