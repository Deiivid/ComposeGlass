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

namespace composeglass {


    void stackBlur(uint8_t* pix, int w, int h, int radius) {
        if (radius < 1) return;

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        std::vector<int> r(wh);
        std::vector<int> g(wh);
        std::vector<int> b(wh);

        std::vector<int> vmin(std::max(w, h));
        std::vector<int> dv(256 * div);

        for (int i = 0; i < 256 * div; i++) {
            dv[i] = i / div;
        }

        int yi = 0;
        int yw = 0;

        for (int y = 0; y < h; y++) {
            int rsum = 0, gsum = 0, bsum = 0;
            for (int i = -radius; i <= radius; i++) {
                int p = (yi + std::min(wm, std::max(i, 0))) * 4;
                rsum += pix[p + 0];
                gsum += pix[p + 1];
                bsum += pix[p + 2];
            }

            for (int x = 0; x < w; x++) {
                int p = (yi + x) * 4;
                r[p / 4] = dv[rsum];
                g[p / 4] = dv[gsum];
                b[p / 4] = dv[bsum];

                if (y == 0) {
                    vmin[x] = std::min(x + radius + 1, wm);
                }
                int p1 = (yi + vmin[x]) * 4;
                int p2 = (yi + std::max(x - radius, 0)) * 4;

                rsum += pix[p1 + 0] - pix[p2 + 0];
                gsum += pix[p1 + 1] - pix[p2 + 1];
                bsum += pix[p1 + 2] - pix[p2 + 2];
            }
            yi += w;
        }

        for (int x = 0; x < w; x++) {
            int rsum = 0, gsum = 0, bsum = 0;
            int yp = -radius * w;

            for (int i = -radius; i <= radius; i++) {
                int yi = std::max(0, yp) + x;
                rsum += r[yi];
                gsum += g[yi];
                bsum += b[yi];
                yp += w;
            }

            int yi = x;
            for (int y = 0; y < h; y++) {
                int p = yi * 4;
                pix[p + 0] = dv[rsum];
                pix[p + 1] = dv[gsum];
                pix[p + 2] = dv[bsum];
                pix[p + 3] = 255; // Alpha

                if (x == 0) {
                    vmin[y] = std::min(y + radius + 1, hm) * w;
                }
                int p1 = x + vmin[y];
                int p2 = x + std::max(y - radius, 0) * w;

                rsum += r[p1] - r[p2];
                gsum += g[p1] - g[p2];
                bsum += b[p1] - b[p2];

                yi += w;
            }
        }
    }

}