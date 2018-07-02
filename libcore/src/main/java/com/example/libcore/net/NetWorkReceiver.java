package com.example.libcore.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


public class NetWorkReceiver extends BroadcastReceiver {

    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private static NetWorkReceiver receiver = null;
    public static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            synchronized (BroadcastReceiver.class) {
                if (receiver == null) {
                    receiver = new NetWorkReceiver();
                }
            }
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        HttpNetUtil.INSTANCE.setConnected(context);
    }

    public static  void registerNetWorkReceiver(Context mContext) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext()
                .registerReceiver(getReceiver(), intentFilter);
    }

    public static void unRegisterNetWorkReceiver(Context mContext){
        mContext.unregisterReceiver(getReceiver());
    }
}