#include "RenderScriptToolkit.h"

namespace composeglass {

    void RenderScriptToolkit::blur(const uint8_t* input, uint8_t* output,
                                   size_t width, size_t height,
                                   size_t stride, int radius,
                                   const Restriction* restriction) {
        size_t startX = restriction ? restriction->startX : 0;
        size_t endX = restriction ? restriction->endX : width;
        size_t startY = restriction ? restriction->startY : 0;
        size_t endY = restriction ? restriction->endY : height;

        // Aquí pon tu algoritmo real. De momento copiamos tal cual.
        for (size_t y = startY; y < endY; y++) {
            for (size_t x = startX; x < endX; x++) {
                size_t idx = y * stride + x * 4;
                output[idx + 0] = input[idx + 0];
                output[idx + 1] = input[idx + 1];
                output[idx + 2] = input[idx + 2];
                output[idx + 3] = input[idx + 3];
            }
        }
    }

} // namespace composeglass