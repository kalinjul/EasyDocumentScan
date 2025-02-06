package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
expect fun rememberDocumentScanner(
    onResult: (List<KmpImage>) -> Unit,
    options: DocumentScannerOptions = DocumentScannerOptions(DocumentScannerOptionsAndroid(), DocumentScannerOptionsIos())
): DocumentScanner

interface DocumentScanner {
    @Throws(DocumentScannerException::class)
    fun scan()
}

data class DocumentScannerOptions(
    val android: DocumentScannerOptionsAndroid,
    val ios: DocumentScannerOptionsIos
)

data class DocumentScannerOptionsAndroid(
    val pageLimit: Int = 1,
    val allowGalleryImport: Boolean = false,
    val scannerMode: DocumentScannerModeAndroid = DocumentScannerModeAndroid.BASE_WITH_FILTER,
    val captureMode: DocumentCaptureMode = DocumentCaptureMode.AUTO
)

data class DocumentScannerOptionsIos(
    val captureMode: DocumentCaptureMode = DocumentCaptureMode.AUTO
)

enum class DocumentScannerModeAndroid {
    FULL, BASE_WITH_FILTER, BASE
}

enum class DocumentCaptureMode {
    AUTO, MANUAL
}