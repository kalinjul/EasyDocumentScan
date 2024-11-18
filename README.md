# EasyDocumentScan: Compose Multiplatform Document Scanner

[![CI Status](https://img.shields.io/github/actions/workflow/status/kalinjul/EasyDocumentScan/main.yml)]((https://github.com/kalinjul/EasyDocumentScan/actions/workflows/main.yml))
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kalinjul.easydocumentscan/scanner)](https://repo1.maven.org/maven2/io/github/kalinjul/easydocumentscan/scanner/)
[![Snapshot](https://img.shields.io/nexus/s/io.github.kalinjul.easydocumentscan/scanner?server=https%3A%2F%2Fs01.oss.sonatype.org&label=latest%20snapshot)](https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/kalinjul/easydocumentscan/scanner/)
![Kotlin Version](https://kotlin-version.aws.icerock.dev/kotlin-version?group=io.github.kalinjul.easydocumentscan&name=scanner)
![Compose Version](https://img.shields.io/badge/dynamic/toml?url=https%3A%2F%2Fraw.githubusercontent.com%2Fkalinjul%2FEasyDocumentScan%2Fmain%2Fgradle%2Flibs.versions.toml&query=%24.versions%5B'compose-multiplatform'%5D&label=Compose%20Version)

Document Scanner for Compose Multiplatform (Android/iOS) using [VNDocumentCameraViewController](https://developer.apple.com/documentation/visionkit/vndocumentcameraviewcontroller) on iOS and [MLKit Document scanner](https://developers.google.com/ml-kit/vision/doc-scanner) on Android.

Supported Compose version:

 | Compose version | EasyDocumentScan Version |
|-----------------|--------------------------|
| 1.7             | 0.0.0+                   |

# Dependency
Add the dependency to your commonMain sourceSet (KMP) / Android dependencies (android only):
```kotlin
implementation("io.github.kalinjul.easydocumentscan:scanner:0.0.1")
```

Or, for your libs.versions.toml:
```toml
[versions]
easydocumentscan = "0.0.1"
[libraries]
easydocumentscan = { module = "io.github.kalinjul.easydocumentscan:scanner", version.ref = "easydocumentscan" }
```

# Usage
TODO