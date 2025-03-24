package com.composeglass.modifier.oldVersionBlur

import android.graphics.Bitmap

object BlurUtils {
    init {
        System.loadLibrary("glassblur")
    }

    external fun nativeBlurBitmap(bitmap: Bitmap, radius: Int)
}