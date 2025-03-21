package com.composeglass.modifier

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gaussianBlurNative

enum class ThemeMode { AUTO, DARK, LIGHT }

/**
 * Applies a glassmorphism effect with blur, opacity, optional border, and shadow.
 *
 * - En Android 12+ (API 31), usa .graphicsLayer + .background + .blur
 * - En APIS anteriores, utiliza tu 'myBlurModifier(...)' como fallback
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
    val view = LocalView.current
    // 1) Ajustar color según tema
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

    // 2) Decide según API
    val baseModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // ANDROID 12+ => usar .blur() nativo (RenderEffect)
        val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0)
        val adjustedOpacity = dynamicBlurOpacity.coerceIn(0f, 1f)

        this
            .graphicsLayer { alpha = 0.98f }
            .background(dynamicBlurColor.copy(alpha = adjustedOpacity))
            .blur(adjustedBlurRadius.dp)

    } else {
        // Fallback: Capturar el fondo + usar gaussianBlurNative
        var blurredBitmap: Bitmap? = null
        this
            .onGloballyPositioned { coords ->
                val w = coords.size.width
                val h = coords.size.height

                if (w > 0 && h > 0) {
                    // Capturamos la porción detrás del Composable
                    val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bmp)

                    val pos = coords.positionInWindow()
                    canvas.translate(-pos.x, -pos.y)
                    view.draw(canvas)

                    // Aplica el blur nativo en el bmp
                    bmp.gaussianBlurNative(blurRadius)
                    blurredBitmap = bmp
                }
            }
            .drawBehind {
                blurredBitmap?.let {
                    drawImage(
                        it.asImageBitmap(),
                        Offset.Zero,
                        alpha = dynamicBlurOpacity
                    )
                }
            }
    }

    // 3) Borde y sombra
    val withBorder = baseModifier.border(borderWidth, borderColor)
    return if (shadow) {
        withBorder.shadow(shadowElevation)
    } else {
        withBorder
    }
}