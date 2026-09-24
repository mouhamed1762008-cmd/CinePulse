package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ClosedCaption
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MediaType
import com.example.ui.components.PosterGradients
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentLiveRed
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleGlow
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.GlassDark
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.SurfaceDarkHigh
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.CinePulseViewModel
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(
    mediaId: String,
    seasonNumber: Int?,
    episodeNumber: Int?,
    viewModel: CinePulseViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val media = viewModel.getMediaById(mediaId) ?: viewModel.getFeaturedHero()

    var isPlaying by remember { mutableStateOf(true) }
    var currentSeconds by remember { mutableIntStateOf(1450) } // ~24m 10s
    val totalSeconds = 7420 // ~2h 03m 40s
    var showControls by remember { mutableStateOf(true) }

    // Dialog state controllers
    var showSubtitlesModal by remember { mutableStateOf(false) }
    var selectedSubtitle by remember { mutableStateOf("English [CC]") }

    var showAudioModal by remember { mutableStateOf(false) }
    var selectedAudio by remember { mutableStateOf("English (Dolby Atmos 5.1)") }

    var showQualityModal by remember { mutableStateOf(false) }
    var selectedQuality by remember { mutableStateOf("4K Ultra HD (Auto)") }

    var showSpeedModal by remember { mutableStateOf(false) }
    var selectedSpeed by remember { mutableFloatStateOf(1.0f) }

    // Real-time playback ticking timer
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000)
            if (currentSeconds < totalSeconds) {
                currentSeconds += 1
                val progress = currentSeconds.toFloat() / totalSeconds.toFloat()
                viewModel.updateWatchProgress(media.id, progress)
            }
        }
    }

    // Auto-hide controls after 4 seconds of inactivity
    LaunchedEffect(showControls, isPlaying) {
        if (showControls && isPlaying) {
            delay(4500)
            showControls = false
        }
    }

    fun formatTime(sec: Int): String {
        val h = sec / 3600
        val m = (sec % 3600) / 60
        val s = sec % 60
        return if (h > 0) "%02d:%02d:%02d".format(h, m, s) else "%02d:%02d".format(m, s)
    }

    val posterGradientColors = PosterGradients.getOrElse(media.posterGradientIndex % PosterGradients.size) {
        listOf(Color(0xFF1E1B4B), Color(0xFF0F0728))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { showControls = !showControls }
            )
            .testTag("video_player_container")
    ) {
        // Video Stage / Visual Content
        if (media.backdropDrawableRes != null) {
            Image(
                painter = painterResource(id = media.backdropDrawableRes),
                contentDescription = "Video Stream",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(posterGradientColors))
            )
        }

        // Subtitle Overlay (if enabled)
        if (selectedSubtitle != "Off" && isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = if (showControls) 120.dp else 40.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.75f))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Temporal anomaly detected at coordinates Sector-7.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Yellow,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Next Episode overlay for series nearing end
        if (media.type == MediaType.SERIES && currentSeconds > (totalSeconds - 120)) {
            Surface(
                color = GlassDark,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentPurpleGlow),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = if (showControls) 120.dp else 50.dp)
                    .clickable {
                        currentSeconds = 0
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Next Episode in 15s",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next Episode",
                        tint = AccentCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Animated Player Controls Layer
        AnimatedVisibility(
            visible = showControls,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.65f))
            ) {
                // Top Controls Bar (Back, Title, Audio/Subtitles, Settings)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SurfaceDarkElevated.copy(alpha = 0.8f))
                                .testTag("player_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            val epText = if (episodeNumber != null) "S${seasonNumber ?: 1} : E$episodeNumber" else null
                            Text(
                                text = media.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            if (epText != null) {
                                Text(
                                    text = epText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AccentPurpleGlow
                                )
                            }
                        }
                    }

                    // Top quick actions: Subtitles, Audio, Quality, Speed
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(
                            onClick = { showSubtitlesModal = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SurfaceDarkElevated.copy(alpha = 0.7f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.ClosedCaption,
                                contentDescription = "Subtitles",
                                tint = if (selectedSubtitle != "Off") AccentCyan else TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { showQualityModal = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SurfaceDarkElevated.copy(alpha = 0.7f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.HighQuality,
                                contentDescription = "Quality",
                                tint = AccentPurpleGlow,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        IconButton(
                            onClick = { showSpeedModal = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SurfaceDarkElevated.copy(alpha = 0.7f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Speed",
                                tint = TextPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Center Play / Pause / 10s Rewind & Forward Controls
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rewind 10s
                    IconButton(
                        onClick = { currentSeconds = (currentSeconds - 10).coerceAtLeast(0) },
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(SurfaceDarkElevated.copy(alpha = 0.75f))
                            .testTag("player_rewind_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay10,
                            contentDescription = "Rewind 10s",
                            tint = TextPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Large Center Play / Pause
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(AccentPurple)
                            .clickable { isPlaying = !isPlaying }
                            .testTag("player_play_pause_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = TextPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Forward 10s
                    IconButton(
                        onClick = { currentSeconds = (currentSeconds + 10).coerceAtMost(totalSeconds) },
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(SurfaceDarkElevated.copy(alpha = 0.75f))
                            .testTag("player_forward_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "Forward 10s",
                            tint = TextPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                // Bottom Progress Bar & Time Scrubber
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // Slider scrubber
                    Slider(
                        value = currentSeconds.toFloat(),
                        onValueChange = { currentSeconds = it.toInt() },
                        valueRange = 0f..totalSeconds.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = AccentPurpleGlow,
                            activeTrackColor = AccentPurple,
                            inactiveTrackColor = SurfaceDarkHigh
                        ),
                        modifier = Modifier.testTag("player_progress_slider")
                    )

                    // Time labels & Bottom controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = formatTime(currentSeconds),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = " / ${formatTime(totalSeconds)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${selectedSpeed}x",
                                style = MaterialTheme.typography.labelSmall,
                                color = AccentCyan,
                                fontWeight = FontWeight.Bold
                            )

                            IconButton(
                                onClick = { showAudioModal = true },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Audio Language",
                                    tint = TextPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            IconButton(
                                onClick = { /* Toggle fullscreen */ },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = "Fullscreen",
                                    tint = TextPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal: Subtitles / CC Selector
    if (showSubtitlesModal) {
        val subtitleOptions = listOf("English [CC]", "Spanish", "French", "German", "Japanese", "Off")
        AlertDialog(
            onDismissRequest = { showSubtitlesModal = false },
            title = {
                Text("Subtitles & Captions", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    subtitleOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedSubtitle = option
                                    showSubtitlesModal = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSubtitle == option,
                                onClick = {
                                    selectedSubtitle = option
                                    showSubtitlesModal = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = AccentPurple)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option, color = TextPrimary)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showSubtitlesModal = false }) {
                    Text("Close", color = TextSecondary)
                }
            },
            containerColor = SurfaceDarkElevated
        )
    }

    // Modal: Audio Tracks
    if (showAudioModal) {
        val audioOptions = listOf("English (Dolby Atmos 5.1)", "English (Original)", "Spanish (5.1 Surround)", "Director Audio Commentary")
        AlertDialog(
            onDismissRequest = { showAudioModal = false },
            title = { Text("Audio Track", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    audioOptions.forEach { opt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAudio = opt
                                    showAudioModal = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedAudio == opt,
                                onClick = {
                                    selectedAudio = opt
                                    showAudioModal = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = AccentPurple)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = opt, color = TextPrimary)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAudioModal = false }) {
                    Text("Close", color = TextSecondary)
                }
            },
            containerColor = SurfaceDarkElevated
        )
    }

    // Modal: Video Quality
    if (showQualityModal) {
        val qualityOptions = listOf("4K Ultra HD (Auto)", "1080p Full HD", "720p HD", "Data Saver (480p)")
        AlertDialog(
            onDismissRequest = { showQualityModal = false },
            title = { Text("Stream Video Quality", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    qualityOptions.forEach { opt ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedQuality = opt
                                    showQualityModal = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedQuality == opt,
                                onClick = {
                                    selectedQuality = opt
                                    showQualityModal = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = AccentPurple)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = opt, color = TextPrimary)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showQualityModal = false }) {
                    Text("Close", color = TextSecondary)
                }
            },
            containerColor = SurfaceDarkElevated
        )
    }

    // Modal: Playback Speed
    if (showSpeedModal) {
        val speedOptions = listOf(0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
        AlertDialog(
            onDismissRequest = { showSpeedModal = false },
            title = { Text("Playback Speed", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    speedOptions.forEach { spd ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedSpeed = spd
                                    showSpeedModal = false
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSpeed == spd,
                                onClick = {
                                    selectedSpeed = spd
                                    showSpeedModal = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = AccentPurple)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "${spd}x", color = TextPrimary)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showSpeedModal = false }) {
                    Text("Close", color = TextSecondary)
                }
            },
            containerColor = SurfaceDarkElevated
        )
    }
}
