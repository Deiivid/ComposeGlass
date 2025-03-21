package com.composeglass.modifier

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntSize
import kotlin.math.max
import kotlin.math.min
fun Modifier.glassBlur(
    radius: Int = 25,
    backgroundAlpha: Float = 0.4f // Menos opacidad para mejor transparencia
): Modifier = composed {
    val view = LocalView.current
    var blurredBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var lastSize by remember { mutableStateOf(IntSize.Zero) }

    this
        .onGloballyPositioned { coords ->
            val width = coords.size.width
            val height = coords.size.height

            if (width > 0 && height > 0 && lastSize != coords.size) {
                lastSize = coords.size
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)

                // Capturamos el área del Composable correctamente
                canvas.translate(-coords.positionInWindow().x, -coords.positionInWindow().y)
                view.draw(canvas)

                // Aplicamos el blur con la función optimizada
                blurredBitmap = boxBlurKotlin(bitmap, radius)
            }
        }
        .drawWithContent {
            blurredBitmap?.let {
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        alpha = (backgroundAlpha * 255).toInt() // Aplicar la transparencia
                    }
                    canvas.nativeCanvas.drawBitmap(it, 0f, 0f, paint)
                }
            }
            drawContent()
        }
}
/**
 * Aplica un Box Blur en dos pasadas (horizontal y vertical).
 */
fun boxBlurKotlin(source: Bitmap, radius: Int): Bitmap {
    if (radius < 1) return source

    val width = source.width
    val height = source.height
    val result = source.copy(Bitmap.Config.ARGB_8888, true)
    val pixels = IntArray(width * height)
    result.getPixels(pixels, 0, width, 0, 0, width, height)

    val temp = IntArray(width * height)

    // --- BLUR HORIZONTAL ---
    for (y in 0 until height) {
        val offset = y * width
        for (x in 0 until width) {
            var rSum = 0
            var gSum = 0
            var bSum = 0
            val left = max(0, x - radius)
            val right = min(width - 1, x + radius)
            val count = right - left + 1

            for (i in left..right) {
                val color = pixels[offset + i]
                rSum += (color shr 16) and 0xFF
                gSum += (color shr 8) and 0xFF
                bSum += color and 0xFF
            }

            temp[offset + x] = (0xFF shl 24) or ((rSum / count) shl 16) or ((gSum / count) shl 8) or (bSum / count)
        }
    }

    // --- BLUR VERTICAL ---
    for (x in 0 until width) {
        for (y in 0 until height) {
            var rSum = 0
            var gSum = 0
            var bSum = 0
            val top = max(0, y - radius)
            val bottom = min(height - 1, y + radius)
            val count = bottom - top + 1

            for (i in top..bottom) {
                val color = temp[i * width + x]
                rSum += (color shr 16) and 0xFF
                gSum += (color shr 8) and 0xFF
                bSum += color and 0xFF
            }

            pixels[y * width + x] = (0xFF shl 24) or ((rSum / count) shl 16) or ((gSum / count) shl 8) or (bSum / count)
        }
    }

    result.setPixels(pixels, 0, width, 0, 0, width, height)
    return result
}