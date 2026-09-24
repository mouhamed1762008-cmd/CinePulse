package com.example.data.model

data class ProgramSchedule(
    val time: String,
    val title: String,
    val duration: String,
    val description: String = "",
    val isCurrent: Boolean = false
)

data class LiveChannel(
    val id: String,
    val channelNumber: Int,
    val name: String,
    val category: String, // "Sports", "Cinema", "News", "Documentary", "Music", "Kids"
    val logoText: String,
    val currentProgram: String,
    val timeSlot: String,
    val programDescription: String,
    val progress: Float, // 0.0f to 1.0f
    val viewersCount: String,
    val quality: String = "1080p 60fps HD",
    val isLive: Boolean = true,
    val isFavorite: Boolean = false,
    val backdropRes: Int? = null,
    val schedule: List<ProgramSchedule> = emptyList()
)
