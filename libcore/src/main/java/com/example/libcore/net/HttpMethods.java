package com.example.libcore.net;


import android.content.Context;

import com.trello.rxlifecycle.components.RxActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import org.greenrobot.eventbus.EventBus;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class HttpMethods {
    private static final String TAG = "HttpMethods";
    private static volatile HttpMethods sInstance;
    private static Context mContext;

    private HttpMethods(){}

    public static HttpMethods getInstance() {
        if (sInstance == null) {
            synchronized (HttpMethods.class) {
                sInstance = new HttpMethods();
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public static class Functions<T> implements Func1<MAPIResult<T>, Object> {
        @Override
        public T call(MAPIResult<T> mapiResult) {
            if (!mapiResult.isSuccess()) {
                throw new APIException(mapiResult.getStatus(), mapiResult.getMessage());
            }
            EventBus.getDefault().post(mapiResult);
            return mapiResult.getData();
        }
    }

    public static class FunctionsStart<T> implements Func1<MAPIResult<T>, Object> {
        @Override
        public MAPIResult<T> call(MAPIResult<T> mapiResult) {
            if (!mapiResult.isSuccess()) {
                throw new APIException(mapiResult.getStatus(), mapiResult.getMessage());
            }
            return mapiResult;
        }
    }

    public <T> void onSub(Observable<T> map, final ProgressSubscriber<T> progress, RxActivity activity) {
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            progress.onCompleted();
            return;
        }
        map.subscribeOn(Schedulers.io())
                .compose(activity.<T>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progress.progressLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progress);
    }

    public <T> void onSub(Observable<T> map, final ProgressSubscriber<T> progress, RxFragment fragment) {
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            progress.onCompleted();
            return;
        }
        map.subscribeOn(Schedulers.io())
                .compose(fragment.<T>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //指定在主线程中执行
                        progress.progressLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progress);
    }
    public <T> void onSub(Observable<T> map, final ProgressSubscriber<T> progress, RxFragmentActivity fragmentActivity) {
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            progress.onCompleted();
            return;
        }
        map.subscribeOn(Schedulers.io())
                .compose(fragmentActivity.<T>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progress.progressLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progress);
    }

    public <T> void onSub(Observable<T> map, final ProgressSubscriber<T> progress) {
        if (!NetWorkUtil.isNetworkConnected(mContext)) {
            progress.onCompleted();
            return;
        }
        map.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progress.progressLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progress);
    }
}