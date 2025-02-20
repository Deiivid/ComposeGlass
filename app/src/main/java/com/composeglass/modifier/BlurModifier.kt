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
    blurColor: Color? = null
): Modifier {
    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0).dp

    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            this.blur(adjustedBlurRadius)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
            // Android 10-11: Apply blur
            this.blur(adjustedBlurRadius)
        }
        else -> {
            // Android 9 and below: Apply only background color
            if (blurColor != null) {
                this.background(blurColor)
            } else {
                this // No blur applied, returns the original Modifier
            }
        }
    }
}
