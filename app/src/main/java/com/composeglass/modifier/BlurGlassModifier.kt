package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

fun Modifier.blurGlass(
    enabled: Boolean = true,
    blurRadius: Int = 10,
    blurColor: Color = Color.White.copy(alpha = 0.2f)
): Modifier {
    if (!enabled) return this

    val safeBlurRadius = blurRadius.coerceIn(0, 10) //Restricted to 10

    return this
        .graphicsLayer { alpha = 0.98f }
        .background(blurColor)
        .blur(safeBlurRadius.dp)
}
