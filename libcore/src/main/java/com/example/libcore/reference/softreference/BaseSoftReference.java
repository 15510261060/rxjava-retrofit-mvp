package com.example.libcore.reference.softreference;

import java.lang.ref.SoftReference;


public abstract class BaseSoftReference<T> {
    private SoftReference<T> mSoftReference;

    public BaseSoftReference(T o) {
        mSoftReference = new SoftReference<>(o);
    }

    public T getReference() {
        return mSoftReference.get();
    }

    public abstract boolean referenceActive();
}
