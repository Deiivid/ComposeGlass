package com.composeglass.modifier

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.IntSize
import com.composeglass.modifier.oldVersionBlur.BlurUtils

fun Modifier.glassBlur(
    radius: Int,
    gradient: Brush = Brush.verticalGradient(
        listOf(
            Color.White.copy(alpha = 0.25f),
            Color.White.copy(alpha = 0.05f)
        )
    )
): Modifier = this then BlurGlassModifier(radius, gradient)

@SuppressLint("ModifierNodeInspectableProperties")
private data class BlurGlassModifier(
    val radius: Int,
    val gradient: Brush
) : ModifierNodeElement<BlurGlassNode>() {
    override fun create() = BlurGlassNode(radius, gradient)
    override fun update(node: BlurGlassNode) {
        node.radius = radius
        node.gradient = gradient
    }
    override fun hashCode() = radius.hashCode() * 31 + gradient.hashCode()
    override fun equals(other: Any?) = other is BlurGlassModifier &&
            other.radius == radius && other.gradient == gradient
}
private class BlurGlassNode(
    var radius: Int,
    var gradient: Brush
) : Modifier.Node(), DrawModifierNode {

    private var blurredBitmap: ImageBitmap? = null
    private var lastSize: IntSize = IntSize.Zero

    override fun ContentDrawScope.draw() {
        val intWidth = size.width.toInt()
        val intHeight = size.height.toInt()
        val currentSize = IntSize(intWidth, intHeight)

        if (blurredBitmap == null || currentSize != lastSize) {
            lastSize = currentSize
            val contentBitmap = drawIntoImage(intWidth, intHeight)
            val testCanvas = Canvas(contentBitmap)
            testCanvas.drawRect(Rect(0f, 0f, 100f, 100f), Paint().apply { color = Color.Transparent })
            val blurred = applyNativeBlur(contentBitmap, radius)

            blurredBitmap = blurred
        }

        blurredBitmap?.let {
            drawImage(it)
            drawRect(brush = gradient, blendMode = BlendMode.Overlay)
        } ?: run {
            drawContent()
        }
    }

    private fun ContentDrawScope.drawIntoImage(width: Int, height: Int): ImageBitmap {
        val imageBitmap = ImageBitmap(width, height)
        val canvas = Canvas(imageBitmap)
        val originalCanvas = drawContext.canvas
        drawContext.canvas = canvas
        canvas.drawRect(
            Rect(0f, 0f, width.toFloat(), height.toFloat()),
            Paint().apply { color = Color.White }
        )
        drawContent()
        drawContext.canvas = originalCanvas
        return imageBitmap
    }

    private fun applyNativeBlur(bitmap: ImageBitmap, radius: Int): ImageBitmap {
        val androidBitmap = bitmap.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
        BlurUtils.nativeBlurBitmap(androidBitmap, radius)
        return androidBitmap.asImageBitmap()
    }
}