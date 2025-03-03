package com.composeglass.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassEffect

@Composable
fun GlassCardExample() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .glassEffect(blurRadius = 7, blurOpacity = 0.2f, blurColor = Color.White)
    ) {
        Text(text = "This is a Glass Card", modifier = Modifier.padding(16.dp))
    }
}
