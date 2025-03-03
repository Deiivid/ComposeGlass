package com.composeglass.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composeglass.ui.components.GlassBlurContainerExample
import com.composeglass.ui.components.GlassButtonExample
import com.composeglass.ui.components.GlassCardExample
import com.composeglass.ui.components.GlassDialogExample
import com.composeglass.ui.components.GlassFloatingActionButton
import com.composeglass.ui.components.GlassImageExample
import com.composeglass.ui.components.GlassListExample
import com.composeglass.ui.components.GlassListWithHeaderExample
import com.composeglass.ui.components.GlassTopAppBarExample

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
                GlassImageExample(painterResource(id = com.composeglass.R.drawable.blur_test_image),false)
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    GlassListExample(glassItems)
                }
                GlassFloatingActionButton(onClick = { showDialog = true }) { Text("FAB") }
                GlassDialogExample(showDialog = showDialog, onDismiss = { showDialog = false }) {
                    Text("This is a Glass Dialog")
                }
                GlassTopAppBarExample(title = "Custom Title")
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    GlassListWithHeaderExample(glassItems)
                }

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
