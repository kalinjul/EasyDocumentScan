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
    onResult: (Result<List<KmpImage>>) -> Unit,
    options: DocumentScannerOptions
): DocumentScanner {
    val context = LocalContext.current

    val options = remember {
        val scannerMode = when (options.android.scannerMode) {
            DocumentScannerModeAndroid.FULL -> GmsDocumentScannerOptions.SCANNER_MODE_FULL
            DocumentScannerModeAndroid.BASE_WITH_FILTER -> GmsDocumentScannerOptions.SCANNER_MODE_BASE_WITH_FILTER
            DocumentScannerModeAndroid.BASE -> GmsDocumentScannerOptions.SCANNER_MODE_BASE
        }

        GmsDocumentScannerOptions.Builder()
            .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_JPEG) // PDF format not supported on ios, so disable in android, too
            .setGalleryImportAllowed(options.android.allowGalleryImport)
            .setScannerMode(scannerMode)
            .setPageLimit(options.android.pageLimit)
            .build()
    }

    val gmsScanner = remember(options) {
        GmsDocumentScanning.getClient(options)
    }

    val scannerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val gmsResult = GmsDocumentScanningResult.fromActivityResultIntent(result.data)
            gmsResult?.pages?.let { pages ->
                val images = pages.map {
                    AndroidImage(platformFile = it.imageUri, type = "jpeg", contentResolver = context.contentResolver)
                }
                onResult(Result.success(images))
            }

//            gmsResult?.pdf?.let { pdf ->
//                val pdfUri = pdf.uri
//                println("PDF: $pdfUri")
//                onScanned(pdfUri.toString())
//            }
        }
    }

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