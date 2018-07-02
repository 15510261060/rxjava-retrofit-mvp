package com.example.libcore.net;


public interface SubscriberOnNextListener<T> {
    void onSuccess(T t);

    void onError(int code, String message);

    void onStartUp();

    void onCompleted();

    void onLoading(boolean isShowLoading);
}
