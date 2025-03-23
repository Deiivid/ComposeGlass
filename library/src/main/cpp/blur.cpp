#include <jni.h>
#include <android/bitmap.h>
#include <cstdint>

extern "C"
JNIEXPORT void JNICALL
Java_com_composeglass_modifier_oldVersionBlur_BlurUtils_nativeBlurBitmap(
        JNIEnv *env,
        jclass clazz,
        jobject bitmap,
        jint radius
) {
    AndroidBitmapInfo info;
    void* pixels;

    if (AndroidBitmap_getInfo(env, bitmap, &info) != ANDROID_BITMAP_RESULT_SUCCESS) return;
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) return;
    if (AndroidBitmap_lockPixels(env, bitmap, &pixels) != ANDROID_BITMAP_RESULT_SUCCESS) return;

    uint32_t* line = static_cast<uint32_t*>(pixels);
    for (int y = 0; y < info.height; ++y) {
        for (int x = 0; x < info.width; ++x) {
            int index = y * info.width + x;
            line[index] = 0x55FFFFFF; // 💡 píxeles semitransparentes blancos (modo "blanqueado")
        }
    }

    AndroidBitmap_unlockPixels(env, bitmap);
}