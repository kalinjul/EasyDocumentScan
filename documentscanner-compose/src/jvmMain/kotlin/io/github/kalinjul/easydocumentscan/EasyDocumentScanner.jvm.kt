package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
actual fun rememberDocumentScanner(
    onResult: (Result<List<KmpImage>>) -> Unit,
    options: DocumentScannerOptions
): DocumentScanner {
    return object: DocumentScanner {
        override fun scan() {
            println("Scanner not implemented for JVM")
        }
    }
}