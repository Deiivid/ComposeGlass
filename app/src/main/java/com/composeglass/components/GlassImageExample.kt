package com.composeglass.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.blurGlass

@Composable
fun GlassImageExample(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "Imagen con blur",
        modifier = Modifier
            .size(200.dp)
            .blurGlass(blurRadius = 20)
    )
}
