package com.example.libcore.mvp;


import com.example.libcore.mvp.present.IBZView;
import com.example.libcore.net.SubscriberOnNextListener;

/**
* 网络请求回调VP中转
* @auth hebin
* created at  
*/
public class BZVPCallback<A, T extends IBZView> implements SubscriberOnNextListener {
    private A mAction;
    private T mView;
    private boolean isDialog;
    private boolean cancelable;

    public BZVPCallback(A action, T view,boolean isDialog, boolean cancelable) {
        this.mAction = action;
        this.mView = view;
        this.isDialog = isDialog;
        this.cancelable = cancelable;
    }

    @Override
    public void onSuccess(Object o) {
        if (mView != null) {
            mView.onSuccess(mAction, o);
            mView.dismissLoading();
        }
    }

    @Override
    public void onError(int code, String msg) {
        if (mView != null) {
            mView.onError(mAction, code, msg);
            mView.dismissLoading();
        }
    }

    @Override
    public void onStartUp() {
        if (isDialog){
            mView.showLoading(isDialog,cancelable);
        }
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.onCompleted(mAction);
            mView.dismissLoading();
        }
    }

    @Override
    public void onLoading(boolean isShowLoading) {
        if (mView != null) {
            mView.onStartUp(mAction);
        }
    }


}
