#ifndef COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H
#define COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H

#include <cstdint>   // For fixed-width integer types (e.g., uint8_t)
#include <cstddef>   // For size_t

namespace composeglass {

    /**
     * Struct to define a restricted region within an image.
     * Allows applying processing (like blur) to only a subset of the image.
     */
    struct Restriction {
        size_t startX;  // Starting X coordinate (inclusive)
        size_t endX;    // Ending X coordinate (exclusive)
        size_t startY;  // Starting Y coordinate (inclusive)
        size_t endY;    // Ending Y coordinate (exclusive)
    };

    /**
     * Utility class that mimics RenderScript behavior.
     * Provides image processing operations such as blur.
     */
    class RenderScriptToolkit {
    public:
        /**
         * Applies a blur effect to the input pixel buffer.
         *
         * @param input Pointer to the source RGBA pixel buffer.
         * @param output Pointer to the destination buffer where the blurred image is stored.
         * @param width Width of the image in pixels.
         * @param height Height of the image in pixels.
         * @param stride Number of bytes per row (may include padding).
         * @param radius Blur intensity (larger values = stronger blur).
         * @param restriction Optional restricted region to apply the blur (nullptr = full image).
         */
        void blur(const uint8_t* input, uint8_t* output,
                  size_t width, size_t height,
                  size_t stride, int radius,
                  const Restriction* restriction = nullptr);
    };

} // namespace composeglass

#endif // COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H