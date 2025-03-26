package com.composeglass.modifier

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.oldVersionBlur.BlurUtils

enum class BlurThemeMode {
    Auto, Light, Dark
}

@Composable
fun Modifier.glassBlur(
    radius: Int,
    themeMode: BlurThemeMode = BlurThemeMode.Auto,
    gradient: Brush? = null
): Modifier {
    val isDark = when (themeMode) {
        BlurThemeMode.Light -> false
        BlurThemeMode.Dark -> true
        BlurThemeMode.Auto -> isSystemInDarkTheme()
    }

    val backgroundColor = if (isDark) Color.Black else Color.White
    val overlayOpacity = if (isDark) 0.7f else 0.3f

    val resolvedGradient = gradient ?: Brush.verticalGradient(
        if (isDark)
            listOf(
                Color.Black.copy(alpha = 0.25f),
                Color.Black.copy(alpha = 0.05f)
            )
        else
            listOf(
                Color.White.copy(alpha = 0.25f),
                Color.White.copy(alpha = 0.05f)
            )
    )

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        glassBlurAndroid12(
            radius = radius,
            backgroundColor = backgroundColor,
            blurOpacity = overlayOpacity,
            gradient = resolvedGradient
        )
    } else {
        glassBlurAndroid11(
            radius = radius,
            backgroundColor = backgroundColor,
            gradient = resolvedGradient
        )
    }
}

private data class BlurGlassModifier(
    val radius: Int,
    val gradient: Brush,
    val backgroundColor: Color
) : ModifierNodeElement<BlurGlassNode>() {

    override fun create() = BlurGlassNode(radius, gradient, backgroundColor)

    override fun update(node: BlurGlassNode) {
        val changed =
            node.radius != radius || node.gradient != gradient || node.backgroundColor != backgroundColor
        node.radius = radius
        node.gradient = gradient
        node.backgroundColor = backgroundColor
        if (changed) node.clearCache()
    }

    override fun equals(other: Any?) = other is BlurGlassModifier &&
            other.radius == radius &&
            other.gradient == gradient &&
            other.backgroundColor == backgroundColor

    override fun hashCode() =
        31 * radius.hashCode() + gradient.hashCode() + backgroundColor.hashCode()
}

private class BlurGlassNode(
    var radius: Int,
    var gradient: Brush,
    var backgroundColor: Color
) : Modifier.Node(), DrawModifierNode {

    private var blurredBitmap: ImageBitmap? = null
    private var lastSize: IntSize = IntSize.Zero

    override fun ContentDrawScope.draw() {
        val width = size.width.toInt()
        val height = size.height.toInt()
        val currentSize = IntSize(width, height)

        if (blurredBitmap == null || currentSize != lastSize) {
            lastSize = currentSize
            val contentBitmap = drawIntoImage(width, height)
            blurredBitmap = applyNativeBlur(contentBitmap, radius)
        }

        blurredBitmap?.let {
            drawImage(it)
            drawRect(brush = gradient, blendMode = BlendMode.Overlay)
        } ?: drawContent()
    }

    private fun ContentDrawScope.drawIntoImage(width: Int, height: Int): ImageBitmap {
        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)
        val original = drawContext.canvas
        drawContext.canvas = canvas

        canvas.drawRect(
            Rect(0f, 0f, width.toFloat(), height.toFloat()),
            Paint().apply { color = backgroundColor }
        )

        drawContent()
        drawContext.canvas = original
        return bitmap
    }

    private fun applyNativeBlur(bitmap: ImageBitmap, radius: Int): ImageBitmap {
        val androidBitmap = bitmap.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
        BlurUtils.nativeBlurBitmap(androidBitmap, radius)
        return androidBitmap.asImageBitmap()
    }

    fun clearCache() {
        blurredBitmap = null
        invalidateDraw()
    }
}


fun Modifier.glassBlurAndroid12(
    radius: Int,
    gradient: Brush,
    backgroundColor: Color,
    blurOpacity: Float
): Modifier {
    val adjustedRadius = (radius * 0.6).coerceIn(0.0, 25.0)
    return this
        .background(backgroundColor)
        .blur(adjustedRadius.dp)
        .drawWithContent {
            drawContent()
            drawRect(brush = gradient, blendMode = BlendMode.Overlay)
        }
}


fun Modifier.glassBlurAndroid11(
    radius: Int,
    backgroundColor: Color,
    gradient: Brush
): Modifier {
    return this.then(
        BlurGlassModifier(
            radius = radius,
            gradient = gradient,
            backgroundColor = backgroundColor
        )
    )
}