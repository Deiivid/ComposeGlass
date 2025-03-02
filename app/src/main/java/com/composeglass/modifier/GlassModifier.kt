package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ThemeMode { AUTO, DARK, LIGHT }


/**
 * Applies a glassmorphism effect with blur, opacity, optional border, and shadow.
 *
 * @param enabled Enables or disables the effect (default true).
 * @param blurRadius The intensity of the blur effect (default 10).
 * @param blurOpacity The opacity of the blur background (default 0.2f).
 * @param blurColor The background color of the blur (default White).
 * @param useThemeColors If true, adapts to system light/dark mode.
 * @param themeMode Specifies if the effect follows Auto, Dark, or Light mode.
 * @param borderColor The color of the border (default Transparent).
 * @param borderWidth The width of the border (default 0.dp, meaning no border).
 * @param shadow If true, adds a subtle shadow effect.
 * @param shadowElevation The elevation of the shadow if enabled.
 */
@Composable
fun Modifier.glassEffect(
    enabled: Boolean = true,
    blurRadius: Int = 10,
    blurOpacity: Float = 0.2f,
    blurColor: Color = Color.White,
    useThemeColors: Boolean = false,
    themeMode: ThemeMode = ThemeMode.AUTO,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
    shadow: Boolean = false,
    shadowElevation: Dp = 4.dp
): Modifier {
    if (!enabled) return this

    val isDarkMode = when (themeMode) {
        ThemeMode.AUTO -> isSystemInDarkTheme()
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val dynamicBlurColor = if (useThemeColors) {
        if (isDarkMode) Color.Black else Color.White
    } else blurColor

    val dynamicBlurOpacity = if (useThemeColors) {
        if (isDarkMode) 0.7f else 0.3f
    } else blurOpacity

    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0)
    val adjustedOpacity = dynamicBlurOpacity.coerceIn(0f, 1f)

    return this
        .graphicsLayer { alpha = 0.98f }
        .background(dynamicBlurColor.copy(alpha = adjustedOpacity))
        .blur(adjustedBlurRadius.dp)
        .border(borderWidth, borderColor)
        .then(if (shadow) Modifier.shadow(shadowElevation) else Modifier)
}
