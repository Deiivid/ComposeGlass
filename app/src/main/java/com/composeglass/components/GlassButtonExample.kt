package com.composeglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.blurGlass

@Composable
fun GlassButtonExample() {
    Button(
        onClick = { /* Acción */ },
        modifier = Modifier
            .blurGlass(blurRadius = 12)
            .padding(8.dp)
    ) {
        Text(text = "Glass Button")
    }
}
