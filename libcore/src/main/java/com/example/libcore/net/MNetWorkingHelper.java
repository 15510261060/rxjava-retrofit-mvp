package com.example.libcore.net;


import android.content.Context;

import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import okhttp3.Interceptor;
import rx.Observable;

/**
 * Class description goes here.
 * <p>
 * Created by hebin on 17/5/8.
 */

public class MNetWorkingHelper implements MINetContract {

    private static volatile MNetWorkingHelper instance;

    private Context mContext;

    private HttpRetrofit mHttpRetrofit;

    private MNetWorkingHelper(){

    }

    public static MNetWorkingHelper getInstance(){
        if(instance ==null){
            synchronized (MNetWorkingHelper.class){
                if(instance == null){
                    instance = new MNetWorkingHelper();
                }
            }
        }
        return instance;
    }


    public MNetWorkingHelper addInterceptor(Context context, Interceptor interceptor){
        mHttpRetrofit.addInterceptor(interceptor);
        return this;
    }

    public MNetWorkingHelper addNetWorkInterceptor(Context context, Interceptor interceptor){
        mHttpRetrofit.addNetworkInterceptor(interceptor);
        return this;
    }

    public void startUp(){
        mHttpRetrofit.startUp();
    }

    public HttpRetrofit init(Context context, RetrofitConfig retrofitConfig){
        RetrofitManager.getInstance().init(context, retrofitConfig);
        mHttpRetrofit = HttpRetrofit.getInstance(context);
        return mHttpRetrofit;
    }

    public <T> T getNetService(Class<T> clazz) {
        return RetrofitManager.getInstance().getNetService(clazz);
    }

    @Override
    public void onRequest(Observable map, ProgressSubscriber progressSubscriber, RxFragmentActivity rxFragmentActivity) {
        RetrofitManager.getInstance().onRequest(map, progressSubscriber, rxFragmentActivity);
    }

    public void onRequest(RequestEntry request){
        RetrofitManager.getInstance().onRequest(request);
    }

}
