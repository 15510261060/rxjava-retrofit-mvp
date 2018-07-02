package com.example.libcore.net;


import android.content.Context;

import com.example.libcore.utils.LogUtil;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
* @author
* create at
**/
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressDialogListener {
    private ProgressDialogHandler progressDialogHandler;
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private static final int NO_LOGIN = 5;

    public ProgressSubscriber(Context context, SubscriberOnNextListener mSubscriberOnNextListener) {
        super();
        progressDialogHandler = new ProgressDialogHandler(context, this, true);
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onStartUp();
        }
    }

    @Override
    public void onCompleted() {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (LogUtil.isDebug()) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            LogUtil.e("接口业务数据获取异常－onError:"+writer.toString());
        }
        if (e instanceof SocketTimeoutException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.NET_TIMEOUT, Constants.getMessage(Constants.NET_TIMEOUT));
            }
        } else if (e instanceof ConnectException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.NET_CONNECT_ERROR, Constants.getMessage(Constants.NET_CONNECT_ERROR));
            }
        } else if (e instanceof APIException) {
            if (mSubscriberOnNextListener != null) {
                APIException apiException = (APIException) e;
                int code = apiException.getCode();
                if (code == NO_LOGIN) {
                    return;
                }
                mSubscriberOnNextListener.onError(code, apiException.getMsg());
            }
        } else if (e instanceof JsonSyntaxException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.JSON_SYNTAX_EXCEPTION, Constants.getMessage(Constants.JSON_SYNTAX_EXCEPTION));
            }
        } else if (e instanceof JsonParseException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.JSON_PARSE_EXCEPTION, Constants.getMessage(Constants.JSON_PARSE_EXCEPTION));
            }
        } else if (e instanceof NullPointerException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.OBJECT_NULL, Constants.getMessage(Constants.OBJECT_NULL));
            }
        } else if (e instanceof IllegalArgumentException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.ILLEGAL_PARAMS, Constants.getMessage(Constants.ILLEGAL_PARAMS));
            }
        } else if (e instanceof EOFException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.UNKNOWN, e.getLocalizedMessage());
            }
        } else if (e instanceof IOException) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.UNKNOWN, e.getLocalizedMessage());
            }
        } else {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(Constants.UNKNOWN, Constants.getMessage(Constants.UNKNOWN));
            }
        }
        if (mSubscriberOnNextListener == null) {
            dimessProgressDialog();
        }
    }

    @Override
    public void onNext(T t) {
        if (null != mSubscriberOnNextListener) {
            mSubscriberOnNextListener.onSuccess(t);
        }
    }

    public void showProgressDialog() {
        if (progressDialogHandler != null)
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESSDIALOG).sendToTarget();
    }

    public void dimessProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DIMISS_PROGRESSDIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    public void progressLoading() {
        if (null != mSubscriberOnNextListener) {
            mSubscriberOnNextListener.onLoading(true);
        } else {
            showProgressDialog();
        }
    }

    @Override
    public void cancelEvent() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
