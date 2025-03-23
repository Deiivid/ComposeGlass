#include <jni.h>
#include <android/bitmap.h>
#include <vector>
#include <cmath>
#include <algorithm>

// Clamp para evitar overflow de índices
static inline int clamp(int value, int min, int max) {
    return std::max(min, std::min(value, max));
}

// Genera kernel Gaussiano 1D
static std::vector<float> createGaussianKernel(int radius, float sigma) {
    std::vector<float> kernel(2 * radius + 1);
    float sum = 0.0f;
    for (int i = -radius; i <= radius; i++) {
        float x = float(i);
        float val = std::exp(-(x * x) / (2 * sigma * sigma));
        kernel[i + radius] = val;
        sum += val;
    }
    for (float& value : kernel) value /= sum;
    return kernel;
}

// IMPLEMENTACIÓN JNI ESPERADA
extern "C"
JNIEXPORT void JNICALL
Java_com_composeglass_modifier_oldVersionBlur_RenderScriptToolkit_blurImageNative(
        JNIEnv* env,
        jclass clazz,
        jobject inputBitmap,
        jobject outputBitmap,
        jint radius
) {
    if (radius < 1) return;

    AndroidBitmapInfo info;
    void* inputPixels = nullptr;
    void* outputPixels = nullptr;

    if (AndroidBitmap_getInfo(env, inputBitmap, &info) != ANDROID_BITMAP_RESULT_SUCCESS ||
        info.format != ANDROID_BITMAP_FORMAT_RGBA_8888) {
        return;
    }

    if (AndroidBitmap_lockPixels(env, inputBitmap, &inputPixels) != ANDROID_BITMAP_RESULT_SUCCESS ||
        AndroidBitmap_lockPixels(env, outputBitmap, &outputPixels) != ANDROID_BITMAP_RESULT_SUCCESS) {
        return;
    }

    int width = info.width;
    int height = info.height;
    int stride = info.stride / 4; // pixels por fila

    uint32_t* in = reinterpret_cast<uint32_t*>(inputPixels);
    uint32_t* out = reinterpret_cast<uint32_t*>(outputPixels);

    std::vector<float> kernel = createGaussianKernel(radius, radius / 3.0f);
    std::vector<uint32_t> temp(width * height);

    // Desenfoque horizontal
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            float r = 0, g = 0, b = 0, a = 0;
            float sum = 0;
            for (int k = -radius; k <= radius; k++) {
                int xx = clamp(x + k, 0, width - 1);
                uint32_t pixel = in[y * stride + xx];

                float weight = kernel[k + radius];
                a += ((pixel >> 24) & 0xff) * weight;
                r += ((pixel >> 16) & 0xff) * weight;
                g += ((pixel >> 8) & 0xff) * weight;
                b += ((pixel) & 0xff) * weight;
                sum += weight;
            }
            uint32_t blurredPixel = ((int)(a / sum) << 24) |
                                    ((int)(r / sum) << 16) |
                                    ((int)(g / sum) << 8) |
                                    ((int)(b / sum));
            temp[y * width + x] = blurredPixel;
        }
    }

    // Desenfoque vertical
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            float r = 0, g = 0, b = 0, a = 0;
            float sum = 0;
            for (int k = -radius; k <= radius; k++) {
                int yy = clamp(y + k, 0, height - 1);
                uint32_t pixel = temp[yy * width + x];

                float weight = kernel[k + radius];
                a += ((pixel >> 24) & 0xff) * weight;
                r += ((pixel >> 16) & 0xff) * weight;
                g += ((pixel >> 8) & 0xff) * weight;
                b += ((pixel) & 0xff) * weight;
                sum += weight;
            }
            uint32_t blurredPixel = ((int)(a / sum) << 24) |
                                    ((int)(r / sum) << 16) |
                                    ((int)(g / sum) << 8) |
                                    ((int)(b / sum));
            out[y * stride + x] = blurredPixel;
        }
    }

    AndroidBitmap_unlockPixels(env, inputBitmap);
    AndroidBitmap_unlockPixels(env, outputBitmap);
}