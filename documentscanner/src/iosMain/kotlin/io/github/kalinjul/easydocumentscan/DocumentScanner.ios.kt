package io.github.kalinjul.easydocumentscan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.interop.LocalUIViewController
import platform.UIKit.UIButton
import platform.UIKit.UIControlEventTouchUpInside
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
            localViewController.presentViewController(controller, animated = true) {


                if (options.ios.captureMode == DocumentCaptureMode.MANUAL) {
                    controller.setManualMode()
                }


            }
        }

        private fun VNDocumentCameraViewController.setManualMode() {
//                val buttons = controller.view.findChildren { it is UIButton }
//                buttons.forEach {
//                    println("${it.accessibilityLabel} ${it}")
//                    it.findChildren { true }.forEach {
//                        if (it is UILabel) {
//                            println("  ${it.text} ${it.accessibilityLabel} $it")
//                            if (it.text == "Auto") {
//                                println("Found Auto button")
//                            }
//                        }
//                    }
//                }

            val autoButton = view.findChild {
                it.hasChild {
                    val text = (it as? UILabel)?.text
                    text == "Auto" || text == "Automatisch"
                } != null
            }
            (autoButton as? UIButton)?.sendActionsForControlEvents(UIControlEventTouchUpInside)

//            var view = autoButton
//            println("View hierarchy:")
//            while(view != null) {
//                println(view)
//                view = view.superview
//            }

//            val navigationbar = navigationController?.navigationBar
//            println("Navigationbar: $navigationbar")
//                buttons.find { it.accessibilityLabel?.contains("Filtereinstellungen") == true }?.removeFromSuperview()
//                val imageViews = controller.view.findChildren { it is UIImageView }
//                imageViews.forEach { it.removeFromSuperview() }
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
    subviews.forEach {
        val view:UIView? = it as? UIView
        when(view) {
            is UIButton -> println(view)
            is UILabel -> println(view.text)
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

private fun UIView.hasChild(predicate: (UIView) -> Boolean): UIView? {
    subviews.forEach {
        val view:UIView? = it as? UIView
        if (view != null && predicate(view)) {
            return view
        }
    }
    return null
}