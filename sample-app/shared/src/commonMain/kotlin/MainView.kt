package io.github.kalinjul.easydocumentscan.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kalinjul.easydocumentscan.rememberDocumentScanner
import kotlinx.coroutines.launch

@Composable
fun MainView() {
    Box() {
        val snackbarHostState = remember() { SnackbarHostState() }

        Column() {
            Text("Start Document Scanner below")
            val scope = rememberCoroutineScope()
            val scanner = rememberDocumentScanner(onScanned = {
                println("Scan result received: $it")
                scope.launch {
                    snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                }
            })
            Button(onClick = {
                scanner.scan()
            }) {
                Text("Scan")
            }
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            hostState = snackbarHostState
        )
    }
}