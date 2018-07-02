package com.example.libcore.reference.weakreference;


import android.support.v4.app.Fragment;

import com.example.libcore.utils.Utils;

public class WeakReferenceFragment extends BaseWeakReference<Fragment> {
    public WeakReferenceFragment(Fragment fragment) {
        super(fragment);
    }

    @Override
    public boolean referenceActive() {
        return Utils.fragmentIsValidate(getReference());
    }

}
