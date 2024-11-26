package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
expect fun rememberDocumentScanner(
    onResult: (List<KmpImage>) -> Unit,
    options: DocumentScannerOptions = DocumentScannerOptions(DocumentScannerOptionsAndroid())
): DocumentScanner

interface DocumentScanner {
    fun scan()
}

data class DocumentScannerOptions(
    val android: DocumentScannerOptionsAndroid,
)

data class DocumentScannerOptionsAndroid(
    val pageLimit: Int = 1,
    val allowGalleryImport: Boolean = false,
    val scannerMode: DocumentScannerModeAndroid = DocumentScannerModeAndroid.BASE_WITH_FILTER,
    val captureMode: DocumentCaptureModeAndroid = DocumentCaptureModeAndroid.AUTO
)

enum class DocumentScannerModeAndroid {
    FULL, BASE_WITH_FILTER, BASE
}

enum class DocumentCaptureModeAndroid {
    AUTO, MANUAL
}