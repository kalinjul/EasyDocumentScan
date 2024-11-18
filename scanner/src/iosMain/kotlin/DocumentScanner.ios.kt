package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.interop.LocalUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.removeFromParentViewController
import platform.UIKit.willMoveToParentViewController
import platform.VisionKit.VNDocumentCameraViewController

@Composable
actual fun rememberDocumentScanner(
    onScanned: (String) -> Unit
): DocumentScanner {

    val localViewController = LocalUIViewController.current
    val documentCameraViewController = remember {
        VNDocumentCameraViewController()
    }

    val delegate = remember(documentCameraViewController) {
        DocumentScannerDelegate(
            onError = {
                println("delegate: onerror")
                documentCameraViewController.dismissViewControllerAnimated(true) {}
                throw DocumentScannerException(it.localizedDescription)
            },
            onCancel = {
                println("delegate: oncancel")
                documentCameraViewController.dismissViewControllerAnimated(true) {}
            },
            onResult = {
                println("delegate: onResult")
                documentCameraViewController.dismissViewControllerAnimated(true) {}
                onScanned(it.toString())
            }
        )
    }

    return object: DocumentScanner {
        override fun scan() {
            documentCameraViewController.setDelegate(delegate)
            localViewController.presentViewController(documentCameraViewController, animated = true)  {}
        }
    }
}

//private fun UIViewController.popSelf() {
//    removeFromParentViewController()
//    view.willMoveToSuperview(null)
//    view.removeFromSuperview()
//}

//@OptIn(ExperimentalForeignApi::class)
//private fun UIViewController.pushViewController(documentCameraViewController: VNDocumentCameraViewController) {
//    documentCameraViewController.willMoveToParentViewController(this)
//    documentCameraViewController.view.setFrame(this.view.frame)
//    this.view.addSubview(documentCameraViewController.view)
//    this.addChildViewController(documentCameraViewController)
//    documentCameraViewController.didMoveToParentViewController(this)
//}