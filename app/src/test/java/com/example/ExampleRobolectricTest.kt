package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.MockCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches CinePulse`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("CinePulse", appName)
    }

    @Test
    fun `mock catalog has movies and series with valid fields`() {
        assertTrue(MockCatalog.movies.isNotEmpty())
        assertTrue(MockCatalog.series.isNotEmpty())
        assertTrue(MockCatalog.liveChannels.isNotEmpty())

        val hero = MockCatalog.movies.first { it.id == "movie-1" }
        assertEquals("Chrono Shift: 2088", hero.title)
        assertTrue(hero.rating > 8.0f)
        assertNotNull(hero.backdropDrawableRes)
    }

    @Test
    fun `series has episodes and seasons`() {
        val series = MockCatalog.series.first { it.id == "series-1" }
        assertEquals("Aetheria: Echoes of Eternity", series.title)
        assertTrue(series.seasons.isNotEmpty())
        assertTrue(series.seasons.first().episodes.isNotEmpty())
    }

    @Test
    fun `live channels have active schedules`() {
        val channel = MockCatalog.liveChannels.first()
        assertTrue(channel.schedule.isNotEmpty())
        assertTrue(channel.viewersCount.isNotEmpty())
        assertTrue(channel.isLive)
    }
}
