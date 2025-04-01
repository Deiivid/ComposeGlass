package com.composeglassmorphism.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.composeglassmorphism.ui.theme.ComposeGlassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGlassTheme {
                GlassMorphismScreen()
            }
        }
    }
}