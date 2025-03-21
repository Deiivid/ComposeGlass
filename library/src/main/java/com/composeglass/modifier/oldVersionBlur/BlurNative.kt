package com.composeglass.modifier.oldVersionBlur


object BlurNative {
    init {
        // Debe coincidir con el nombre que diste en CMakeLists.txt => nativeblur
        System.loadLibrary("nativeblur")
    }

    // Declaración JNI (coincide con la firma en blur.cpp)
    external fun gaussianBlur(
        pixels: IntArray,
        width: Int,
        height: Int,
        radius: Int
    )
}