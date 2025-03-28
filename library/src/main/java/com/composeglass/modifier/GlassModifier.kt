package com.composeglass.modifier

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
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
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.oldVersionBlur.BlurUtils

/**
 * Enum to define the theme mode for the blur effect.
 */
enum class BlurThemeMode {
    Auto, Light, Dark
}

/**
 * Applies a glassmorphism blur effect to the Composable.
 * Uses Android 12+ native blur or a custom blur fallback for older versions.
 *
 * @param radius The blur radius in dp.
 * @param themeMode Automatically adapts to system theme unless forced.
 * @param gradient Optional gradient overlay. If null, a default is used based on theme.
 */

class BlurGlassConfig {
    var radius: Int = 20
    var themeMode: BlurThemeMode = BlurThemeMode.Auto
    var blurColor: Color? = null
    var gradient: Brush? = null
}


@Composable
fun Modifier.glassBlur(
    configBlock: BlurGlassConfig.() -> Unit
): Modifier {
    val config = BlurGlassConfig().apply(configBlock)

    val isDark = when (config.themeMode) {
        BlurThemeMode.Light -> false
        BlurThemeMode.Dark -> true
        BlurThemeMode.Auto -> isSystemInDarkTheme()
    }

    val defaultBackground = if (isDark) Color.Black else Color.White
    val overlayOpacity = if (isDark) 0.7f else 0.3f

    val resolvedColor = config.blurColor ?: defaultBackground

    val resolvedGradient = config.gradient ?: Brush.verticalGradient(
        if (isDark)
            listOf(Color.Black.copy(alpha = 0.25f), Color.Black.copy(alpha = 0.05f))
        else
            listOf(Color.White.copy(alpha = 0.25f), Color.White.copy(alpha = 0.05f))
    )

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        glassBlurAndroid12(
            radius = config.radius,
            backgroundColor = resolvedColor,
            blurOpacity = overlayOpacity,
            gradient = resolvedGradient
        )
    } else {
        glassBlurAndroid11(
            radius = config.radius,
            backgroundColor = resolvedColor,
            gradient = resolvedGradient
        )
    }
}

/**
 * Modifier for Android < 12 using a custom draw implementation with native blur.
 */
@SuppressLint("ModifierNodeInspectableProperties")
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

/**
 * DrawModifierNode that applies blur to the captured content using native C++ blur.
 */
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

        // Re-capture and blur bitmap if size changes
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

    /**
     * Captures the current drawing content into an ImageBitmap.
     */
    private fun ContentDrawScope.drawIntoImage(width: Int, height: Int): ImageBitmap {
        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)
        val original = drawContext.canvas
        drawContext.canvas = canvas

        // Fill with background color before drawing content
        canvas.drawRect(
            Rect(0f, 0f, width.toFloat(), height.toFloat()),
            Paint().apply { color = backgroundColor }
        )

        drawContent()
        drawContext.canvas = original
        return bitmap
    }

    /**
     * Applies the native blur using RenderScriptToolkit or C++ (from BlurUtils).
     */
    private fun applyNativeBlur(bitmap: ImageBitmap, radius: Int): ImageBitmap {
        val androidBitmap = bitmap.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
        BlurUtils.nativeBlurBitmap(androidBitmap, radius)
        return androidBitmap.asImageBitmap()
    }

    /**
     * Clears the cached blurred image and triggers re-draw.
     */
    fun clearCache() {
        blurredBitmap = null
        invalidateDraw()
    }
}

/**
 * Modifier for Android 12+ using native blur and overlay gradient.
 *
 * @param radius The blur radius.
 * @param gradient The gradient to overlay.
 * @param backgroundColor The color below the blur.
 * @param blurOpacity The alpha to apply on background color.
 */
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

/**
 * Modifier fallback for Android < 12 that applies custom native blur via Modifier.Node.
 *
 * @param radius The blur radius.
 * @param backgroundColor The solid background behind the content.
 * @param gradient The gradient overlay.
 */
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