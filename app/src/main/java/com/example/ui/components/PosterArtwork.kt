package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MediaItem
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleGlow
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.RatingGold
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

val PosterGradients = listOf(
    listOf(Color(0xFF2E0854), Color(0xFF130924), Color(0xFF090D1A)), // 0: Chrono Shift Cyber Violet
    listOf(Color(0xFF0A2540), Color(0xFF0B192C), Color(0xFF050B14)), // 1: Shadows of Abyss
    listOf(Color(0xFF1E1B4B), Color(0xFF31103F), Color(0xFF0F0728)), // 2: Nebula Odyssey
    listOf(Color(0xFF3F0B1B), Color(0xFF1F0D16), Color(0xFF0A0A0F)), // 3: Midnight Syndicate
    listOf(Color(0xFF063A36), Color(0xFF0A1F1D), Color(0xFF080D0C)), // 4: Apex Protocol
    listOf(Color(0xFF3B1E54), Color(0xFF241434), Color(0xFF0C0714)), // 5: Whispers in Starlight
    listOf(Color(0xFF1E293B), Color(0xFF0F172A), Color(0xFF05070B)), // 6: Aetheria Noir
    listOf(Color(0xFF142C1E), Color(0xFF0D1D14), Color(0xFF070E0A)), // 7: Silicon Underworld
    listOf(Color(0xFF451A03), Color(0xFF271005), Color(0xFF0D0602)), // 8: The Last Dominion
    listOf(Color(0xFF1E3A8A), Color(0xFF172554), Color(0xFF080F1E)), // 9: Orbital Station 9
    listOf(Color(0xFF064E3B), Color(0xFF022C22), Color(0xFF01140F))  // 10: Chronicles of Deep
)

@Composable
fun MediaPosterCard(
    media: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Int = 135,
    cardHeight: Int = 195,
    showProgress: Boolean = false
) {
    val gradientColors = PosterGradients.getOrElse(media.posterGradientIndex % PosterGradients.size) {
        listOf(Color(0xFF1E1B4B), Color(0xFF0F0728))
    }

    Column(
        modifier = modifier
            .width(cardWidth.dp)
            .clickable(onClick = onClick)
            .testTag("media_poster_${media.id}")
    ) {
        Box(
            modifier = Modifier
                .width(cardWidth.dp)
                .height(cardHeight.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.verticalGradient(gradientColors))
        ) {
            // Ambient design elements inside poster
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentPurpleGlow.copy(alpha = 0.25f),
                                Color.Transparent
                            ),
                            radius = 280f
                        )
                    )
            )

            // Age rating / Quality badge top left
            Surface(
                color = BackgroundDark.copy(alpha = 0.75f),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = media.ageRating,
                    style = MaterialTheme.typography.labelSmall,
                    color = AccentCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // Rating badge top right
            Surface(
                color = BackgroundDark.copy(alpha = 0.75f),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = RatingGold,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "%.1f".format(media.rating),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Bottom title & genre preview on poster
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, BackgroundDark.copy(alpha = 0.95f))
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = media.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = media.genres.take(2).joinToString(" • "),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Progress bar if requested / partially watched
            if (showProgress && media.watchProgress != null) {
                LinearProgressIndicator(
                    progress = { media.watchProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .align(Alignment.BottomCenter),
                    color = AccentPurple,
                    trackColor = BackgroundDark.copy(alpha = 0.6f)
                )
            }
        }

        // Subtitle information under card
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${media.year} • ${media.durationOrSeasons}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun MediaLandscapeCard(
    media: MediaItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    cardWidth: Int = 230,
    cardHeight: Int = 130
) {
    val gradientColors = PosterGradients.getOrElse(media.posterGradientIndex % PosterGradients.size) {
        listOf(Color(0xFF1E1B4B), Color(0xFF0F0728))
    }

    Column(
        modifier = modifier
            .width(cardWidth.dp)
            .clickable(onClick = onClick)
            .testTag("media_landscape_${media.id}")
    ) {
        Box(
            modifier = Modifier
                .width(cardWidth.dp)
                .height(cardHeight.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Brush.linearGradient(gradientColors))
        ) {
            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                BackgroundDark.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // Play icon in center
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SurfaceDarkElevated.copy(alpha = 0.8f))
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Bottom title and remaining time
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Text(
                    text = media.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                if (media.remainingTimeText != null) {
                    Text(
                        text = media.remainingTimeText,
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentPurpleGlow
                    )
                }
            }

            // Progress bar
            val progress = media.watchProgress ?: 0.5f
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.5.dp)
                    .align(Alignment.BottomCenter),
                color = AccentPurple,
                trackColor = SurfaceDarkElevated.copy(alpha = 0.6f)
            )
        }
    }
}
