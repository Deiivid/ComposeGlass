package com.composeglass.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassBlur

@Composable
fun GlassButtonExample() {
    Button(
        onClick = { /* Action */ },
        modifier = Modifier
            .glassBlur(radius = 20)
            .padding(8.dp)
    ) {
        Text(text = "Glass Button")
    }
}
