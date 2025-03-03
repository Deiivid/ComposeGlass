package com.composeglass.modifier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A reusable container that applies a glassmorphism effect to its content.
 *
 * This container allows flexible customization of blur intensity, opacity, color, border, and shadows.
 * It supports automatic theme detection and manual theme configuration.
 *
 * @param modifier The `Modifier` to apply to the container.
 * @param blurRadius The intensity of the blur effect (default: 15).
 * @param blurOpacity The opacity of the blurred background (default: 0.3f).
 * @param blurColor The color of the blurred background (default: White).
 * @param borderColor The color of the optional border (default: Transparent, meaning no border).
 * @param borderWidth The thickness of the border (default: 0.dp, meaning no border).
 * @param useThemeColors If enabled, adapts blur color and opacity based on the system theme.
 * @param themeMode Determines whether to follow the system theme, dark mode, or light mode.
 * @param contentAlignment Defines the alignment of content inside the container (default: Center).
 * @param content The composable content that will be wrapped inside the blur container.
 */
@Composable
fun BlurContainer(
    modifier: Modifier = Modifier,
    blurRadius: Int = 15,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    borderColor: Color = Color.Transparent,
    borderWidth: Float = 0f,
    useThemeColors: Boolean = false,
    themeMode: ThemeMode = ThemeMode.AUTO,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .glassEffect(
                blurRadius = blurRadius,
                blurOpacity = blurOpacity,
                blurColor = blurColor,
                useThemeColors = useThemeColors,
                themeMode = themeMode
            )
            .then(
                if (borderWidth > 0f) Modifier.border(borderWidth.dp, borderColor) else Modifier
            ),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}
