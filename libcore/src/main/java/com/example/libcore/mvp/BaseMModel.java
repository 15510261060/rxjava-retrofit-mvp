package com.example.libcore.mvp;


import com.example.libcore.net.MINetContract;
import com.example.libcore.net.MNetWorkingHelper;

/**
 * Class description goes here.
 * <p>
 * Created by hebin on 17/5/11.
 */

public class BaseMModel<T>{

    protected MINetContract iNetContract;

    protected T mapiService;

    protected BaseMModel(Class<T> clazz) {
        iNetContract = MNetWorkingHelper.getInstance();
        mapiService = iNetContract.getNetService(clazz);
    }
}
