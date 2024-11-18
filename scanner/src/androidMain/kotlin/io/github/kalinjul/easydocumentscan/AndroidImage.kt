package io.github.kalinjul.easydocumentscan

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

data class AndroidImage(
    val platformFile: Uri,
    override val type: String,
    val contentResolver: ContentResolver
) : KmpImage {
    override suspend fun loadBytes(): ByteArray {
        val inputStreamRotation = contentResolver.openInputStream(platformFile) ?: throw Exception("Cannot read file: $platformFile")
        val rotation = inputStreamRotation.getRotation()
        val inputStreamBytes = contentResolver.openInputStream(platformFile) ?: throw Exception("Cannot read file: $platformFile")
        if (rotation == 0) {
            return inputStreamBytes.readBytes()
        } else {
            val srcBytes = inputStreamBytes.readBytes()
            val rotatedBitmap = srcBytes.toBitmap().rotate(rotation)
            val baos = ByteArrayOutputStream()
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
            return baos.toByteArray()
        }
    }

    override suspend fun loadImage(): ImageBitmap {
        val inputStream = contentResolver.openInputStream(platformFile)
        val bytes = inputStream?.readBytes() ?: throw Exception("Cannot read file: $platformFile")

        val bitmap = bytes.toBitmap().let { bitmap ->
            contentResolver.openInputStream(platformFile)
                ?.let { inputStream -> bitmap.rotateImageIfRequired(inputStream) }
                ?: bitmap
        }

        return bitmap.asImageBitmap()
    }
}


@Throws(IOException::class)
private fun Bitmap.rotateImageIfRequired(inputStream: InputStream): Bitmap {
    val rotation = inputStream.getRotation()

    println("Rotation: $rotation")
    return if (rotation == 0) {
        this
    } else {
        rotate(rotation)
    }
}

private fun InputStream.getRotation(): Int {
    val ei = ExifInterface(this)
    val orientation =
        ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
}

private fun Bitmap.rotate(degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedImg = Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
    recycle()
    return rotatedImg
}
