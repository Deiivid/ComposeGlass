package com.composeglass.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composeglass.modifier.glassBlur

@Composable
fun GlassButtonExample() {
    Button(
        onClick = { /* Action */ },
        modifier = Modifier
            .glassBlur {
                radius = 5
            }

    ) {
        Text(text = "Glass Button")
    }
}
