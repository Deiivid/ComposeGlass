package com.composeglassmorphism.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composeglassmorphism.modifier.glassMorphism

@Composable
fun GlassMorphismListWithHeaderExample(items: List<String>) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .glassMorphism {
                    radius = 10
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Glass Header", textAlign = TextAlign.Center, modifier = Modifier.glassMorphism {
                radius = 10
                blurColor = Color.Red
            })
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                Text(
                    text = item, modifier = Modifier
                        .padding(16.dp)
                        .glassMorphism { radius = 4 })
            }
        }
    }
}
