// blur.h
#ifndef COMPOSEGLASS_BLUR_H
#define COMPOSEGLASS_BLUR_H

#include <cstdint>

namespace composeglass {

/**
 * Aplica un desenfoque tipo StackBlur directamente sobre el buffer de píxeles.
 *
 * @param pixels Buffer RGBA de la imagen. Se modifica directamente.
 * @param width Ancho de la imagen en píxeles.
 * @param height Alto de la imagen en píxeles.
 * @param radius Radio del desenfoque (ej: 5, 10, 20).
 */
    void stackBlur(uint8_t* pixels, int width, int height, int radius);

} // namespace composeglass

#endif // COMPOSEGLASS_BLUR_H