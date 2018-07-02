package com.example.libcore.reference.softreference;

import android.graphics.Bitmap;


public class SoftReferenceBitmap extends BaseSoftReference<Bitmap> {

    public SoftReferenceBitmap(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public boolean referenceActive() {
        Bitmap bitmap = getReference();
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        return true;
    }
}
