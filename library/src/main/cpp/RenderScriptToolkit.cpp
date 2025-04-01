#include "RenderScriptToolkit.h"  // Include the header for the RenderScriptToolkit class
#include "blur.h"                 // Include the declaration of the stackBlur function
#include <cstring>                // Include for std::memcpy

namespace composeGlassMorphism {

    /**
     * Applies a blur effect to the input buffer and writes the result to the output buffer.
     *
     * @param input Pointer to the original pixel buffer (RGBA).
     * @param output Pointer to the destination buffer where blurred pixels will be written.
     * @param width Width of the image in pixels.
     * @param height Height of the image in pixels.
     * @param stride Number of bytes per row (may be >= width * 4).
     * @param radius Blur radius (amount of blur).
     * @param restriction Optional restriction to limit the blur to a specific area.
     */
    void RenderScriptToolkit::blur(const uint8_t* input, uint8_t* output,
                                   size_t width, size_t height,
                                   size_t stride, int radius,
                                   const Restriction* restriction) {
        // Define the area to blur. If no restriction is provided, apply to the whole image.
        size_t startX = restriction ? restriction->startX : 0;
        size_t endX = restriction ? restriction->endX : width;
        size_t startY = restriction ? restriction->startY : 0;
        size_t endY = restriction ? restriction->endY : height;

        // Copy the original input image into the output buffer before applying blur
        std::memcpy(output, input, height * stride);

        // Apply stack blur to the entire output image (restriction not applied here yet)
        stackBlur(output, width, height, radius);
    }

}