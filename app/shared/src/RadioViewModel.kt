package gy.roach.kradioplayer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * UI state for the radio player screen.
 */
data class RadioUiState(
    val stations: List<RadioStation> = emptyList(),
    val currentStation: RadioStation? = null,
    val isPlaying: Boolean = false,
    val volume: Float = 0.8f,
    val isLoading: Boolean = true,
    val error: String? = null
)

/**
 * ViewModel for the Caribbean Radio Player.
 * Manages station list, playback state, and volume.
 */
class RadioViewModel : ViewModel() {

    var uiState by mutableStateOf(RadioUiState())
        private set

    init {
        loadStations()
    }

    /**
     * Load the station list from the core module.
     */
    fun loadStations() {
        uiState = uiState.copy(isLoading = true, error = null)
        try {
            val stations = getStations()
            uiState = uiState.copy(stations = stations, isLoading = false)
        } catch (e: Exception) {
            uiState = uiState.copy(
                isLoading = false,
                error = "Failed to load stations: ${e.message}"
            )
        }
    }

    /**
     * Select a station and start playing.
     */
    fun selectStation(station: RadioStation) {
        uiState = uiState.copy(
            currentStation = station,
            isPlaying = true
        )
        // Play the selected station's stream
        playStation(station)
    }

    /**
     * Toggle play/pause.
     */
    fun togglePlayPause() {
        val newPlaying = !uiState.isPlaying
        uiState = uiState.copy(isPlaying = newPlaying)

        if (newPlaying) {
            playCurrent()
        } else {
            pausePlayback()
        }
    }

    /**
     * Update the volume level (0.0f to 1.0f).
     */
    fun setVolume(volume: Float) {
        val clampedVolume = volume.coerceIn(0f, 1f)
        uiState = uiState.copy(volume = clampedVolume)
        setAudioVolume(clampedVolume)
    }

    /**
     * Check if a station is the currently playing one.
     */
    fun isCurrentStation(station: RadioStation): Boolean {
        return uiState.currentStation?.index == station.index
    }

    // ---- Audio playback methods (platform-specific delegation) ----

    private fun playStation(station: RadioStation) {
        setAudioStreamUrl(station.url)
        playAudio()
        setAudioVolume(uiState.volume)
    }

    private fun playCurrent() {
        uiState.currentStation?.let { station ->
            // If the stream URL hasn't been set yet, set it
            setAudioStreamUrl(station.url)
        }
        playAudio()
    }

    private fun pausePlayback() {
        pauseAudio()
    }

    // ---- Platform-specific audio methods (expect/actual) ----

    private fun setAudioStreamUrl(url: String) {
        setAudioStreamUrlActual(url)
    }

    private fun playAudio() {
        playAudioActual()
    }

    private fun pauseAudio() {
        pauseAudioActual()
    }

    private fun setAudioVolume(volume: Float) {
        setAudioVolumeActual(volume)
    }
}
