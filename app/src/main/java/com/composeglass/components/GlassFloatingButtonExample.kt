package com.composeglass.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassEffect

@Composable
fun GlassFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    blurRadius: Int = 15,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .glassEffect(blurRadius = blurRadius, blurOpacity = blurOpacity, blurColor = blurColor),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(onClick = onClick) {
            content()
        }
    }
}
