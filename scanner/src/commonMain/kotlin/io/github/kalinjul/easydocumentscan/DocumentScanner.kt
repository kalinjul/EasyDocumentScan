package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable

@Composable
expect fun rememberDocumentScanner(
    onResult: (List<KmpImage>) -> Unit
): DocumentScanner

interface DocumentScanner {
    fun scan()
}