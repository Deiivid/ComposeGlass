#include "RenderScriptToolkit.h"
#include "blur.h"
#include <cstring>

namespace composeglass {

    void RenderScriptToolkit::blur(const uint8_t* input, uint8_t* output,
                                   size_t width, size_t height,
                                   size_t stride, int radius,
                                   const Restriction* restriction) {
        size_t startX = restriction ? restriction->startX : 0;
        size_t endX = restriction ? restriction->endX : width;
        size_t startY = restriction ? restriction->startY : 0;
        size_t endY = restriction ? restriction->endY : height;

        // Por ahora, no se usa la restricción
        std::memcpy(output, input, height * stride);
        stackBlur(output, width, height, radius); // <- aquí está la llamada a tu función nativa
    }

}ya