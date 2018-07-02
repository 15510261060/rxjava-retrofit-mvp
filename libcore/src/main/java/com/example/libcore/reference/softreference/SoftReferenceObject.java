package com.example.libcore.reference.softreference;


public class SoftReferenceObject extends BaseSoftReference<Object> {
    public SoftReferenceObject(Object o) {
        super(o);
    }

    @Override
    public boolean referenceActive() {
        return getReference() != null;
    }
}
