package com.composeglass.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.blurGlass

@Composable
fun GlassCardExample() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .blurGlass(blurRadius = 16, blurColor = Color.White.copy(alpha = 0.2f))
    ) {
        Text(text = "Este es un Glass Card", modifier = Modifier.padding(16.dp))
    }
}
