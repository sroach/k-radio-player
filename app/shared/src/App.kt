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

private val ColorAccentBlue = Color(0xFF0A84FF)
private val ColorAccentPurple = Color(0xFF5E5CE6)

// Dark mode colors
private val ColorDarkBackground = Color(0xFF000000)
private val ColorDarkSurface = Color(0xFF1C1C1E)
private val ColorDarkSurfaceBorder = Color(0xFF2C2C2E)
private val ColorDarkTextPrimary = Color(0xFFFFFFFF)
private val ColorDarkTextSecondary = Color(0xFF8E8E93)
private val ColorDarkTextTertiary = Color(0xFF636366)

// Light mode colors
private val ColorLightBackground = Color(0xFFFFFFFF)
private val ColorLightSurface = Color(0xFFF5F5F7)
private val ColorLightSurfaceBorder = Color(0xFFE5E5EA)
private val ColorLightTextPrimary = Color(0xFF000000)
private val ColorLightTextSecondary = Color(0xFF8E8E93)
private val ColorLightTextTertiary = Color(0xFFAEAEB2)

@Composable
private fun AppleColorScheme(isDark: Boolean): ColorScheme {
    return if (isDark) {
        darkColorScheme(
            primary = ColorAccentBlue,
            secondary = ColorAccentPurple,
            background = ColorDarkBackground,
            surface = ColorDarkSurface,
            onBackground = ColorDarkTextPrimary,
            onSurface = ColorDarkTextPrimary,
            onSurfaceVariant = ColorDarkTextSecondary,
            outline = ColorDarkSurfaceBorder
        )
    } else {
        lightColorScheme(
            primary = ColorAccentBlue,
            secondary = ColorAccentPurple,
            background = ColorLightBackground,
            surface = ColorLightSurface,
            onBackground = ColorLightTextPrimary,
            onSurface = ColorLightTextPrimary,
            onSurfaceVariant = ColorLightTextSecondary,
            outline = ColorLightSurfaceBorder
        )
    }
}

/**
 * Main App composable for the Caribbean Radio Player.
 * Apple-inspired theme with automatic light/dark mode support based on system preference.
 */
@Composable
@Preview
@DevelopmentEntryPoint
fun App() {
    val viewModel = remember { RadioViewModel() }
    val state = viewModel.uiState
    val isDark = isDarkMode()

    MaterialTheme(
        colorScheme = AppleColorScheme(isDark)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
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
                    isPlaying = state.isPlaying,
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
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitle
        Text(
            text = "Live streaming from the islands",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
