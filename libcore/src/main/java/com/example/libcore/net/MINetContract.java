package com.example.libcore.net;


import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import rx.Observable;

/**
 * 中间层网络服务功能
 * <p>
 * Created by hebin on 17/5/11.
 */

public interface MINetContract {

    public <T> T getNetService(Class<T> clazz);

    public void onRequest(Observable map,
                          ProgressSubscriber progressSubscriber,
                          RxFragmentActivity rxFragmentActivity);

}
