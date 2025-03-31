package com.composeglass.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassBlur

@Composable
fun GlassImageExample(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "Blurred Image",
        modifier = Modifier
            .size(200.dp)
            .glassBlur {
                radius = 10
            },

    )
}
