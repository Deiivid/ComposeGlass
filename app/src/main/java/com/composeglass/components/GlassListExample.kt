package com.composeglass.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.blurGlass

@Composable
fun GlassListExample(items: List<String>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .blurGlass(blurRadius = 20, blurColor = Color.Gray.copy(alpha = 0.2f))
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.6f))
                )
            }
        }
    }
}
