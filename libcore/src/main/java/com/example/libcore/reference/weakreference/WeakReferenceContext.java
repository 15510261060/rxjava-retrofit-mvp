package com.example.libcore.reference.weakreference;


import android.content.Context;

import com.example.libcore.utils.Utils;

public class WeakReferenceContext extends BaseWeakReference<Context> {

    public WeakReferenceContext(Context context) {
        super(context);
    }

    @Override
    public boolean referenceActive() {
        return Utils.contextIsValidate(getReference());
    }
}
