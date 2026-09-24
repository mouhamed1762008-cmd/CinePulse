package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.UserProfileEntity
import com.example.data.model.LiveChannel
import com.example.data.model.MediaItem
import com.example.data.model.MockCatalog
import com.example.data.repository.CinePulseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CinePulseViewModel(
    private val repository: CinePulseRepository
) : ViewModel() {

    val userProfile: StateFlow<UserProfileEntity> = repository.userProfile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfileEntity(userName = "Alex Stone", isOnboarded = false)
        )

    val watchlist: StateFlow<List<MediaItem>> = repository.watchlistMedia
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val continueWatching: StateFlow<List<MediaItem>> = repository.continueWatching
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MockCatalog.allMedia.filter { it.watchProgress != null }
        )

    val recentSearches: StateFlow<List<String>> = repository.recentSearches
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf("Cyberpunk 2088", "Aetheria", "Champions League", "Interstellar")
        )

    val liveChannels: StateFlow<List<LiveChannel>> = repository.liveChannels
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MockCatalog.liveChannels
        )

    // Search UI State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchFilterType = MutableStateFlow("All")
    val searchFilterType: StateFlow<String> = _searchFilterType.asStateFlow()

    private val _searchGenreFilter = MutableStateFlow("All")
    val searchGenreFilter: StateFlow<String> = _searchGenreFilter.asStateFlow()

    // Live TV UI State
    private val _selectedLiveCategory = MutableStateFlow("All")
    val selectedLiveCategory: StateFlow<String> = _selectedLiveCategory.asStateFlow()

    private val _activeLiveChannelId = MutableStateFlow("ch-1")
    val activeLiveChannelId: StateFlow<String> = _activeLiveChannelId.asStateFlow()

    // Movie screen filter
    private val _movieGenreFilter = MutableStateFlow("All")
    val movieGenreFilter: StateFlow<String> = _movieGenreFilter.asStateFlow()

    private val _movieSortFilter = MutableStateFlow("Popular")
    val movieSortFilter: StateFlow<String> = _movieSortFilter.asStateFlow()

    // Series screen filter
    private val _seriesGenreFilter = MutableStateFlow("All")
    val seriesGenreFilter: StateFlow<String> = _seriesGenreFilter.asStateFlow()

    // Search results calculation
    val searchResults: StateFlow<List<MediaItem>> = combine(
        _searchQuery,
        _searchFilterType,
        _searchGenreFilter
    ) { query, type, genre ->
        repository.searchCatalog(query, type, genre)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MockCatalog.allMedia
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun setSearchFilterType(type: String) {
        _searchFilterType.value = type
    }

    fun setSearchGenreFilter(genre: String) {
        _searchGenreFilter.value = genre
    }

    fun submitSearch(query: String) {
        viewModelScope.launch {
            repository.addSearchQuery(query)
        }
    }

    fun removeRecentSearch(query: String) {
        viewModelScope.launch {
            repository.removeSearchQuery(query)
        }
    }

    fun clearAllSearches() {
        viewModelScope.launch {
            repository.clearAllRecentSearches()
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            repository.saveProfileName(name)
        }
    }

    fun toggleWatchlist(mediaId: String, currentInList: Boolean) {
        viewModelScope.launch {
            repository.toggleWatchlist(mediaId, currentInList)
        }
    }

    fun updateWatchProgress(mediaId: String, progress: Float) {
        viewModelScope.launch {
            repository.updateWatchProgress(mediaId, progress)
        }
    }

    fun selectLiveCategory(cat: String) {
        _selectedLiveCategory.value = cat
    }

    fun selectLiveChannel(channelId: String) {
        _activeLiveChannelId.value = channelId
    }

    fun toggleFavoriteChannel(channelId: String, currentFav: Boolean) {
        viewModelScope.launch {
            repository.toggleFavoriteChannel(channelId, currentFav)
        }
    }

    fun setMovieGenreFilter(genre: String) {
        _movieGenreFilter.value = genre
    }

    fun setMovieSortFilter(sort: String) {
        _movieSortFilter.value = sort
    }

    fun setSeriesGenreFilter(genre: String) {
        _seriesGenreFilter.value = genre
    }

    fun updateProfile(
        name: String,
        notifications: Boolean,
        quality: String,
        language: String
    ) {
        viewModelScope.launch {
            repository.updateSettings(
                notifications = notifications,
                preferredQuality = quality,
                preferredLanguage = language,
                avatarIndex = 0
            )
            repository.saveProfileName(name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.resetOnboarding()
        }
    }

    fun getMediaById(id: String): MediaItem? = repository.getMediaById(id)
    fun getSimilarMedia(media: MediaItem): List<MediaItem> = repository.getSimilarMedia(media)

    fun getFeaturedHero(): MediaItem = repository.getFeaturedHero()
    fun getFeaturedSeries(): MediaItem = repository.getFeaturedSeries()
    fun getTrending(): List<MediaItem> = repository.getTrending()
    fun getPopularMovies(): List<MediaItem> = repository.getPopularMovies()
    fun getPopularSeries(): List<MediaItem> = repository.getPopularSeries()
    fun getRecommended(): List<MediaItem> = repository.getRecommended()
    fun getRecentlyAdded(): List<MediaItem> = repository.getRecentlyAdded()
    fun getAllMovies(): List<MediaItem> = repository.getAllMovies()
    fun getAllSeries(): List<MediaItem> = repository.getAllSeries()

    companion object {
        fun provideFactory(repository: CinePulseRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CinePulseViewModel(repository) as T
                }
            }
    }
}
