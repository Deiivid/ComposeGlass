package com.composeglass.modifier.oldVersionBlur

import android.graphics.Bitmap

object RenderScriptToolkit {
    init {
        System.loadLibrary("renderscript_toolkit")
    }

    external fun blurImageNative(input: Bitmap, output: Bitmap, radius: Int)

    fun blur(input: Bitmap, output: Bitmap, radius: Int) {
        require(input.config == Bitmap.Config.ARGB_8888 && output.config == Bitmap.Config.ARGB_8888) {
            "Bitmaps must be in ARGB_8888 config"
        }
        require(input.width == output.width && input.height == output.height) {
            "Input and output Bitmap must have the same dimensions"
        }
        blurImageNative(input, output, radius)
    }
}