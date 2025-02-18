package com.composeglass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composeglass.components.GlassButtonExample
import com.composeglass.components.GlassCardExample
import com.composeglass.components.GlassImageExample
import com.composeglass.components.GlassListExample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassScreen() {
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
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                GlassCardExample()
                Spacer(modifier = Modifier.height(16.dp))
                GlassButtonExample()
                Spacer(modifier = Modifier.height(16.dp))
                GlassImageExample(painterResource(id = R.drawable.blur_test_image))
                Spacer(modifier = Modifier.height(16.dp))
                GlassListExample(listOf("Elemento 1", "Elemento 2", "Elemento 3"))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGlassScreen() {
    GlassScreen()
}
