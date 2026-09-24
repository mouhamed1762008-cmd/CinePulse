package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MediaItem
import com.example.ui.components.MediaPosterCard
import com.example.ui.components.SectionHeader
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleGlow
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.SurfaceBorder
import com.example.ui.theme.SurfaceDarkElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.CinePulseViewModel

@Composable
fun MoviesScreen(
    viewModel: CinePulseViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToPlayer: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedGenre by viewModel.movieGenreFilter.collectAsState()
    val selectedSort by viewModel.movieSortFilter.collectAsState()
    val allMovies = viewModel.getAllMovies()
    val featuredMovie = viewModel.getFeaturedHero()

    val genres = listOf("All", "Sci-Fi", "Action", "Mystery", "Thriller", "Drama", "Crime")
    val sortOptions = listOf("Popular", "Top Rated", "Newest")

    val filteredMovies = allMovies.filter { movie ->
        selectedGenre == "All" || movie.genres.any { g -> g.equals(selectedGenre, ignoreCase = true) }
    }.let { list ->
        when (selectedSort) {
            "Top Rated" -> list.sortedByDescending { it.rating }
            "Newest" -> list.sortedByDescending { it.year }
            else -> list.filter { it.isPopular }.ifEmpty { list }
        }
    }

    val trendingMovies = allMovies.filter { it.isTrending }
    val newReleases = allMovies.filter { it.isNewRelease }
    val topRated = allMovies.sortedByDescending { it.rating }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 26.sp),
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "Blockbusters, award winners & indie films in 4K HDR",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }

        // Genre Filters
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            items(genres) { genre ->
                val isSelected = selectedGenre == genre
                FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setMovieGenreFilter(genre) },
                    label = {
                        Text(
                            text = genre,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentPurple,
                        selectedLabelColor = TextPrimary,
                        containerColor = SurfaceDarkElevated,
                        labelColor = TextSecondary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) AccentPurpleGlow else SurfaceBorder,
                        selectedBorderColor = AccentPurple,
                        enabled = true,
                        selected = isSelected
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Featured Movie Banner
            item {
                FeaturedHeroBanner(
                    media = featuredMovie,
                    isInWatchlist = false,
                    onWatchNow = { onNavigateToPlayer(featuredMovie.id) },
                    onMoreInfo = { onNavigateToDetails(featuredMovie.id) },
                    onToggleWatchlist = { viewModel.toggleWatchlist(featuredMovie.id, false) }
                )
            }

            // Trending Movies
            item {
                SectionHeader(title = "Trending Movies")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trendingMovies, key = { "trend_m_${it.id}" }) { movie ->
                        MediaPosterCard(
                            media = movie,
                            onClick = { onNavigateToDetails(movie.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // New Releases
            item {
                SectionHeader(title = "New Releases This Month")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(newReleases, key = { "new_m_${it.id}" }) { movie ->
                        MediaPosterCard(
                            media = movie,
                            onClick = { onNavigateToDetails(movie.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Top Rated Movies
            item {
                SectionHeader(title = "Top Rated of All Time")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(topRated, key = { "top_m_${it.id}" }) { movie ->
                        MediaPosterCard(
                            media = movie,
                            onClick = { onNavigateToDetails(movie.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Filtered Movies Grid Section with Sorting Controls
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Browse Movies (${filteredMovies.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    // Sort pills
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        sortOptions.forEach { opt ->
                            val isSel = selectedSort == opt
                            Surface(
                                color = if (isSel) AccentCyan.copy(alpha = 0.2f) else SurfaceDarkElevated,
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSel) AccentCyan else SurfaceBorder
                                ),
                                modifier = Modifier.clickable { viewModel.setMovieSortFilter(opt) }
                            ) {
                                Text(
                                    text = opt,
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = if (isSel) AccentCyan else TextSecondary,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Grid items rendered in rows
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    filteredMovies.chunked(2).forEach { pair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            pair.forEach { movie ->
                                MediaPosterCard(
                                    media = movie,
                                    onClick = { onNavigateToDetails(movie.id) },
                                    cardWidth = 165,
                                    cardHeight = 235,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (pair.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}
