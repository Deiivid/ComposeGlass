package com.composeglass.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

/**
 * Applies a background blur effect based on the Android version.
 *
 * @param blurRadius The intensity of the blur effect (default 10).
 */
fun Modifier.backgroundBlur(
    blurRadius: Int = 10,
): Modifier {
    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0).dp
    return this.blur(adjustedBlurRadius)
}
