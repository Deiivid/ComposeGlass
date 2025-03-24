#ifndef COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H
#define COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H

#include <cstdint>
#include <cstddef>

namespace composeglass {

    struct Restriction {
        size_t startX;
        size_t endX;
        size_t startY;
        size_t endY;
    };

    class RenderScriptToolkit {
    public:
        void blur(const uint8_t* input, uint8_t* output,
                  size_t width, size_t height,
                  size_t stride, int radius,
                  const Restriction* restriction = nullptr);
    };

} // namespace composeglass

#endif // COMPOSEGLASS_RENDERSCRIPTTOOLKIT_H