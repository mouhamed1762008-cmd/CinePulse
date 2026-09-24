package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.MediaItem
import com.example.ui.components.CinePulseTopBar
import com.example.ui.components.MediaLandscapeCard
import com.example.ui.components.MediaPosterCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleGlow
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.RatingGold
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.SurfaceDarkHigh
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.CinePulseViewModel

@Composable
fun HomeScreen(
    viewModel: CinePulseViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToPlayer: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onSeeAllMovies: () -> Unit,
    onSeeAllSeries: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val continueWatching by viewModel.continueWatching.collectAsState()
    val watchlist by viewModel.watchlist.collectAsState()

    val featuredHero = viewModel.getMediaById("movie-1") ?: viewModel.getFeaturedHero()
    val trendingList = viewModel.getMediaById("movie-1")?.let { viewModel.getSimilarMedia(it) } ?: emptyList()
    val popularMovies = viewModel.getPopularMovies()
    val popularSeries = viewModel.getPopularSeries()
    val recommended = viewModel.getRecommended()
    val recentlyAdded = viewModel.getRecentlyAdded()

    val isHeroInWatchlist = watchlist.any { it.id == featuredHero.id }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Sticky / Top Bar
        CinePulseTopBar(
            userName = profile.userName,
            onSearchClick = onNavigateToSearch,
            onProfileClick = onNavigateToProfile
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Large Featured Hero Banner
            item {
                FeaturedHeroBanner(
                    media = featuredHero,
                    isInWatchlist = isHeroInWatchlist,
                    onWatchNow = { onNavigateToPlayer(featuredHero.id) },
                    onMoreInfo = { onNavigateToDetails(featuredHero.id) },
                    onToggleWatchlist = {
                        viewModel.toggleWatchlist(featuredHero.id, isHeroInWatchlist)
                    }
                )
            }

            // Continue Watching Section with horizontal carousel & progress bars
            if (continueWatching.isNotEmpty()) {
                item {
                    SectionHeader(
                        title = "Continue Watching for ${profile.userName.split(" ").firstOrNull() ?: "You"}"
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(continueWatching, key = { "continue_${it.id}" }) { item ->
                            MediaLandscapeCard(
                                media = item,
                                onClick = { onNavigateToPlayer(item.id) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Trending Now
            item {
                SectionHeader(
                    title = "Trending Now",
                    actionLabel = "See All",
                    onActionClick = onSeeAllMovies
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trendingList, key = { "trending_${it.id}" }) { item ->
                        MediaPosterCard(
                            media = item,
                            onClick = { onNavigateToDetails(item.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Popular Movies
            item {
                SectionHeader(
                    title = "Popular Movies",
                    actionLabel = "Explore",
                    onActionClick = onSeeAllMovies
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(popularMovies, key = { "pop_mov_${it.id}" }) { item ->
                        MediaPosterCard(
                            media = item,
                            onClick = { onNavigateToDetails(item.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Popular Series
            item {
                SectionHeader(
                    title = "Popular Series",
                    actionLabel = "Explore",
                    onActionClick = onSeeAllSeries
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(popularSeries, key = { "pop_ser_${it.id}" }) { item ->
                        MediaPosterCard(
                            media = item,
                            onClick = { onNavigateToDetails(item.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Recommended For You
            item {
                SectionHeader(
                    title = "Recommended For You",
                    actionLabel = "More",
                    onActionClick = onSeeAllMovies
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recommended, key = { "rec_${it.id}" }) { item ->
                        MediaPosterCard(
                            media = item,
                            onClick = { onNavigateToDetails(item.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Recently Added
            item {
                SectionHeader(
                    title = "Recently Added Releases"
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recentlyAdded, key = { "recent_${it.id}" }) { item ->
                        MediaPosterCard(
                            media = item,
                            onClick = { onNavigateToDetails(item.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun FeaturedHeroBanner(
    media: MediaItem,
    isInWatchlist: Boolean,
    onWatchNow: () -> Unit,
    onMoreInfo: () -> Unit,
    onToggleWatchlist: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(410.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(SurfaceDarkElevated)
    ) {
        // Hero Image
        Image(
            painter = painterResource(id = R.drawable.img_hero_cyberpunk_1790253854978),
            contentDescription = media.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient fade overlay into background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            BackgroundDark.copy(alpha = 0.45f),
                            BackgroundDark.copy(alpha = 0.88f),
                            BackgroundDark
                        )
                    )
                )
        )

        // Content details inside hero
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            // Badges row: Quality, Age, Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = AccentPurple.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "FEATURED PREMIERE",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = SurfaceDarkHigh.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = media.videoQuality,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = AccentCyan,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }

                Surface(
                    color = SurfaceDarkHigh.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = RatingGold,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "%.1f".format(media.rating),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = media.title,
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Sub-info: Year, Age rating, Runtime, Genres
            Text(
                text = "${media.year} • ${media.ageRating} • ${media.durationOrSeasons} • ${media.genres.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )

            // Short Description
            Text(
                text = media.description,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary.copy(alpha = 0.85f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
            )

            // Primary Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "Watch Now" Button
                Button(
                    onClick = onWatchNow,
                    modifier = Modifier
                        .weight(1.2f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .testTag("hero_watch_now_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentPurple)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Watch Now",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                // "More Info" Button
                OutlinedButton(
                    onClick = onMoreInfo,
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .testTag("hero_more_info_button"),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(SurfaceBorder, AccentPurple.copy(alpha = 0.4f)))
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = SurfaceDarkElevated.copy(alpha = 0.7f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = TextPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Watchlist Bookmark Button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceDarkElevated.copy(alpha = 0.8f))
                        .border(1.dp, SurfaceBorder, RoundedCornerShape(12.dp))
                        .clickable(onClick = onToggleWatchlist)
                        .testTag("hero_watchlist_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isInWatchlist) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = if (isInWatchlist) "Added" else "Add to List",
                        tint = if (isInWatchlist) AccentCyan else TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
