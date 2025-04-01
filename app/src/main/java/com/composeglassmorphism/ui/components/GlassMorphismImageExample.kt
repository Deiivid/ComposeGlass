package com.composeglassmorphism.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.composeglassmorphism.modifier.glassMorphism

@Composable
fun GlassMorphismImageExample(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "Blurred Image",
        modifier = Modifier
            .size(200.dp)
            .glassMorphism {
                radius = 10
                gradientColors= listOf(Color.Cyan,Color.Red)
            },

        )
}
