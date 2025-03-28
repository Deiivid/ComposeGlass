/*
package com.composeglass.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassBlur

@Composable
fun GlassFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    blurRadius: Dp = 15.dp,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .glassBlur(radius = 20),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(onClick = onClick) {
            content()
        }
    }
}
*/
