package gy.roach.kradioplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---- Apple-inspired color tokens (shared with App.kt) ----

private val ColorElevatedSurface = Color(0xFF1C1C1E)
private val ColorSurfaceBorder = Color(0xFF2C2C2E)
private val ColorTextPrimary = Color(0xFFFFFFFF)
private val ColorTextSecondary = Color(0xFF8E8E93)
private val ColorAccentBlue = Color(0xFF0A84FF)

/**
 * Audio player component — Apple Music-style floating now-playing bar.
 * Minimal controls: circular play/pause icon, slim volume slider, no redundant labels.
 */
@Composable
fun AudioPlayer(
    currentStation: RadioStation?,
    isPlaying: Boolean,
    volume: Float,
    onPlayPause: () -> Unit,
    onVolumeChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    if (currentStation != null) {
        // Now playing bar
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(ColorElevatedSurface)
                .padding(16.dp)
        ) {
            // Station info + play/pause row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Station info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentStation.label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorTextPrimary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = currentStation.type.joinToString(" • "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = ColorTextSecondary,
                        maxLines = 1
                    )
                }

                // Circular play/pause button
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onPlayPause),
                    shape = CircleShape,
                    color = ColorAccentBlue
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isPlaying) "⏸" else "▶",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Slim volume slider
            SlimSlider(
                value = volume,
                onValueChange = onVolumeChanged,
                modifier = Modifier.fillMaxWidth()
            )
        }
    } else {
        // Empty state
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(ColorElevatedSurface)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Select a station to start listening",
                fontSize = 14.sp,
                color = ColorTextSecondary
            )
        }
    }
}

/**
 * Slim custom slider — 2dp height, rounded thumb, Apple-style minimal track.
 * Uses an invisible Material Slider for interaction, overlaid with custom visuals.
 */
@Composable
private fun SlimSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        // Track background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(1.dp))
                .background(ColorSurfaceBorder)
        )

        // Track fill
        Box(
            modifier = Modifier
                .fillMaxWidth(value)
                .height(2.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(1.dp))
                .background(ColorAccentBlue)
        )

        // Thumb
        Box(
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterStart)
                .offset(x = (value * 100 - 8).dp)
                .clip(CircleShape)
                .background(Color.White)
        )

        // Invisible slider for interaction
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
    }
}
