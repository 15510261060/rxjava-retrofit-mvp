package com.example.libcore.net;

import android.content.Context;


import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import rx.Observable;


/**
 * Class description goes here.
 * <p>
 * Created by hebin on 17/5/3.
 */


public class RetrofitManager implements INetWorkingProtocol {

    private HttpRetrofit mHttpRetrofit;

    private HttpMethods mHttpMethods;

    private static RetrofitManager instance;

    private RetrofitManager(){}

    public synchronized static RetrofitManager getInstance(){
        if(null == instance){
            instance = new RetrofitManager();
        }
        return instance;
    }

    public void init(Context context, RetrofitConfig retrofitConfig) {
        HttpRetrofit.getInstance(context)
                .initConfig(retrofitConfig);
        HttpMethods.getInstance().init(context);
        mHttpRetrofit = HttpRetrofit.getInstance(context);
        mHttpMethods = HttpMethods.getInstance();
    }



    @Override
    public <T> T getNetService(Class<T> clazz) {
        return mHttpRetrofit.getObj(clazz);
    }

    @Override
    public void onRequest(Observable observable, ProgressSubscriber progressSubscriber, RxFragmentActivity rxFragmentActivity) {
        Observable map = observable.map(new HttpMethods.Functions());
        mHttpMethods.onSub(map,progressSubscriber,rxFragmentActivity);
    }


    @Override
    public void onRequest(RequestEntry request) {

    }


}
