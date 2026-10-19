package gy.roach.kradioplayer

/**
 * Platform-specific audio playback declarations.
 * Actual implementations are provided per platform (wasmJs uses HTML5 audio, others are no-ops).
 */

expect fun setAudioStreamUrlActual(url: String)
expect fun playAudioActual()
expect fun pauseAudioActual()
expect fun setAudioVolumeActual(volume: Float)
