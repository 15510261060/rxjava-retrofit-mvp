package com.example.libcore.reference.weakreference;

import java.lang.ref.WeakReference;


public abstract class BaseWeakReference<T> {
    protected WeakReference<T> mWeakReference;

    public BaseWeakReference(T reference) {
        mWeakReference = new WeakReference<>(reference);
    }

    public abstract boolean referenceActive();

    public T getReference() {
        return mWeakReference.get();
    }
}
