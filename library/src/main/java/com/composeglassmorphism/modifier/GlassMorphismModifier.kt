package com.composeglassmorphism.modifier

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.composeglassmorphism.modifier.utils.GlassMorphismUtils

/**
 * Defines the theme mode used by the glass morphism theme effect.
 * - Auto: adapts to the system theme
 * - Light / Dark: forces the blur style regardless of the system theme
 */
enum class GlassMorphismThemeMode {
    Auto, Light, Dark
}

/**
 * Configuration class for the glass blur effect.
 * Can be used as a DSL to customize blur radius, theme mode, color and gradient.
 */
class BlurGlassConfig {
    var radius: Int = 10                       // Blur radius
    var themeMode: GlassMorphismThemeMode = GlassMorphismThemeMode.Auto // Theme mode (auto/light/dark)
    var blurColor: Color? = null               // Optional background color for blur
    var gradientColors: List<Color>? = null  // Optional gradient overlay
}

/**
 * Applies a glassmorphism-style blur modifier.
 * - On Android 12+: uses native RenderEffect.blur()
 * - On Android <12: uses native blur via C++ (JNI)
 *
 * @param configBlock DSL block to customize blur appearance
 * @return Modifier with the applied blur and gradient effect
 */
@Composable
fun Modifier.glassMorphism(
    configBlock: BlurGlassConfig.() -> Unit
): Modifier {
    val config = BlurGlassConfig().apply(configBlock)

    if (config.radius !in 1..10) {
        throw IllegalArgumentException("❌ The 'radius' must be between 1 and 25. Received:  ${config.radius}")
    }

    val isDark = when (config.themeMode) {
        GlassMorphismThemeMode.Light -> false
        GlassMorphismThemeMode.Dark -> true
        GlassMorphismThemeMode.Auto -> isSystemInDarkTheme()
    }

    val defaultBackground = if (isDark) Color.Black else Color.White
    val resolvedColor = config.blurColor ?: defaultBackground

    val fallbackGradient = Brush.verticalGradient(
        if (isDark)
            listOf(Color.Black.copy(alpha = 0.25f), Color.Black.copy(alpha = 0.05f))
        else
            listOf(Color.White.copy(alpha = 0.25f), Color.White.copy(alpha = 0.05f))
    )
    val resolvedGradient = if (config.gradientColors.isNullOrEmpty()) {
        fallbackGradient
    } else {
        require(config.gradientColors!!.size >= 2) {
            "❌ You must provide at least 2 colors for the gradient. Received: ${config.gradientColors!!.size}"
        }
        Brush.verticalGradient(config.gradientColors!!)
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // Use native blur effect on Android 12+
        glassMorphismAndroid12(
            radius = config.radius,
            backgroundColor = resolvedColor,
            gradient = resolvedGradient
        )
    } else {
        // Use custom blur effect on Android <12
        glassMorphismAndroid11(
            radius = config.radius,
            backgroundColor = resolvedColor,
            gradient = resolvedGradient
        )
    }
}

/**
 * Custom blur fallback for Android <12 using JNI-based blur engine.
 */
@SuppressLint("ModifierNodeInspectableProperties")
private data class GlassMorphismModifier(
    val radius: Int,
    val gradient: Brush,
    val backgroundColor: Color
) : ModifierNodeElement<GlassMorphismsNode>() {

    override fun create() = GlassMorphismsNode(radius, gradient, backgroundColor)

    override fun update(node: GlassMorphismsNode) {
        val changed =
            node.radius != radius || node.gradient != gradient || node.backgroundColor != backgroundColor
        node.radius = radius
        node.gradient = gradient
        node.backgroundColor = backgroundColor
        if (changed) node.clearCache()
    }

    override fun equals(other: Any?) = other is GlassMorphismModifier &&
            other.radius == radius &&
            other.gradient == gradient &&
            other.backgroundColor == backgroundColor

    override fun hashCode() =
        31 * radius.hashCode() + gradient.hashCode() + backgroundColor.hashCode()
}

/**
 * Modifier node that draws the blurred content using native bitmap processing.
 */
private class GlassMorphismsNode(
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

        // Re-capture and blur if the size changed
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
     * Renders the content into an ImageBitmap to apply the blur effect.
     */
    private fun ContentDrawScope.drawIntoImage(width: Int, height: Int): ImageBitmap {
        val bitmap = ImageBitmap(width, height)
        val canvas = Canvas(bitmap)
        val originalCanvas = drawContext.canvas
        drawContext.canvas = canvas

        // Draw solid background before rendering content
        canvas.drawRect(
            Rect(0f, 0f, width.toFloat(), height.toFloat()),
            Paint().apply { color = backgroundColor }
        )

        drawContent()

        // Restore original canvas
        drawContext.canvas = originalCanvas
        return bitmap
    }

    /**
     * Applies a native (C++) blur to the image.
     */
    private fun applyNativeBlur(bitmap: ImageBitmap, radius: Int): ImageBitmap {
        val androidBitmap = bitmap.asAndroidBitmap().copy(Bitmap.Config.ARGB_8888, true)
        GlassMorphismUtils.nativeGlassMorphismBitmap(androidBitmap, radius)
        return androidBitmap.asImageBitmap()
    }

    /**
     * Clears cached image and forces a redraw.
     */
    fun clearCache() {
        blurredBitmap = null
        invalidateDraw()
    }
}

/**
 * Native GlassMorphism implementation using Android 12+ RenderEffect.
 */
fun Modifier.glassMorphismAndroid12(
    radius: Int,
    gradient: Brush,
    backgroundColor: Color,
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
 * Fallback implementation for Android <12 using native C++ GlassMorphism.
 */
fun Modifier.glassMorphismAndroid11(
    radius: Int,
    backgroundColor: Color,
    gradient: Brush
): Modifier {
    return this.then(
        GlassMorphismModifier(
            radius = radius,
            gradient = gradient,
            backgroundColor = backgroundColor
        )
    )
}
