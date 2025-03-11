import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.composeglass.modifier.ThemeMode
import com.composeglass.modifier.glassEffect

@Composable
fun BlurContainer(
    modifier: Modifier = Modifier,
    blurRadius: Int = 15,
    blurOpacity: Float = 0.3f,
    blurColor: Color = Color.White,
    borderColor: Color = Color.Transparent,
    borderWidth: Float = 0f,
    useThemeColors: Boolean = false,
    themeMode: ThemeMode = ThemeMode.AUTO,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Si es Android 12 o superior, usa glassEffect()
    val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        Modifier.glassEffect(
            blurRadius = blurRadius,
            blurOpacity = blurOpacity,
            blurColor = blurColor,
            useThemeColors = useThemeColors,
            themeMode = themeMode
        )
    } else {
        // Si es Android 11 o inferior, usa drawBehind con un bitmap desenfocado
        Modifier.drawBehind {
            val bitmap = Bitmap.createBitmap(
                size.width.toInt(),
                size.height.toInt(),
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawIntoCanvas {
                it.nativeCanvas.drawBitmap(
                    blurBitmap(context, bitmap, blurRadius),
                    0f,
                    0f,
                    Paint()
                )
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .then(blurModifier)
            .then(
                if (borderWidth > 0f) Modifier.border(borderWidth.dp, borderColor) else Modifier
            ),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

/**
 * Simula un desenfoque usando RenderScript en Android 11 e inferior.
 */
fun blurBitmap(context: android.content.Context, bitmap: Bitmap, radius: Int): Bitmap {
    val rs = android.renderscript.RenderScript.create(context)
    val input = android.renderscript.Allocation.createFromBitmap(rs, bitmap)
    val output = android.renderscript.Allocation.createTyped(rs, input.type)
    val script =
        android.renderscript.ScriptIntrinsicBlur.create(rs, android.renderscript.Element.U8_4(rs))

    script.setRadius(radius.toFloat())
    script.setInput(input)
    script.forEach(output)
    output.copyTo(bitmap)

    return bitmap
}
