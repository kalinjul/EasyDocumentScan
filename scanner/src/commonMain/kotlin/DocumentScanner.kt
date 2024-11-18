package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
expect fun rememberDocumentScanner(
    onScanned: (String) -> Unit
): DocumentScanner

interface DocumentScanner {
    fun scan()
}