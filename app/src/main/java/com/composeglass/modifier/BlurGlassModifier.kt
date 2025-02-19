package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

fun Modifier.blurGlass(
    enabled: Boolean = true,
    blurRadius: Int = 10,  // Controls the blur intensity
    blurOpacity: Float = 0.2f,  // Controls the background opacity
    blurColor: Color = Color.White
): Modifier {
    if (!enabled) return this

    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0) // Adjust blur intensity
    val adjustedOpacity = blurOpacity.coerceIn(0f, 1f) // Adjust opacity (0 = fully transparent, 1 = solid)

    return this
        .graphicsLayer { alpha = 0.98f } // Maintains a smoothing effect
        .background(blurColor.copy(alpha = adjustedOpacity)) // Applies the customized opacity
        .blur(adjustedBlurRadius.dp)
}
