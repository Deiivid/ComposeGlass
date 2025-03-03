package com.composeglass.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassEffect
import kotlinx.coroutines.delay

@Composable
fun GlassListExample(items: List<String>) {
    var opacityLevel by remember { mutableStateOf(1f) }
    var blurRadius by remember { mutableStateOf(1f) }

    val animatedOpacity by animateFloatAsState(
        targetValue = opacityLevel,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "Opacity Animation"
    )

    val animatedBlurRadius by animateFloatAsState(
        targetValue = blurRadius,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "Blur Animation"
    )

    LaunchedEffect(Unit) {
        while (true) {
            for (i in 1..10) {
                opacityLevel = i / 10f
                blurRadius = i.toFloat()
                delay(1000)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .glassEffect(
                blurRadius = animatedBlurRadius.toInt(),
                blurOpacity = animatedOpacity
            ),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = animatedOpacity)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
