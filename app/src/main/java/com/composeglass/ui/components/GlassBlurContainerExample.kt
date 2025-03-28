package com.composeglass.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.sp
import com.composeglass.modifier.BlurThemeMode
import com.composeglass.modifier.glassBlur
enum class ThemeMode {
    AUTO, DARK, LIGHT
}
@Composable
fun GlassBlurContainerExample() {

    var buttonThemeMode by remember { mutableStateOf(BlurThemeMode.Auto) }
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
