package gy.roach.kradioplayer

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---- Apple-inspired color tokens (shared with App.kt) ----

private val ColorElevatedSurface = Color(0xFF1C1C1E)
private val ColorTextPrimary = Color(0xFFFFFFFF)
private val ColorTextSecondary = Color(0xFF8E8E93)
private val ColorAccentBlue = Color(0xFF0A84FF)
private val ColorAccentPurple = Color(0xFF5E5CE6)

/**
 * Gradient pairs for station artwork — Apple Music style.
 */
private val gradientPairs = listOf(
    Pair(Color(0xFF0A84FF), Color(0xFF5E5CE6)), // Blue → Purple
    Pair(Color(0xFF5E5CE6), Color(0xFFBF5AF2)), // Purple → Pink
    Pair(Color(0xFF30D158), Color(0xFF63E6BE)), // Green → Mint
    Pair(Color(0xFFFF9500), Color(0xFFFF6482)), // Orange → Pink
    Pair(Color(0xFFFF6482), Color(0xFFFF453A)), // Pink → Red
    Pair(Color(0xFF64D2FF), Color(0xFF0A84FF)), // Cyan → Blue
    Pair(Color(0xFFBF5AF2), Color(0xFF30D158)), // Pink → Green
    Pair(Color(0xFFFFD60A), Color(0xFFFF9500)), // Yellow → Orange
)

/**
 * Station list component displaying radio stations in a grid.
 * Apple-inspired: gradient artwork cards, no borders, animated equalizer for playing station.
 */
@Composable
fun StationList(
    stations: List<RadioStation>,
    isLoading: Boolean,
    error: String?,
    currentStationIndex: Int?,
    onStationSelected: (RadioStation) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = ColorAccentBlue,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading stations...",
                        fontSize = 16.sp,
                        color = ColorTextSecondary
                    )
                }
            }
        } else if (error != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = error,
                    color = Color(0xFFFF453A),
                    fontSize = 16.sp
                )
            }
        } else if (stations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No stations available",
                    fontSize = 16.sp,
                    color = ColorTextSecondary
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stations, key = { it.index }) { station ->
                    StationCard(
                        station = station,
                        isCurrent = currentStationIndex == station.index,
                        onClick = { onStationSelected(station) }
                    )
                }
            }
        }
    }
}

/**
 * Apple Music-style station card: gradient artwork, clean text, animated equalizer when playing.
 */
@Composable
private fun StationCard(
    station: RadioStation,
    isCurrent: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradient = gradientPairs[station.index % gradientPairs.size]
    val brush = Brush.linearGradient(
        listOf(gradient.first, gradient.second)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(ColorElevatedSurface)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        // Gradient artwork
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(brush),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = station.label,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Station name
        Text(
            text = station.label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorTextPrimary,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Genre pills
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            station.type.take(2).forEach { genre ->
                Text(
                    text = genre,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = ColorTextSecondary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF2C2C2E))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Animated equalizer bars when playing
        if (isCurrent) {
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedEqualizerBars()
        }
    }
}

/**
 * Animated equalizer bars — 3 bars with staggered animation, Apple Music style.
 */
@Composable
private fun AnimatedEqualizerBars() {
    val transition = rememberInfiniteTransition(label = "equalizer")

    val height1 by transition.animateFloat(
        initialValue = 4f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar1"
    )
    val height2 by transition.animateFloat(
        initialValue = 8f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar2"
    )
    val height3 by transition.animateFloat(
        initialValue = 4f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(350, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bar3"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.height(16.dp)
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(height1.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(ColorAccentBlue)
        )
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(height2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(ColorAccentBlue)
        )
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(height3.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(ColorAccentBlue)
        )
    }
}
