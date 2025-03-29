package com.composeglass.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.BlurThemeMode
import com.composeglass.modifier.glassBlur
import com.composeglass.ui.components.GlassBlurContainerExample
import com.composeglass.ui.components.GlassButtonExample
import com.composeglass.ui.components.GlassCardExample
import com.composeglass.ui.components.GlassDialogExample
import com.composeglass.ui.components.GlassFloatingActionButton
import com.composeglass.ui.components.GlassImageExample
import com.composeglass.ui.components.GlassListExample

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
                GlassCardExample()
                GlassButtonExample()

                GlassImageExample(painterResource(id = com.composeglass.R.drawable.blur_test_image))

                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    GlassListExample(glassItems)
                }
                GlassFloatingActionButton(onClick = { showDialog = true }) { Text("FAB") }
                GlassDialogExample(showDialog = showDialog, onDismiss = { showDialog = false }) {
                    Text("This is a Glass Dialog")
                }

                GlassBlurContainerExample()
                GlassBlurContainerExample()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewGlassScreen() {
    GlassScreen()
}
