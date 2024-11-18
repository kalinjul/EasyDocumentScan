package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
actual fun rememberDocumentScanner(
    onScanned: (String) -> Unit,
): DocumentScanner {
    return object: DocumentScanner {
        override fun scan() {
            println("Scanner not implemented for JVM")
        }

    }
}