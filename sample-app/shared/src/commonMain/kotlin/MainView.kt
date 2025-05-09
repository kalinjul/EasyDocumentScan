package io.github.kalinjul.easydocumentscan.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import io.github.kalinjul.easydocumentscan.DocumentCaptureMode
import io.github.kalinjul.easydocumentscan.DocumentScannerModeAndroid
import io.github.kalinjul.easydocumentscan.DocumentScannerOptions
import io.github.kalinjul.easydocumentscan.DocumentScannerOptionsAndroid
import io.github.kalinjul.easydocumentscan.DocumentScannerOptionsIos
import io.github.kalinjul.easydocumentscan.KmpImage
import io.github.kalinjul.easydocumentscan.rememberDocumentScanner
import kotlinx.coroutines.launch

@Composable
fun MainView() {
    val snackbarHostState = remember() { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) {
        var images by remember { mutableStateOf<List<KmpImage>>(listOf()) }
        var bitmapImages by remember { mutableStateOf<List<ImageBitmap>>(listOf()) }

        LaunchedEffect(images) {
            images.map { it.loadImage() }.also { bitmapImages = it }
        }

        Column(modifier = Modifier.padding(it)) {
            Text("Document Scanner demo")
            val scope = rememberCoroutineScope()
            val scanner = rememberDocumentScanner(
                onResult = {
                    images = it
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "${it.size} documents scanned",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                options = DocumentScannerOptions(
                    DocumentScannerOptionsAndroid(
                        pageLimit = 3,
                        allowGalleryImport = true,
                        scannerMode = DocumentScannerModeAndroid.BASE
                    ),
                    DocumentScannerOptionsIos(
                        captureMode = DocumentCaptureMode.MANUAL
                    )
                )
            )
            Button(onClick = {
                scanner.scan()
            }) {
                Text("Scan")
            }

            LazyColumn {
                items(bitmapImages) {
                    Image(
                        bitmap = it,
                        contentDescription = null
                    )
                }
            }
        }
    }
}