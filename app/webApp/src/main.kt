package gy.roach.kradioplayer

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

/**
 * Caribbean Radio Player - Web entry point.
 * A Kotlin Multiplatform radio streaming app modeled after the Rust-based radio-player.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App()
    }
}
