package com.composeglassmorphism.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composeglassmorphism.modifier.glassMorphism

@Composable
fun GlassMorphismFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .glassMorphism {
                radius = 10
            },
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(onClick = onClick) {
            content()
        }
    }
}
