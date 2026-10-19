package gy.roach.kradioplayer

/**
 * WASM/JS actual implementation of audio playback using HTML5 <audio> element.
 * A singleton AudioPlayerEngine manages the browser audio element.
 */

private val audioEngine by lazy { AudioPlayerEngine().apply { init() } }

actual fun setAudioStreamUrlActual(url: String) {
    audioEngine.setStreamUrlDirect(url)
}

actual fun playAudioActual() {
    audioEngine.play()
}

actual fun pauseAudioActual() {
    audioEngine.pause()
}

actual fun setAudioVolumeActual(volume: Float) {
    audioEngine.setVolume(volume)
}
