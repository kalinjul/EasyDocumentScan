package io.github.kalinjul.easydocumentscan

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.posix.memcpy

data class IosImage(
    val image: UIImage
): KmpImage {
//    override val path: String = ""
    override val type: String = "jpeg"

    override suspend fun loadBytes(): ByteArray {
        val jpeg = UIImageJPEGRepresentation(image, 0.9)
        return jpeg?.toByteArray() ?: throw LoadBytesException("Could not convert to jpeg")
    }

    override suspend fun loadImage(): ImageBitmap {
        return loadBytes().toImageBitmap()
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
    }
}
