#include <jni.h>
#include <vector>
#include <cmath>
#include <algorithm>

// Clamp para mantener los índices dentro del rango válido
static inline int clampIndex(int x, int maxVal) {
    return std::max(0, std::min(x, maxVal));
}

// Genera un kernel gaussiano 1D
static std::vector<float> createGaussianKernel(int radius, float sigma) {
    std::vector<float> kernel(2 * radius + 1);
    float sum = 0.0f;
    for (int i = -radius; i <= radius; i++) {
        float x = float(i);
        float val = std::exp(-(x * x) / (2 * sigma * sigma));
        kernel[i + radius] = val;
        sum += val;
    }
    for (float &value : kernel) value /= sum;
    return kernel;
}

// Aplica convolución 1D a una dirección
static void applyConvolution1D(
        const std::vector<int>& src,
        std::vector<int>& dst,
        int width,
        int height,
        const std::vector<float>& kernel,
        bool horizontal
) {
    int radius = static_cast<int>(kernel.size()) / 2;

    for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
            float r = 0, g = 0, b = 0, a = 0;
            for (int k = -radius; k <= radius; ++k) {
                int sampleX = horizontal ? clampIndex(x + k, width - 1) : x;
                int sampleY = horizontal ? y : clampIndex(y + k, height - 1);
                int pixel = src[sampleY * width + sampleX];

                float coeff = kernel[k + radius];
                a += ((pixel >> 24) & 0xFF) * coeff;
                r += ((pixel >> 16) & 0xFF) * coeff;
                g += ((pixel >> 8) & 0xFF) * coeff;
                b += (pixel & 0xFF) * coeff;
            }

            int finalA = std::clamp(static_cast<int>(a), 0, 255);
            int finalR = std::clamp(static_cast<int>(r), 0, 255);
            int finalG = std::clamp(static_cast<int>(g), 0, 255);
            int finalB = std::clamp(static_cast<int>(b), 0, 255);

            dst[y * width + x] = (finalA << 24) | (finalR << 16) | (finalG << 8) | finalB;
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_composeglass_modifier_oldVersionBlur_BlurNative_gaussianBlur(
        JNIEnv* env,
        jclass clazz,
        jintArray pixels_,
        jint width,
        jint height,
        jint radius
) {
    if (radius < 1 || width < 1 || height < 1) return;

    jint* pixels = env->GetIntArrayElements(pixels_, nullptr);
    if (!pixels) return;

    std::vector<int> src(pixels, pixels + (width * height));
    std::vector<int> temp(width * height);
    std::vector<int> dst(width * height);

    float sigma = radius / 3.0f;
    auto kernel = createGaussianKernel(radius, sigma);

    // Convolución horizontal
    applyConvolution1D(src, temp, width, height, kernel, true);
    // Convolución vertical
    applyConvolution1D(temp, dst, width, height, kernel, false);

    // Copia el resultado de vuelta a Java
    for (int i = 0; i < width * height; i++) {
        pixels[i] = dst[i];
    }

    env->ReleaseIntArrayElements(pixels_, pixels, 0);
}