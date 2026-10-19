package gy.roach.kradioplayer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.reload.DevelopmentEntryPoint

// ---- Apple-inspired color tokens ----

private val ColorPureBlack = Color(0xFF000000)
private val ColorElevatedSurface = Color(0xFF1C1C1E)
private val ColorSurfaceBorder = Color(0xFF2C2C2E)
private val ColorAccentBlue = Color(0xFF0A84FF)
private val ColorAccentPurple = Color(0xFF5E5CE6)
private val ColorTextPrimary = Color(0xFFFFFFFF)
private val ColorTextSecondary = Color(0xFF8E8E93)
private val ColorTextTertiary = Color(0xFF636366)

/**
 * Main App composable for the Caribbean Radio Player.
 * Apple-inspired dark theme: pure black background, refined accent colors, minimal aesthetics.
 */
@Composable
@Preview
@DevelopmentEntryPoint
fun App() {
    val viewModel = remember { RadioViewModel() }
    val state = viewModel.uiState

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = ColorAccentBlue,
            secondary = ColorAccentPurple,
            background = ColorPureBlack,
            surface = ColorElevatedSurface,
            onBackground = ColorTextPrimary,
            onSurface = ColorTextPrimary,
            onSurfaceVariant = ColorTextSecondary,
            outline = ColorSurfaceBorder
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = ColorPureBlack
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Header
                CaribbeanRadioHeader()

                Spacer(modifier = Modifier.height(32.dp))

                // Station list (scrollable)
                StationList(
                    stations = state.stations,
                    isLoading = state.isLoading,
                    error = state.error,
                    currentStationIndex = state.currentStation?.index,
                    onStationSelected = { viewModel.selectStation(it) },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Audio player (floating bar at bottom)
                AudioPlayer(
                    currentStation = state.currentStation,
                    isPlaying = state.isPlaying,
                    volume = state.volume,
                    onPlayPause = { viewModel.togglePlayPause() },
                    onVolumeChanged = { viewModel.setVolume(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Minimal header — no emoji, clean typography, generous whitespace.
 */
@Composable
private fun CaribbeanRadioHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Title
        Text(
            text = "Caribbean Radio",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            color = ColorTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitle
        Text(
            text = "Live streaming from the islands",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = ColorTextSecondary
        )
    }
}
