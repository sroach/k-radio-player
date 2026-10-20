@file:OptIn(ExperimentalWasmJsInterop::class)

package gy.roach.kradioplayer

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny

/**
 * Wasm/JS implementation: use browser's `matchMedia` to detect dark mode preference.
 * Falls back to true (dark mode) if the API is unavailable.
 */
actual fun isDarkMode(): Boolean {
    return try {
        val matches = getDarkModePreference()
        matches
    } catch (_: Exception) {
        true
    }
}

private fun getDarkModePreference(): Boolean = js("globalThis.matchMedia('(prefers-color-scheme: dark)').matches")
