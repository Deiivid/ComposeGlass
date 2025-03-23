package com.composeglass.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.glassBlur

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val glassItems = listOf("Elemento 1", "Elemento 2", "Elemento 3")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Glassmorphism Demo",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .glassBlur(radius = 20)

                ){
                    Text("Glassmorphism 1")

                }

                GlassCard(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(200.dp),
                    blurRadius = 20.dp
                ) {
                    Text("Glassmorphism Applied")
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Red, Color.Green, Color.Blue)
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .glassBlur(radius = 20)
                    ) {
                        Text("Glassmorphism Applied")

                    }
                }
            }
        }
    }
}


@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 20.dp,
    blurOpacity: Float = 0.15f,
    blurColor: Color = Color.White,
    cornerRadius: Dp = 20.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .glassBlur(radius = 20)
            .clip(RoundedCornerShape(cornerRadius))
            .border(1.dp, blurColor.copy(alpha = 0.1f), RoundedCornerShape(cornerRadius))
            .shadow(4.dp, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center,
        content = content
    )
}
@Preview(showBackground = true)
@Composable
fun PreviewGlassScreen() {
    GlassScreen()
}
