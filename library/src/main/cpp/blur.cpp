#include <jni.h>
#include <android/bitmap.h>
#include <cstdint>
#include <cstring>
#include <vector>
#include <algorithm>
#include <cmath>

extern "C"
JNIEXPORT void JNICALL
Java_com_composeglass_modifier_oldVersionBlur_BlurUtils_nativeBlurBitmap(
        JNIEnv *env,
        jclass clazz,
        jobject bitmap,
        jint radius
) {
    if (radius < 1) return;

    AndroidBitmapInfo info;
    void* pixels;

    if (AndroidBitmap_getInfo(env, bitmap, &info) != ANDROID_BITMAP_RESULT_SUCCESS) return;
    if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) return;
    if (AndroidBitmap_lockPixels(env, bitmap, &pixels) != ANDROID_BITMAP_RESULT_SUCCESS) return;
    if (pixels == nullptr || info.width <= 1 || info.height <= 1) {
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    }

    const int w = static_cast<int>(info.width);
    const int h = static_cast<int>(info.height);
    const int wh = w * h;
    uint32_t* pix = static_cast<uint32_t*>(pixels);

    const int div = radius + radius + 1;
    std::vector<int> r(wh), g(wh), b(wh);
    std::vector<int> vmin(std::max(w, h));

    std::vector<int> dv(256 * div);
    for (int i = 0; i < dv.size(); ++i) dv[i] = (i / div);

    int yw = 0, yi = 0;

    std::vector<std::vector<int>> stack(div, std::vector<int>(3));
    int stackpointer, stackstart;
    int* sir;
    int rbs;
    int r1 = radius + 1;
    int routsum, goutsum, boutsum;
    int rinsum, ginsum, binsum;
    int rsum, gsum, bsum;

    // Horizontal blur
    for (int y = 0; y < h; ++y) {
        rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;

        for (int i = -radius; i <= radius; ++i) {
            int p = pix[yi + std::min(w - 1, std::max(i, 0))];
            sir = &stack[i + radius][0];
            sir[0] = (p >> 16) & 0xFF;
            sir[1] = (p >> 8) & 0xFF;
            sir[2] = p & 0xFF;

            rbs = r1 - std::abs(i);
            rsum += sir[0] * rbs;
            gsum += sir[1] * rbs;
            bsum += sir[2] * rbs;

            if (i > 0) {
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
            } else {
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
            }
        }

        stackpointer = radius;

        for (int x = 0; x < w; ++x) {
            r[yi] = dv[rsum];
            g[yi] = dv[gsum];
            b[yi] = dv[bsum];

            rsum -= routsum;
            gsum -= goutsum;
            bsum -= boutsum;

            stackstart = stackpointer - radius + div;
            sir = &stack[stackstart % div][0];

            routsum -= sir[0];
            goutsum -= sir[1];
            boutsum -= sir[2];

            if (y == 0) vmin[x] = std::min(x + radius + 1, w - 1);
            int p = pix[yw + vmin[x]];
            sir[0] = (p >> 16) & 0xFF;
            sir[1] = (p >> 8) & 0xFF;
            sir[2] = p & 0xFF;

            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];

            rsum += rinsum;
            gsum += ginsum;
            bsum += binsum;

            stackpointer = (stackpointer + 1) % div;
            sir = &stack[stackpointer][0];

            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];

            rinsum -= sir[0];
            ginsum -= sir[1];
            binsum -= sir[2];

            ++yi;
        }
        yw += w;
    }

    // Vertical blur
    for (int x = 0; x < w; ++x) {
        rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
        int yp = -radius * w;
        for (int i = -radius; i <= radius; ++i) {
            yi = std::max(0, yp) + x;
            sir = &stack[i + radius][0];
            sir[0] = r[yi];
            sir[1] = g[yi];
            sir[2] = b[yi];

            rbs = r1 - std::abs(i);
            rsum += r[yi] * rbs;
            gsum += g[yi] * rbs;
            bsum += b[yi] * rbs;

            if (i > 0) {
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
            } else {
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
            }

            if (i < h - 1) yp += w;
        }

        yi = x;
        stackpointer = radius;

        for (int y = 0; y < h; ++y) {
            pix[yi] = (0xFF << 24) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

            rsum -= routsum;
            gsum -= goutsum;
            bsum -= boutsum;

            stackstart = stackpointer - radius + div;
            sir = &stack[stackstart % div][0];

            routsum -= sir[0];
            goutsum -= sir[1];
            boutsum -= sir[2];

            if (x == 0) vmin[y] = std::min(y + r1, h - 1) * w;
            int p = x + vmin[y];

            sir[0] = r[p];
            sir[1] = g[p];
            sir[2] = b[p];

            rinsum += sir[0];
            ginsum += sir[1];
            binsum += sir[2];

            rsum += rinsum;
            gsum += ginsum;
            bsum += binsum;

            stackpointer = (stackpointer + 1) % div;
            sir = &stack[stackpointer][0];

            routsum += sir[0];
            goutsum += sir[1];
            boutsum += sir[2];

            rinsum -= sir[0];
            ginsum -= sir[1];
            binsum -= sir[2];

            yi += w;
        }
    }

    AndroidBitmap_unlockPixels(env, bitmap);
}