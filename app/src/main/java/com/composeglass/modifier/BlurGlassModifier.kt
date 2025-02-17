package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class BlurType {
    GAUSSIAN, RADIAL, BOX
}

fun Modifier.blurGlass(
    enabled: Boolean = true,
    blurRadius: Int = 10,
    blurType: BlurType = BlurType.GAUSSIAN,
    blurColor: Color = Color.White.copy(alpha = 0.2f)
): Modifier {
    if (!enabled) return this

    return this
        .background(blurColor)
        .blur(blurRadius.dp)
}
