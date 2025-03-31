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
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.BlurThemeMode
import com.composeglass.modifier.glassBlur

@Composable
fun GlassBlurContainerExample() {

    var buttonThemeMode by remember { mutableStateOf(BlurThemeMode.Auto) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
                .glassBlur {
                    radius = 5
                    themeMode = buttonThemeMode
                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Glassmorphism Applied")
        }


        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            buttonThemeMode = when (buttonThemeMode) {
                BlurThemeMode.Auto -> BlurThemeMode.Dark
                BlurThemeMode.Dark -> BlurThemeMode.Light
                BlurThemeMode.Light -> BlurThemeMode.Auto
            }
        }) {
            Text("Change Theme Mode: $buttonThemeMode")
        }
    }
}
