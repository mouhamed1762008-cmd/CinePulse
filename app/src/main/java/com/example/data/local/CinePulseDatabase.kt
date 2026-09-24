package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserProfileEntity::class,
        WatchlistEntity::class,
        WatchHistoryEntity::class,
        RecentSearchEntity::class,
        FavoriteChannelEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CinePulseDatabase : RoomDatabase() {

    abstract fun cinePulseDao(): CinePulseDao

    companion object {
        @Volatile
        private var INSTANCE: CinePulseDatabase? = null

        fun getDatabase(context: Context): CinePulseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CinePulseDatabase::class.java,
                    "cinepulse_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
