package com.example.libcore.envir;


import android.content.Context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AbsManager<T> {

    protected Context mCtx;

    private String currentKey;

    public synchronized void init(Context context) {
        this.mCtx = context;
    }

    private Map<String, T> map = new ConcurrentHashMap<>();

    public void add(String key, T t) {
        map.put(key, t);
    }

    public void remove(String key) {
        map.remove(key);
    }


    public void use(String key) {
        currentKey = key;
    }

    public T addAndUse(String key, T t) {
        use(key);
        add(key, t);
        return t;
    }

    public T getCurrentKey() {
        try {
            return map.get(currentKey);
        } catch (Exception e) {
            return null;
        }
    }
}
