package com.example.libcore.reference.weakreference;


import android.view.View;

import com.example.libcore.utils.Utils;

public class WeakReferenceView extends BaseWeakReference<View> {
    public WeakReferenceView(View view) {
        super(view);
    }

    @Override
    public boolean referenceActive() {
        return Utils.viewIsValidate(getReference());
    }
}
