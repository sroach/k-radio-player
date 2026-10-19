@file:OptIn(ExperimentalWasmJsInterop::class)

package gy.roach.kradioplayer

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny

/**
 * Browser audio playback engine using HTML5 `<audio>` element.
 * Created via Kotlin/Wasm JavaScript interop.
 */
class AudioPlayerEngine {

    companion object {
        private const val AUDIO_ELEMENT_ID = "radio-audio"
    }

    private var audioElement: JsAny? = null
    private var isInitialized = false

    /**
     * Initialize the hidden audio element in the DOM.
     */
    fun init() {
        if (isInitialized) return

        val existing = getElementById(AUDIO_ELEMENT_ID)

        audioElement = if (existing != null) {
            existing
        } else {
            val audio = createAudioElement()
            setAudioId(audio, AUDIO_ELEMENT_ID)
            setAudioCrossOrigin(audio, "anonymous")
            hideElement(audio)
            appendToBody(audio)
            audio
        }
        isInitialized = true
    }

    /**
     * Set the stream URL directly (bypassing proxy) for standalone web builds.
     */
    fun setStreamUrlDirect(url: String) {
        audioElement?.let { audio ->
            setAudioCrossOrigin(audio, "anonymous")
            setAudioSrc(audio, url)
            loadAudio(audio)
        }
    }

    /**
     * Start playing the current stream.
     */
    fun play(): Boolean {
        return try {
            audioElement?.let { audio ->
                playAudio(audio)
                true
            } ?: false
        } catch (e: Exception) {
            println("Audio play error: ${e.message}")
            false
        }
    }

    /**
     * Pause the current stream.
     */
    fun pause() {
        try {
            audioElement?.let { audio ->
                pauseAudio(audio)
            }
        } catch (e: Exception) {
            println("Audio pause error: ${e.message}")
        }
    }

    /**
     * Set the volume (0.0 to 1.0).
     */
    fun setVolume(volume: Float) {
        audioElement?.let { audio ->
            setAudioVolume(audio, volume.coerceIn(0f, 1f))
        }
    }

    /**
     * Stop playback and reset.
     */
    fun stop() {
        pause()
        audioElement?.let { audio ->
            setAudioSrc(audio, "")
            loadAudio(audio)
        }
    }

    /**
     * Clean up the audio element from the DOM.
     */
    fun dispose() {
        try {
            stop()
            val existing = getElementById(AUDIO_ELEMENT_ID)
            if (existing != null) {
                removeFromBody(existing)
            }
            audioElement = null
            isInitialized = false
        } catch (e: Exception) {
            println("Audio dispose error: ${e.message}")
        }
    }
}

// ---- JavaScript interop functions ----

private fun getElementById(id: String): JsAny? = js("document.getElementById(id)")

private fun createAudioElement(): JsAny = js("document.createElement('audio')")

private fun setAudioId(audio: JsAny, id: String) {
    js("audio.id = id")
}

private fun setAudioCrossOrigin(audio: JsAny, value: String) {
    js("audio.crossOrigin = value")
}

private fun setAudioSrc(audio: JsAny, url: String) {
    js("audio.src = url")
}

private fun setAudioVolume(audio: JsAny, volume: Float) {
    js("audio.volume = volume")
}

private fun hideElement(element: JsAny) {
    js("element.style.display = 'none'")
}

private fun appendToBody(element: JsAny) {
    js("document.body.appendChild(element)")
}

private fun playAudio(audio: JsAny) {
    js("audio.play()")
}

private fun pauseAudio(audio: JsAny) {
    js("audio.pause()")
}

private fun loadAudio(audio: JsAny) {
    js("audio.load()")
}

private fun removeFromBody(element: JsAny) {
    js("document.body.removeChild(element)")
}
