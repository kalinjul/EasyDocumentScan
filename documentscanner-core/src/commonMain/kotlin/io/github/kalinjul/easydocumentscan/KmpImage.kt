package io.github.kalinjul.easydocumentscan

import androidx.compose.ui.graphics.ImageBitmap
import kotlin.coroutines.cancellation.CancellationException

interface KmpImage {
    val type: String

    @Throws(LoadBytesException::class, CancellationException::class)
    suspend fun loadBytes(): ByteArray

    /**
     * Load the image. This will also rotate if necessary.
     */
    @Throws(LoadBytesException::class, CancellationException::class)
    suspend fun loadImage(): ImageBitmap
}

class LoadBytesException(message: String?): Exception(message)