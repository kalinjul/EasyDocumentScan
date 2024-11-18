package io.github.kalinjul.easydocumentscan

import android.app.Activity.RESULT_OK
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult

@Composable
actual fun rememberDocumentScanner(
    onScanned: (String) -> Unit
): DocumentScanner {
    val options = remember {
        GmsDocumentScannerOptions.Builder()
            .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_BASE)
            .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF, GmsDocumentScannerOptions.RESULT_FORMAT_JPEG)
            .setGalleryImportAllowed(false)
            .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER)
            .setPageLimit(3)
            .build()
    }

    val gmsScanner = remember(options) {
        GmsDocumentScanning.getClient(options)
    }

    val scannerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val gmsResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            gmsResult?.pages?.let { pages ->
                pages.forEach { page ->
                    val imageUri = page.imageUri
                    println("Image: $imageUri")
                }
            }
            gmsResult?.pdf?.let { pdf ->
                val pdfUri = pdf.uri
                println("PDF: $pdfUri")
                onScanned(pdfUri.toString())
            }
        }
    }

    val context = LocalContext.current

    val documentScanner = remember(gmsScanner, scannerLauncher, context) {
        object: DocumentScanner {
            override fun scan() {
                context.getActivityOrNull()?.let {
                    gmsScanner.getStartScanIntent(it)
                        .addOnSuccessListener { intentSender: IntentSender ->
                            scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                        }
                        .addOnFailureListener { e: Exception ->
                            e.message?.let { Log.e("error", it) }
                        }
                }
            }

        }
    }

    return documentScanner
}