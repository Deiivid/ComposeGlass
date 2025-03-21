#include <jni.h>
#include <vector>
#include <cmath>

// Función auxiliar para no salirnos de [0, maxVal]
static inline int clampIndex(int x, int maxVal) {
    if (x < 0) return 0;
    if (x > maxVal) return maxVal;
    return x;
}

/**
 * Genera un kernel gaussiano 1D de tamaño (2*radius + 1).
 * sigma ~ radius/3, aunque puedes ajustar ese factor.
 */
static std::vector<float> createGaussianKernel(int radius, float sigma) {
    std::vector<float> kernel(2 * radius + 1);
    float sum = 0.0f;
    for (int i = -radius; i <= radius; i++) {
        float x = float(i);
        float val = std::exp(-(x * x) / (2 * sigma * sigma));
        kernel[i + radius] = val;
        sum += val;
    }
    // Normalizar
    for (int i = 0; i < kernel.size(); i++) {
        kernel[i] /= sum;
    }
    return kernel;
}

/**
 * Aplica convolución 1D (kernel) en sentido horizontal.
 *  - px: vector con píxeles ARGB
 *  - out: vector salida
 *  - width, height: dimensiones
 *  - kernel: kernel gaussiano (de longitud 2*radius+1)
 *  - radius: radio de desenfoque
 */
static void convolveHorizontal(
        const std::vector<int>& px,
        std::vector<int>& out,
        int width,
        int height,
        const std::vector<float>& kernel,
        int radius
) {
    int wm = width - 1;
    for (int y = 0; y < height; y++) {
        int offset = y * width;
        for (int x = 0; x < width; x++) {
            float rSum = 0, gSum = 0, bSum = 0;
            // Aplicamos kernel en el vecindario [x - radius, x + radius]
            for (int k = -radius; k <= radius; k++) {
                int nx = clampIndex(x + k, wm);
                int c = px[offset + nx];
                float w = kernel[k + radius];
                rSum += ((c >> 16) & 0xFF) * w;
                gSum += ((c >> 8) & 0xFF) * w;
                bSum += (c & 0xFF) * w;
            }
            int r = static_cast<int>(rSum + 0.5f);
            int g = static_cast<int>(gSum + 0.5f);
            int b = static_cast<int>(bSum + 0.5f);
            out[offset + x] = (0xFF << 24) | (r << 16) | (g << 8) | b;
        }
    }
}

/**
 * Aplica convolución 1D (kernel) en sentido vertical.
 */
static void convolveVertical(
        const std::vector<int>& px,
        std::vector<int>& out,
        int width,
        int height,
        const std::vector<float>& kernel,
        int radius
) {
    int hm = height - 1;
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            float rSum = 0, gSum = 0, bSum = 0;
            int offset = y * width + x;
            // Vecindario [y - radius, y + radius]
            for (int k = -radius; k <= radius; k++) {
                int ny = clampIndex(y + k, hm);
                int c = px[ny * width + x];
                float w = kernel[k + radius];
                rSum += ((c >> 16) & 0xFF) * w;
                gSum += ((c >> 8) & 0xFF) * w;
                bSum += (c & 0xFF) * w;
            }
            int r = static_cast<int>(rSum + 0.5f);
            int g = static_cast<int>(gSum + 0.5f);
            int b = static_cast<int>(bSum + 0.5f);
            out[offset] = (0xFF << 24) | (r << 16) | (g << 8) | b;
        }
    }
}

/**
 * Implementación nativa de un desenfoque gaussiano 2D (horizontal + vertical).
 * Firma JNI: Java_com_composeglass_modifier_oldVersionBlur_BlurNative_gaussianBlur
 *    => package = com.composeglass.modifier.oldVersionBlur
 *    => clase   = BlurNative
 *    => función = gaussianBlur
 */
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
    if (radius < 1 || width < 1 || height < 1) {
        return; // Nada que blurear
    }

    // Obtenemos el puntero a los píxeles Java
    jint* pixels = env->GetIntArrayElements(pixels_, nullptr);
    if (!pixels) return;

    // Copiamos a std::vector para manipularlos fácilmente
    std::vector<int> src(pixels, pixels + (width * height));
    std::vector<int> temp(width * height);
    std::vector<int> dst(width * height);

    // Por convención, tomamos sigma ~ radius/3.0 (ajústalo según tu gusto)
    float sigma = radius / 3.0f;
    // Generamos kernel gaussiano 1D
    auto kernel = createGaussianKernel(radius, sigma);

    // 1) Convolución horizontal
    convolveHorizontal(src, temp, width, height, kernel, radius);
    // 2) Convolución vertical
    convolveVertical(temp, dst, width, height, kernel, radius);

    // Pasamos el resultado al array Java
    for (int i = 0; i < width * height; i++) {
        pixels[i] = dst[i];
    }

    // Liberamos array en la JVM
    env->ReleaseIntArrayElements(pixels_, pixels, 0);
}