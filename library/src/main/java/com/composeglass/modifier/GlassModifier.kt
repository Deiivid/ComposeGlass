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
import android.view.Window
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntSize
import androidx.core.view.drawToBitmap
import com.composeglass.modifier.oldVersionBlur.BlurUtils
import kotlinx.coroutines.delay

fun Modifier.glassBlur(radius: Int): Modifier = composed {
    val context = LocalContext.current
    val view = LocalView.current
    var blurredImage by remember { mutableStateOf<Bitmap?>(null) }
    var targetRect by remember { mutableStateOf<Rect?>(null) }
    var targetSize by remember { mutableStateOf(IntSize.Zero) }

    val window: Window? = (context as? ContextWrapper)?.findActivity()?.window

    // 👇 Aquí es donde diferimos el PixelCopy
    LaunchedEffect(targetRect) {
        targetRect?.let { rect ->
            delay(100) // esperamos un frame aprox
            val dest = Bitmap.createBitmap(rect.width(), rect.height(), Bitmap.Config.ARGB_8888)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && window != null) {
                PixelCopy.request(window, rect, dest, { result ->
                    if (result == PixelCopy.SUCCESS) {
                        println("✅ PixelCopy capturado correctamente")
                        BlurUtils.nativeBlurBitmap(dest, radius)
                        println("✅ Blur aplicado correctamente")
                        blurredImage = dest
                    } else {
                        println("❌ PixelCopy falló con código: $result")
                    }
                }, Handler(Looper.getMainLooper()))
            } else {
                try {
                    val fullBitmap = view.drawToBitmap(Bitmap.Config.ARGB_8888)
                    val cropped = Bitmap.createBitmap(
                        fullBitmap,
                        rect.left,
                        rect.top,
                        rect.width(),
                        rect.height()
                    )
                    fullBitmap.recycle()
                    BlurUtils.nativeBlurBitmap(cropped, radius)
                    blurredImage = cropped
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    this
        .onGloballyPositioned { coords ->
            val position = coords.positionInWindow()
            val size = coords.size
            if (size != targetSize) {
                targetSize = size
                targetRect = Rect(
                    position.x.toInt(),
                    position.y.toInt(),
                    position.x.toInt() + size.width,
                    position.y.toInt() + size.height
                )
            }
        }
        .drawWithContent {
            blurredImage?.let { bmp ->
                println("✅ Dibujando imagen desenfocada")
                drawImage(bmp.asImageBitmap())
            } ?: run {
                println("🕐 Aún no hay imagen para dibujar")
                drawContent()
            }
        }
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}