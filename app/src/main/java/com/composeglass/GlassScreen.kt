package com.composeglass

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
            TopAppBar(title = { Text("Glassmorphism Demo") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
               GlassCardExample()
               Spacer(modifier = Modifier.height(16.dp))
               GlassButtonExample()
               Spacer(modifier = Modifier.height(16.dp))
               GlassImageExample(painterResource(id = android.R.drawable.ic_menu_gallery))
               Spacer(modifier = Modifier.height(16.dp))
               GlassListExample(listOf("Elemento 1", "Elemento 2", "Elemento 3"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGlassScreen() {
    GlassScreen()
}
