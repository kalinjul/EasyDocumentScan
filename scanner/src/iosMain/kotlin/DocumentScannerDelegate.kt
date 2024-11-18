package io.github.kalinjul.easydocumentscan

import platform.Foundation.NSError
import platform.VisionKit.VNDocumentCameraScan
import platform.VisionKit.VNDocumentCameraViewController
import platform.VisionKit.VNDocumentCameraViewControllerDelegateProtocol
import platform.darwin.NSObject

class DocumentScannerDelegate(
    private val onError: (NSError) -> Unit,
    private val onCancel: () -> Unit,
    private val onResult: (VNDocumentCameraScan) -> Unit
): VNDocumentCameraViewControllerDelegateProtocol, NSObject() {
    override fun documentCameraViewControllerDidCancel(controller: VNDocumentCameraViewController) {
        onCancel()
    }

    override fun documentCameraViewController(
        controller: VNDocumentCameraViewController,
        didFailWithError: NSError
    ) {
        onError(didFailWithError)
    }

    override fun documentCameraViewController(
        controller: VNDocumentCameraViewController,
        didFinishWithScan: VNDocumentCameraScan
    ) {
        onResult(didFinishWithScan)
    }
}