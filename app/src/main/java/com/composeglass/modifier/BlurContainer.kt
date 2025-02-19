package com.composeglass.modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun BlurContainer(
    modifier: Modifier = Modifier,
    blurRadius: Int = 15,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    useThemeColors: Boolean = false, //Allows automatic theme detection
    themeMode: ThemeMode = ThemeMode.AUTO, // Allows forcing dark or light mode
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
