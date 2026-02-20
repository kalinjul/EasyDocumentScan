# EasyDocumentScan: Compose Multiplatform Document Scanner

[![CI Status](https://img.shields.io/github/actions/workflow/status/kalinjul/EasyDocumentScan/main.yml)]((https://github.com/kalinjul/EasyDocumentScan/actions/workflows/main.yml))
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kalinjul.easydocumentscan/documentscanner-compose)](https://repo1.maven.org/maven2/io/github/kalinjul/easydocumentscan/documentscanner-compose/)
![Kotlin Version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=io.github.kalinjul.easydocumentscan&name=documentscanner-compose)
![Compose Version](https://img.shields.io/badge/dynamic/toml?url=https%3A%2F%2Fraw.githubusercontent.com%2Fkalinjul%2FEasyDocumentScan%2Fmain%2Fgradle%2Flibs.versions.toml&query=%24.versions%5B'compose-multiplatform'%5D&label=Compose%20Version)

Document Scanner for Compose Multiplatform (Android/iOS) using [VNDocumentCameraViewController](https://developer.apple.com/documentation/visionkit/vndocumentcameraviewcontroller) on iOS and [MLKit Document scanner](https://developers.google.com/ml-kit/vision/doc-scanner) on Android.

Supported Compose versions:

| EasyDocumentScan Version | Compose version |
|--------------------------|-----------------|
| 0.1.0+                   | 1.7             |
| 0.2.0+                   | 1.8             |
| 0.3.0+                   | 1.9             |
| 0.4.0+                   | 1.10            |

# Dependency
> [!NOTE]
> Starting with 0.3.2, artifact id changed from "documentscanner" to "documentscanner-compose" and a new "documentscanner-core" artifact was introduced that contains the KmpFile abstraction as well as some Helper functions.

Add the dependency to your commonMain sourceSet (KMP) / Android dependencies (android only):
```kotlin
implementation("io.github.kalinjul.easydocumentscan:documentscanner-compose:0.3.2")
```

Or, for your libs.versions.toml:
```toml
[versions]
easydocumentscanner = "0.3.2"
[libraries]
easydocumentscanner-compose = { module = "io.github.kalinjul.easydocumentscan:documentscanner-compose", version.ref = "easydocumentscanner" }
easydocumentscanner-core = { module = "io.github.kalinjul.easydocumentscan:documentscanner-core", version.ref = "easydocumentscanner" }
```

# Usage
```kotlin
val scanner = rememberDocumentScanner(
 onResult = {
     // handle result images
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
 Button(onClick = { scanner.scan() }) {
     Text("Scan")
 }
```

Check out the included sample app.