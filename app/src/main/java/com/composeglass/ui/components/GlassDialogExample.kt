package com.composeglass.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composeglass.modifier.glassBlur

@Composable
fun GlassDialogExample(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .glassBlur {
                    radius = 5
                }
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                onDismissRequest = onDismiss,
                confirmButton = {},
                title = { Text("Glass Dialog") },
                text = { content() }
            )
        }
    }
}
