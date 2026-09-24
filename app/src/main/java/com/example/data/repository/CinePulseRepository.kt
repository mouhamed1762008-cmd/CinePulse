package com.example.data.repository

import com.example.data.local.CinePulseDao
import com.example.data.local.FavoriteChannelEntity
import com.example.data.local.RecentSearchEntity
import com.example.data.local.UserProfileEntity
import com.example.data.local.WatchHistoryEntity
import com.example.data.local.WatchlistEntity
import com.example.data.model.LiveChannel
import com.example.data.model.MediaItem
import com.example.data.model.MediaType
import com.example.data.model.MockCatalog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CinePulseRepository(
    private val dao: CinePulseDao
) {
    // User profile
    val userProfile: Flow<UserProfileEntity> = dao.getUserProfile().map {
        it ?: UserProfileEntity(userName = "Alex Stone", isOnboarded = false)
    }

    suspend fun saveProfileName(name: String) {
        val trimmed = name.trim().ifEmpty { "Guest" }
        dao.insertOrUpdateProfile(
            UserProfileEntity(
                id = 1,
                userName = trimmed,
                isOnboarded = true
            )
        )
    }

    suspend fun resetOnboarding() {
        dao.insertOrUpdateProfile(
            UserProfileEntity(
                id = 1,
                userName = "Guest",
                isOnboarded = false
            )
        )
    }

    suspend fun updateSettings(
        notifications: Boolean,
        preferredQuality: String,
        preferredLanguage: String,
        avatarIndex: Int
    ) {
        dao.insertOrUpdateProfile(
            UserProfileEntity(
                id = 1,
                notificationsEnabled = notifications,
                preferredQuality = preferredQuality,
                audioLanguage = preferredLanguage,
                avatarIndex = avatarIndex,
                isOnboarded = true
            )
        )
    }

    // Media Queries
    fun getFeaturedHero(): MediaItem = MockCatalog.movies.first { it.id == "movie-1" }
    fun getFeaturedSeries(): MediaItem = MockCatalog.series.first { it.id == "series-1" }

    fun getTrending(): List<MediaItem> = MockCatalog.allMedia.filter { it.isTrending }
    fun getPopularMovies(): List<MediaItem> = MockCatalog.movies.filter { it.isPopular }
    fun getPopularSeries(): List<MediaItem> = MockCatalog.series.filter { it.isPopular }
    fun getRecommended(): List<MediaItem> = MockCatalog.allMedia.sortedByDescending { it.rating }
    fun getRecentlyAdded(): List<MediaItem> = MockCatalog.allMedia.filter { it.isNewRelease }

    fun getAllMovies(): List<MediaItem> = MockCatalog.movies
    fun getAllSeries(): List<MediaItem> = MockCatalog.series

    fun getMediaById(id: String): MediaItem? {
        return MockCatalog.allMedia.find { it.id == id }
    }

    fun getSimilarMedia(item: MediaItem): List<MediaItem> {
        val targetGenre = item.genres.firstOrNull() ?: "Action"
        return MockCatalog.allMedia
            .filter { it.id != item.id && it.genres.any { g -> g.equals(targetGenre, ignoreCase = true) } }
            .take(6)
    }

    // Watchlist
    val watchlistMedia: Flow<List<MediaItem>> = dao.getWatchlist().map { list ->
        val idSet = list.map { it.mediaId }.toSet()
        MockCatalog.allMedia.filter { idSet.contains(it.id) }
    }

    fun isItemInWatchlist(mediaId: String): Flow<Boolean> = dao.isInWatchlist(mediaId)

    suspend fun toggleWatchlist(mediaId: String, currentInWatchlist: Boolean) {
        if (currentInWatchlist) {
            dao.removeFromWatchlist(mediaId)
        } else {
            dao.addToWatchlist(WatchlistEntity(mediaId = mediaId))
        }
    }

    // Continue Watching / History
    val continueWatching: Flow<List<MediaItem>> = dao.getWatchHistory().map { historyList ->
        if (historyList.isEmpty()) {
            // Provide sensible defaults from catalog with pre-set progress
            MockCatalog.allMedia.filter { it.watchProgress != null }
        } else {
            val progressMap = historyList.associate { it.mediaId to it.progressPercent }
            val items = MockCatalog.allMedia.filter { progressMap.containsKey(it.id) }.map {
                it.copy(
                    watchProgress = progressMap[it.id] ?: 0.5f,
                    remainingTimeText = "${((1f - (progressMap[it.id] ?: 0.5f)) * 90).toInt()}m left"
                )
            }
            if (items.isEmpty()) MockCatalog.allMedia.filter { it.watchProgress != null } else items
        }
    }

    suspend fun updateWatchProgress(mediaId: String, progress: Float) {
        dao.saveWatchProgress(
            WatchHistoryEntity(
                mediaId = mediaId,
                progressPercent = progress.coerceIn(0f, 1f)
            )
        )
    }

    // Searches
    val recentSearches: Flow<List<String>> = dao.getRecentSearches().map { list ->
        list.map { it.query }
    }

    suspend fun addSearchQuery(query: String) {
        val trimmed = query.trim()
        if (trimmed.isNotBlank()) {
            dao.addRecentSearch(RecentSearchEntity(query = trimmed))
        }
    }

    suspend fun removeSearchQuery(query: String) {
        dao.deleteRecentSearch(query)
    }

    suspend fun clearAllRecentSearches() {
        dao.clearRecentSearches()
    }

    fun searchCatalog(query: String, filterType: String, genreFilter: String): List<MediaItem> {
        val q = query.trim().lowercase()
        return MockCatalog.allMedia.filter { item ->
            val matchesQuery = q.isEmpty() ||
                    item.title.lowercase().contains(q) ||
                    item.genres.any { it.lowercase().contains(q) } ||
                    item.director.lowercase().contains(q) ||
                    item.cast.any { it.name.lowercase().contains(q) }

            val matchesType = when (filterType) {
                "Movies" -> item.type == MediaType.MOVIE
                "Series" -> item.type == MediaType.SERIES
                else -> true
            }

            val matchesGenre = genreFilter == "All" || item.genres.any { it.equals(genreFilter, ignoreCase = true) }

            matchesQuery && matchesType && matchesGenre
        }
    }

    // Live Channels
    val liveChannels: Flow<List<LiveChannel>> = dao.getFavoriteChannels().map { favList ->
        val favIds = favList.map { it.channelId }.toSet()
        MockCatalog.liveChannels.map { channel ->
            channel.copy(isFavorite = favIds.contains(channel.id) || channel.isFavorite)
        }
    }

    suspend fun toggleFavoriteChannel(channelId: String, currentFav: Boolean) {
        if (currentFav) {
            dao.removeFavoriteChannel(channelId)
        } else {
            dao.addFavoriteChannel(FavoriteChannelEntity(channelId = channelId))
        }
    }
}
