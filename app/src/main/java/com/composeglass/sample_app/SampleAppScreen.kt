package com.composeglass.sample_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.BlurType
import com.composeglass.modifier.blurGlass

@Composable
fun SampleAppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .blurGlass(
                    enabled = true,
                    blurRadius = 15,
                    blurType = BlurType.GAUSSIAN,
                    blurColor = Color.Blue.copy(alpha = 0.2f)
                )
        ) {
            Text(
                text = "Hello Glassmorphism!",
                color = Color.White
            )
        }
    }
}
