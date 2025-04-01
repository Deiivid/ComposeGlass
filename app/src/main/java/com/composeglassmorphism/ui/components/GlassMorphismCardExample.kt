package com.composeglassmorphism.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composeglassmorphism.modifier.glassMorphism

@Composable
fun GlassMorphismCardExample() {
    Card(
        modifier = Modifier
            .glassMorphism {
                radius = 5
            },
    ) {
        Text(text = "This is a Glass Card", modifier = Modifier.padding(16.dp))
    }
}
