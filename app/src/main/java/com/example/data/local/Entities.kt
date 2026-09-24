package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val userName: String = "Alex Stone",
    val planName: String = "Ultra 4K HDR VIP",
    val planExpires: String = "Active • Auto-renews Dec 2026",
    val avatarIndex: Int = 0,
    val notificationsEnabled: Boolean = true,
    val preferredQuality: String = "4K Ultra HD",
    val audioLanguage: String = "English (Dolby Atmos)",
    val downloadWifiOnly: Boolean = true,
    val isOnboarded: Boolean = false
)

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val mediaId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey val mediaId: String,
    val progressPercent: Float = 0.5f,
    val lastEpisodeTitle: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey val query: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_channels")
data class FavoriteChannelEntity(
    @PrimaryKey val channelId: String,
    val addedAt: Long = System.currentTimeMillis()
)
