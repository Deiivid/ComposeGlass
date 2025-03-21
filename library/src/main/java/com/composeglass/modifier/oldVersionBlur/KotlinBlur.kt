import android.graphics.Bitmap
import com.composeglass.modifier.oldVersionBlur.BlurNative


fun Bitmap.gaussianBlurNative(radius: Int): Bitmap {
    if (radius < 1) return this
    // Asegurarnos de que sea mutable
    val mutableBmp = if (!isMutable) copy(config!!, true) else this

    val array = IntArray(width * height)
    mutableBmp.getPixels(array, 0, width, 0, 0, width, height)

    // Llamamos a la función nativa
    BlurNative.gaussianBlur(array, width, height, radius)

    // Retornamos los píxeles ya desenfocados
    mutableBmp.setPixels(array, 0, width, 0, 0, width, height)
    return mutableBmp
}