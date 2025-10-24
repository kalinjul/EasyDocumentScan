package io.github.kalinjul.easydocumentscan

sealed class DocumentScannerException(message: String): Exception(message) {
    data class NotAuthorized(override val message: String): DocumentScannerException(message = message)
    data class Unknown(override val message: String): DocumentScannerException(message = message)
}