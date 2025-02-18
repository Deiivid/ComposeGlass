package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

fun Modifier.blurGlass(
    enabled: Boolean = true,
    blurRadius: Int = 10,  // Controla el desenfoque
    blurOpacity: Float = 0.2f,  // 🔥 Nuevo: Controla la opacidad del fondo
    blurColor: Color = Color.White
): Modifier {
    if (!enabled) return this

    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0) // Ajuste del blur
    val adjustedOpacity = blurOpacity.coerceIn(0f, 1f) // 🔥 Ajuste de opacidad (0 = totalmente transparente, 1 = sólido)

    return this
        .graphicsLayer { alpha = 0.98f } // Mantiene el efecto de suavizado
        .background(blurColor.copy(alpha = adjustedOpacity)) // 🔥 Aplica la opacidad personalizada
        .blur(adjustedBlurRadius.dp)
}
