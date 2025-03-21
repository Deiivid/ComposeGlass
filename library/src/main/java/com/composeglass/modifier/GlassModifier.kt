package com.composeglass.modifier

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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

    // 1) Ajustamos color/alpha según el tema
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

    // 2) Decidimos según versión de Android
    val baseModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // === ANDROID 12+ => usar .blur() nativo ===
        val adjustedBlurRadius = (blurRadius * 0.6).coerceIn(0.0, 25.0)
        val adjustedOpacity = dynamicBlurOpacity.coerceIn(0f, 1f)

        this
            .graphicsLayer { alpha = 0.98f }
            .background(dynamicBlurColor.copy(alpha = adjustedOpacity))
            .blur(adjustedBlurRadius.dp)
    } else {
        // === Fallback en APIs < 31 => usar tu myBlurModifier(...) ===
        //   Ojo: myBlurModifier(...) es un ejemplo que difumina un bitmap degradado;
        //   No hará un blur real del contenido detrás, pero mantiene coherencia.
        this.glassBlur(blurRadius)
    }

    // 3) Añadimos borde y sombra si corresponden
    val withBorder = baseModifier.border(borderWidth, borderColor)
    return if (shadow) {
        withBorder.shadow(shadowElevation)
    } else {
        withBorder
    }
}