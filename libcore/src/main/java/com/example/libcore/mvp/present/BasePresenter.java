package com.example.libcore.mvp.present;


import android.content.Context;

import com.example.libcore.mvp.BZVPCallback;
import com.example.libcore.net.ProgressSubscriber;
import com.example.libcore.reference.weakreference.BaseWeakReference;
import com.example.libcore.reference.weakreference.WeakReferenceContext;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.LinkedList;

import rx.Subscription;

public abstract class BasePresenter implements IBasePresenter {

    private LinkedList<Subscription> mSubscriptions = new LinkedList<>();
    protected IBZView mView; // 视图接口
    protected BaseWeakReference<?> mReference; // 操作的上下文

    public BasePresenter(BaseWeakReference<?> reference, IBZView view) {
        this.mReference = reference;
        this.mView = view;
    }

    public BasePresenter(RxFragmentActivity reference, IBZView view) {
        this.mReference = new WeakReferenceContext(reference);
        this.mView = view;
    }

    protected ProgressSubscriber createProgressSubscriber(Context context, Enum action, IBZView iView,boolean isDialog, boolean cancelable){
        return new ProgressSubscriber(context,new BZVPCallback<Enum,IBZView>(action,iView,isDialog,cancelable));
    }

    public void resetView() {
        this.mView = null;
    }

    protected <T>T get(Class<T> type) {
        if (mReference == null) {
            return null;
        }
        if (!mReference.referenceActive()) {
            return null;
        }
        Object o = mReference.getReference();
        return (T) o;
    }

    protected final void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }
        synchronized (mSubscriptions) {
            if (!mSubscriptions.contains(subscription)) {
                mSubscriptions.add(subscription);
            }
        }
    }

    public void initialize() {

    }

    public void resume() {

    }

    public void pause() {

    }

    public void destroy() {
        synchronized (mSubscriptions) {
            if (mSubscriptions == null || mSubscriptions.isEmpty()) {
                return;
            }
            for (Subscription subscription : mSubscriptions) {
                if (subscription != null && !subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }
            }
            mSubscriptions.clear();
        }
    }



}
