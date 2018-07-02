package com.example.libcore.net;


import android.content.Context;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import rx.Observable;

/**
 * Class description goes here.
 * <p>
 * Created by hebin on 17/5/3.
 */

public interface INetWorkingProtocol {

    void init(Context context, RetrofitConfig retrofitConfig);

    <T> T getNetService(Class<T> t);

    void onRequest(Observable observable,
                   ProgressSubscriber progressSubscriber,
                   RxFragmentActivity rxFragmentActivity);

    void onRequest(RequestEntry request);
}
