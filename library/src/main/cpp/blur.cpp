#include <jni.h>
#include <android/bitmap.h>
#include "RenderScriptToolkit.h"
#include "blur.h"
#include <vector>

using namespace composeglass;

extern "C"
JNIEXPORT void JNICALL
Java_com_composeglass_modifier_oldVersionBlur_BlurUtils_nativeBlurBitmap(
        JNIEnv *env, jclass, jobject bitmap, jint radius
) {
    if (radius < 1) return;

    AndroidBitmapInfo info;
    void* pixels;
    if (AndroidBitmap_getInfo(env, bitmap, &info) != ANDROID_BITMAP_RESULT_SUCCESS) return;
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) return;
    if (AndroidBitmap_lockPixels(env, bitmap, &pixels) != ANDROID_BITMAP_RESULT_SUCCESS) return;

    RenderScriptToolkit toolkit;
    std::vector<uint8_t> input(static_cast<uint8_t*>(pixels),
                               static_cast<uint8_t*>(pixels) + info.height * info.stride);
    toolkit.blur(input.data(), static_cast<uint8_t*>(pixels),
                 info.width, info.height, info.stride, radius);

    AndroidBitmap_unlockPixels(env, bitmap);
}

// Implementación simple de stackBlur
namespace composeglass {

    void stackBlur(uint8_t* pixels, int width, int height, int radius) {
        // Aquí podrías mover tu algoritmo real. De momento, lo dejamos como no-op.
        // Esto está para evitar el error de "undefined symbol" y probar el pipeline completo.
    }

}