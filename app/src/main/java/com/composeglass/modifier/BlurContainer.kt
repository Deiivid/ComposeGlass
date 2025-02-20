package com.composeglass.modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A reusable container that applies a glassmorphism blur effect to its content.
 *
 * This container allows for flexible customization of blur intensity, opacity, and color.
 * Additionally, it supports automatic theme detection and manual theme configuration.
 *
 * @param modifier The `Modifier` to apply to the container.
 * @param blurRadius The intensity of the blur effect (default: 15).
 * @param blurOpacity The opacity of the blurred background (default: 0.3f).
 * @param blurColor The color of the blurred background (default: White).
 * @param useThemeColors If enabled, adapts blur color and opacity based on the system theme.
 * @param themeMode Determines whether to follow the system theme, dark mode, or light mode.
 * @param content The composable content that will be wrapped inside the blur container.
 */
@Composable
fun BlurContainer(
    modifier: Modifier = Modifier,
    blurRadius: Int = 15,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    useThemeColors: Boolean = false,
    themeMode: ThemeMode = ThemeMode.AUTO,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .blurGlass(
                blurRadius = blurRadius,
                blurOpacity = blurOpacity,
                blurColor = blurColor,
                useThemeColors = useThemeColors,
                themeMode = themeMode
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
