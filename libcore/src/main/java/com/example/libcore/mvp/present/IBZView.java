package com.example.libcore.mvp.present;


import com.example.libcore.mvp.view.IView;

/**
* 业务请求回调
* @auth hebin
* created at  
*/
public interface IBZView<A> extends IView {
    void onSuccess(A action, Object o);

    void onError(A action, int code, String message);

    void onStartUp(A action);

    void onCompleted(A action);

}
