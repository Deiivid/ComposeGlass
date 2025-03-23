package com.composeglass.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.BlurContainer
import com.composeglass.modifier.ThemeMode
import com.composeglass.modifier.glassEffect

@Composable
fun GlassBlurContainerExample() {
    var themeMode by remember { mutableStateOf(ThemeMode.AUTO) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Glass Blur Container Example")

        Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .glassEffect(true,19.dp,0.15f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Glassmorphism Applied")
            }


        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            themeMode = when (themeMode) {
                ThemeMode.AUTO -> ThemeMode.DARK
                ThemeMode.DARK -> ThemeMode.LIGHT
                ThemeMode.LIGHT -> ThemeMode.AUTO
            }
        }) {
            Text(text = "Change Theme Mode: $themeMode")
        }
    }
}
