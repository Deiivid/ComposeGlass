#ifndef COMPOSEGLASS_BLUR_H
#define COMPOSEGLASS_BLUR_H

#include <cstdint>

namespace composeGlassMorphism {

/**
 * Applies a StackBlur effect directly on the pixel buffer.
 *
 * @param pix RGBA pixel buffer of the image. This buffer is modified in place.
 * @param w Width of the image in pixels.
 * @param h Height of the image in pixels.
 * @param radius Blur radius (e.g., 5, 10, 20). Higher values result in stronger blur.
 */
    void stackBlur(uint8_t* pix, int w, int h, int radius);

}

#endif // COMPOSEGLASS_BLUR_H