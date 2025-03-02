package com.composeglass.modifier

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Applies a background blur effect based on the Android version.
 *
 * - **Android 12+ (API 31+)** → Uses `Modifier.blur()`
 * - **Android 10-11 (API 29-30)** → Uses `Modifier.blur()` (fallback)
 * - **Older versions (API < 29)** → Uses only background color (no blur)
 *
 * @param blurRadius The intensity of the blur effect (default 10).
 * @param blurColor Optional background color for older devices.
 */
fun Modifier.backgroundBlur(
    blurRadius: Int = 10,
): Modifier {
    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0).dp
    this.blur(adjustedBlurRadius)
}
