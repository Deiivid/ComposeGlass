package com.composeglass.modifier

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.oldVersionBlur.RenderScriptToolkit
enum class ThemeMode { AUTO, DARK, LIGHT }

fun Modifier.glassEffect(
    enabled: Boolean = true,
    blurRadius: Dp = 20.dp,
    blurOpacity: Float = 0.5f,
    blurColor: Color = Color.White
): Modifier = composed {
    if (!enabled) return@composed this

    val context = LocalContext.current
    val view = LocalView.current
    val density = LocalDensity.current
    val blurPx = with(density) { blurRadius.toPx() }

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var lastRect by remember { mutableStateOf<Rect?>(null) }
    val window = context.findActivity()?.window

    this
        .onGloballyPositioned { coordinates ->
            if (window == null) return@onGloballyPositioned
            val pos = coordinates.positionInWindow()
            val size = coordinates.size
            if (size.width <= 0 || size.height <= 0) return@onGloballyPositioned

            val rect = Rect(pos.x.toInt(), pos.y.toInt(), pos.x.toInt() + size.width, pos.y.toInt() + size.height)
            if (rect == lastRect) return@onGloballyPositioned
            lastRect = rect

            val dest = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
            PixelCopy.request(window, rect, dest, { result ->
                if (result == PixelCopy.SUCCESS) {
                    imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        dest.asImageBitmap()
                    } else {
                        val output = Bitmap.createBitmap(dest.width, dest.height, Bitmap.Config.ARGB_8888)
                        RenderScriptToolkit.blur(dest, output, blurPx.toInt())
                        output.asImageBitmap()
                    }
                } else {
                    imageBitmap = null
                }
            }, Handler(Looper.getMainLooper()))
        }
        .drawBehind {
            imageBitmap?.let { img ->
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val frameworkPaint = paint.asFrameworkPaint()
                        frameworkPaint.isAntiAlias = true
                        val effect = android.graphics.RenderEffect.createBlurEffect(
                            blurPx,
                            blurPx,
                            android.graphics.Shader.TileMode.CLAMP
                        )
                        frameworkPaint.setShadowLayer(0f, 0f, 0f, android.graphics.Color.TRANSPARENT) // evitar errores de HW
                        frameworkPaint.setRenderEffectCompat(effect)
                    }
                    canvas.drawImage(img, Offset.Zero, paint)
                }
            }
            if (blurOpacity > 0f) {
                drawRect(color = blurColor, alpha = blurOpacity)
            }
        }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
@RequiresApi(Build.VERSION_CODES.S)
@Suppress("DEPRECATION")
fun android.graphics.Paint.setRenderEffectCompat(effect: android.graphics.RenderEffect) {
    try {
        val method = android.graphics.Paint::class.java.getDeclaredMethod("setRenderEffect", android.graphics.RenderEffect::class.java)
        method.isAccessible = true
        method.invoke(this, effect)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}