package com.example.libcore.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


public class ProgressDialogHandler extends Handler {
    private Context mContext;
    private ProgressDialogListener mListener;
    private boolean mCancelable;
    private ProgressDialog progressDialog;
    public static final int SHOW_PROGRESSDIALOG = 1;
    public static final int DIMISS_PROGRESSDIALOG = 2;


    public ProgressDialogHandler(Context context, ProgressDialogListener listener, boolean cancelable) {
        super();
        mCancelable = cancelable;
        mContext = context;
        mListener = listener;
    }

    public void initProgressDialog() {
        //TODO 按照需求设置ContentView
        if(progressDialog==null){
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(mCancelable);
            if (mCancelable) {
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (null != mListener) {
                            mListener.cancelEvent();
                        }
                    }
                });
            }
        }
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    private void setDimissProgressdialog(){

        if(null!=progressDialog&&progressDialog.isShowing()){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case SHOW_PROGRESSDIALOG:
                initProgressDialog();
                break;
            case DIMISS_PROGRESSDIALOG:
                setDimissProgressdialog();
                break;

        }

    }
}
