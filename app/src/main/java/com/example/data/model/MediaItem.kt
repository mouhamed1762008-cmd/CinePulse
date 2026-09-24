package com.example.data.model

enum class MediaType {
    MOVIE,
    SERIES
}

data class CastMember(
    val name: String,
    val character: String,
    val avatarInitials: String = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
)

data class Episode(
    val episodeNumber: Int,
    val seasonNumber: Int,
    val title: String,
    val duration: String,
    val synopsis: String,
    val thumbnailGradientIndex: Int = episodeNumber % 5
)

data class Season(
    val seasonNumber: Int,
    val title: String,
    val episodesCount: Int,
    val episodes: List<Episode>
)

data class MediaItem(
    val id: String,
    val title: String,
    val type: MediaType,
    val tagline: String = "",
    val description: String,
    val year: Int,
    val ageRating: String, // "PG-13", "16+", "18+", "All"
    val durationOrSeasons: String, // "2h 18m" or "3 Seasons"
    val rating: Float, // e.g. 8.9f
    val genres: List<String>,
    val director: String,
    val cast: List<CastMember>,
    val videoQuality: String = "4K Ultra HD",
    val audioTrack: String = "Dolby Atmos 5.1",
    val seasons: List<Season> = emptyList(),
    val backdropDrawableRes: Int? = null,
    val posterGradientIndex: Int = 0,
    val watchProgress: Float? = null, // 0.0f to 1.0f
    val remainingTimeText: String? = null,
    val isFeatured: Boolean = false,
    val isTrending: Boolean = false,
    val isPopular: Boolean = false,
    val isNewRelease: Boolean = false,
    val isTopRated: Boolean = false
)
