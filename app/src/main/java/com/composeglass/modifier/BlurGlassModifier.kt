package com.composeglass.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

/**
 * Applies a glassmorphism blur effect to any Composable.
 *
 * @param enabled Determines whether the blur effect is applied (default: true).
 * @param blurRadius Controls the intensity of the blur effect (default: 10).
 * @param blurOpacity Defines the opacity of the blurred background (default: 0.2f).
 * @param blurColor Specifies the background color of the blur effect (default: White).
 * @param useThemeColors If enabled, the blur effect adapts to the system theme.
 * @param themeMode Determines whether the blur follows the system theme, dark mode, or light mode.
 *
 * @return A modified `Modifier` with the applied Glassmorphism effect.
 */
enum class ThemeMode { AUTO, DARK, LIGHT } // Enum for theme selection

@Composable
fun Modifier.blurGlass(
    enabled: Boolean = true,
    blurRadius: Int = 10,
    blurOpacity: Float = 0.2f,
    blurColor: Color = Color.White,
    useThemeColors: Boolean = false,
    themeMode: ThemeMode = ThemeMode.AUTO
): Modifier {
    if (!enabled) return this

    val isDarkMode = when (themeMode) {
        ThemeMode.AUTO -> isSystemInDarkTheme() // Automatic system mode
        ThemeMode.DARK -> true  // Force Dark Mode
        ThemeMode.LIGHT -> false // Force Light Mode
    }

    // Apply theme colors only if `useThemeColors` is true
    val dynamicBlurColor = if (useThemeColors) {
        if (isDarkMode) Color.Black else Color.White
    } else blurColor

    val dynamicBlurOpacity = if (useThemeColors) {
        if (isDarkMode) 0.7f else 0.3f
    } else blurOpacity

    val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0) // Adjust blur intensity
    val adjustedOpacity =
        dynamicBlurOpacity.coerceIn(0f, 1f) // Adjust opacity (0 = fully transparent, 1 = solid)

    return this
        .graphicsLayer { alpha = 0.98f } // Maintains a smoothing effect
        .background(dynamicBlurColor.copy(alpha = adjustedOpacity)) // Applies the customized opacity
        .blur(adjustedBlurRadius.dp)
}
