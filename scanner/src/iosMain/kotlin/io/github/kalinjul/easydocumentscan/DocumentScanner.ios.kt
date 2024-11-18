package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIButton
import platform.UIKit.UILabel
import platform.UIKit.UIView
import platform.VisionKit.VNDocumentCameraViewController

@Composable
actual fun rememberDocumentScanner(
    onResult: (List<KmpImage>) -> Unit,
    options: DocumentScannerOptions
): DocumentScanner {

    val localViewController = LocalUIViewController.current

    var delegate by remember() { mutableStateOf<DocumentScannerDelegate?>(null) }

    return object: DocumentScanner {
        override fun scan() {
            val controller = VNDocumentCameraViewController()
            controller.setDelegate(
                DocumentScannerDelegate(
                    onError = {
                        println("delegate: onerror")
                        controller.dismissViewControllerAnimated(true) {}
                        throw DocumentScannerException(it.localizedDescription)
                    },
                    onCancel = {
                        println("delegate: oncancel")
                        controller.dismissViewControllerAnimated(true) {}
                    },
                    onResult = { result ->
                        println("delegate: onResult")
                        controller.dismissViewControllerAnimated(true) {}

                        val documents = (0..< result.pageCount.toInt()).map {
                            val image = result.imageOfPageAtIndex(it.toULong())
                            IosImage(image)
                        }

                        onResult(documents)
                    }
                ).also {
                    delegate = it // need to remember delegate else it is dereferenced
                }
            )
            localViewController.presentViewController(controller, animated = true)  {
//                val buttons = controller.view.findChildren { it is UIButton }
//                buttons.forEach {
//                    println("${it.accessibilityLabel} ${it}")
//                }
//                buttons.find { it.accessibilityLabel?.contains("Filtereinstellungen") == true }?.removeFromSuperview()
//
//                val imageViews = controller.view.findChildren { it is UIImageView }
//                imageViews.forEach { it.removeFromSuperview() }
            }
        }
    }
}

private fun UIView.findChildren(predicate: (UIView) -> Boolean): List<UIView> {
    val found = subviews.flatMap {
        val view:UIView? = it as? UIView
        if (view != null) {
            if (predicate(view)) {
                listOf(view)
            } else {
                view.findChildren(predicate)
            }
        } else {
            emptyList()
        }
    }
    return found
}

private fun UIView.findChild(predicate: (UIView) -> Boolean): UIView? {
    println("Traversing subviews of: $this")
    subviews.forEach {
        val view:UIView? = it as? UIView
        println("Subview:")
        when(view) {
            is UIButton -> println(view)
            is UILabel -> println((view as UILabel).text)
            else -> println(view)
        }

        if (view != null && predicate(view)) {
            return view
        } else {
            view?.findChild(predicate)?.let { return it }
        }
    }
    return null
}