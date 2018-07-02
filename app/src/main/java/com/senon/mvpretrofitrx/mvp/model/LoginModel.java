package com.senon.mvpretrofitrx.mvp.model;


import com.example.libcore.mvp.BaseMModel;
import com.example.libcore.net.ProgressSubscriber;
import com.senon.mvpretrofitrx.mvp.api.ApiService;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.HashMap;

import rx.Observable;

/**
 * LoginModel
 */
public class LoginModel extends BaseMModel<ApiService> {

    private static LoginModel sInstance;

    private LoginModel() {
        super(ApiService.class);
    }

    public static LoginModel getInstance() {
        if (sInstance == null) {
            synchronized (LoginModel.class) {
                if (sInstance == null) {
                    sInstance = new LoginModel();
                }
            }
        }
        return sInstance;
    }

    public void login(HashMap<String,String> map, ProgressSubscriber progressSubscriber,
                      RxFragmentActivity rxFragmentActivity) {
        Observable mObservable = mapiService.login(map);
        iNetContract.onRequest(mObservable, progressSubscriber, rxFragmentActivity);
    }

    public void logout(HashMap<String,String> map, ProgressSubscriber progressSubscriber,
                                       RxFragmentActivity rxFragmentActivity) {
        Observable mObservable = mapiService.logout(map);
        iNetContract.onRequest(mObservable, progressSubscriber, rxFragmentActivity);
    }
}
