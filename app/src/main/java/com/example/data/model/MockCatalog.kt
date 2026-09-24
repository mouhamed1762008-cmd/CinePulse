package com.example.data.model

import com.example.R

object MockCatalog {

    val castChronoShift = listOf(
        CastMember("Alexander Vance", "Commander Liam Cross"),
        CastMember("Elena Rostova", "Dr. Lyra Sterling"),
        CastMember("Kenji Sato", "Chief Engineer Jin"),
        CastMember("Sarah Jenkins", "Director Miller"),
        CastMember("Marcus Thorne", "Zero Architect")
    )

    val castAetheria = listOf(
        CastMember("Clara Morgan", "Detective Maya Reed"),
        CastMember("David Hensley", "Inspector Julian Cole"),
        CastMember("Aisha Khan", "Dr. Noor Farooq"),
        CastMember("Tobias Drake", "The Observer")
    )

    val episodesAetheriaSeason1 = listOf(
        Episode(
            episodeNumber = 1,
            seasonNumber = 1,
            title = "Pilot: Into the Fog",
            duration = "58m",
            synopsis = "When an unexplained gravitational anomaly fractures downtown Neo-Berlin, Detective Maya Reed uncovers a classified surveillance protocol."
        ),
        Episode(
            episodeNumber = 2,
            seasonNumber = 1,
            title = "Zero Sum Mirage",
            duration = "52m",
            synopsis = "A memory broker's assassination leaves behind corrupted temporal records that point toward the high council."
        ),
        Episode(
            episodeNumber = 3,
            seasonNumber = 1,
            title = "Sub-Level Echoes",
            duration = "55m",
            synopsis = "Maya delves into the abandoned subterranean research vaults beneath Sector 7, finding a survivor from 1999."
        ),
        Episode(
            episodeNumber = 4,
            seasonNumber = 1,
            title = "The Quantum Veil",
            duration = "61m",
            synopsis = "Julian Cole races to decrypt the core archive before an automated system purge erases all evidence."
        )
    )

    val episodesAetheriaSeason2 = listOf(
        Episode(
            episodeNumber = 1,
            seasonNumber = 2,
            title = "Convergence",
            duration = "56m",
            synopsis = "Six months after the sector incident, satellite telemetry detects artificial pulses originating from deep orbit."
        ),
        Episode(
            episodeNumber = 2,
            seasonNumber = 2,
            title = "The Black Gate",
            duration = "59m",
            synopsis = "An unexpected alliance offers Maya access to the restricted Dyson sphere prototype."
        )
    )

    val movies = listOf(
        MediaItem(
            id = "movie-1",
            title = "Chrono Shift: 2088",
            type = MediaType.MOVIE,
            tagline = "Time wasn't meant to be rewritten.",
            description = "In the neon-drenched metropolis of Neo-Shinjuku, a rogue quantum chronologist races against an ominous syndicate to prevent an impending temporal collapse that threatens human existence across dimensions.",
            year = 2026,
            ageRating = "PG-13",
            durationOrSeasons = "2h 24m",
            rating = 8.9f,
            genres = listOf("Sci-Fi", "Cyberpunk", "Action", "Thriller"),
            director = "Marcus Nolan",
            cast = castChronoShift,
            videoQuality = "4K Dolby Vision",
            audioTrack = "Dolby Atmos 5.1",
            backdropDrawableRes = R.drawable.img_hero_cyberpunk_1790253854978,
            posterGradientIndex = 0,
            watchProgress = 0.65f,
            remainingTimeText = "51m left",
            isFeatured = true,
            isTrending = true,
            isPopular = true,
            isNewRelease = true,
            isTopRated = true
        ),
        MediaItem(
            id = "movie-2",
            title = "Shadows of the Abyss",
            type = MediaType.MOVIE,
            tagline = "Deep beneath the ice, truth sleeps in darkness.",
            description = "An oceanic deep-sea exploration crew at the Mariana Trench discovers an ancient alien spire radiating bio-luminescent signals into the solar system.",
            year = 2025,
            ageRating = "16+",
            durationOrSeasons = "2h 11m",
            rating = 8.4f,
            genres = listOf("Sci-Fi", "Mystery", "Horror"),
            director = "Elena Vasquez",
            cast = listOf(
                CastMember("Rachel Weisz", "Dr. Susan Miller"),
                CastMember("Idris Alba", "Captain Jonas Reed"),
                CastMember("Stellan Skars", "Dr. Carlsson")
            ),
            videoQuality = "4K UHD",
            audioTrack = "Dolby 5.1",
            backdropDrawableRes = null,
            posterGradientIndex = 1,
            watchProgress = 0.28f,
            remainingTimeText = "1h 34m left",
            isTrending = true,
            isPopular = true,
            isNewRelease = false,
            isTopRated = false
        ),
        MediaItem(
            id = "movie-3",
            title = "Nebula Odyssey",
            type = MediaType.MOVIE,
            tagline = "The final beacon of mankind.",
            description = "A daring interstellar crew embarks through a relativistic wormhole to colonize an earth-like world, encountering gravitational paradoxes that bend their sanity.",
            year = 2026,
            ageRating = "PG-13",
            durationOrSeasons = "2h 45m",
            rating = 9.1f,
            genres = listOf("Sci-Fi", "Adventure", "Drama"),
            director = "Christopher Vance",
            cast = listOf(
                CastMember("Matthew Miller", "Commander Cooper"),
                CastMember("Jessica Chastain", "Dr. Amelia Brand"),
                CastMember("Michael Caine", "Prof. Brand")
            ),
            videoQuality = "IMAX Enhanced 4K",
            audioTrack = "DTS:X Spatial",
            backdropDrawableRes = null,
            posterGradientIndex = 2,
            isTrending = true,
            isPopular = true,
            isNewRelease = true,
            isTopRated = true
        ),
        MediaItem(
            id = "movie-4",
            title = "The Midnight Syndicate",
            type = MediaType.MOVIE,
            tagline = "The ultimate heist leaves no footprints.",
            description = "An elite squad of specialized operatives plan a daring zero-gravity heist on an orbital financial satellite, only to be betrayed from within.",
            year = 2025,
            ageRating = "18+",
            durationOrSeasons = "2h 05m",
            rating = 8.6f,
            genres = listOf("Action", "Crime", "Thriller"),
            director = "Denis Villeneuve",
            cast = listOf(
                CastMember("Tom Hardy", "Vincent Stone"),
                CastMember("Zoe Saldana", "Kira Valen"),
                CastMember("Oscar Isaac", "Gabriel Cross")
            ),
            videoQuality = "4K UHD HDR",
            audioTrack = "Dolby Atmos",
            backdropDrawableRes = null,
            posterGradientIndex = 3,
            isTrending = true,
            isPopular = false,
            isNewRelease = false,
            isTopRated = true
        ),
        MediaItem(
            id = "movie-5",
            title = "Apex Protocol",
            type = MediaType.MOVIE,
            tagline = "Trust nobody in the digital shadows.",
            description = "When autonomous defense drones are hijacked by an enigmatic AI cluster, an ex-black ops cyber agent must navigate corrupt megacorporations to shut down the kill-switch.",
            year = 2026,
            ageRating = "16+",
            durationOrSeasons = "1h 58m",
            rating = 8.2f,
            genres = listOf("Action", "Espionage", "Cyberpunk"),
            director = "Chad Stahelski",
            cast = listOf(
                CastMember("Keanu Thorne", "Agent John Mercer"),
                CastMember("Hiroyuki Sanada", "Kazuya Lord"),
                CastMember("Ana de Armas", "Sophia Cole")
            ),
            videoQuality = "4K UHD",
            audioTrack = "Dolby 5.1",
            backdropDrawableRes = null,
            posterGradientIndex = 4,
            isTrending = false,
            isPopular = true,
            isNewRelease = true,
            isTopRated = false
        ),
        MediaItem(
            id = "movie-6",
            title = "Whispers in the Starlight",
            type = MediaType.MOVIE,
            tagline = "Across lightyears, some bonds never break.",
            description = "Two astrophysicists stationed on opposite planetary observation outposts communicate via entangled radio frequencies, finding love across the cosmic void.",
            year = 2024,
            ageRating = "All",
            durationOrSeasons = "1h 50m",
            rating = 8.5f,
            genres = listOf("Romance", "Drama", "Sci-Fi"),
            director = "Sofia Coppola",
            cast = listOf(
                CastMember("Emma Stone", "Dr. Chloe Martin"),
                CastMember("Timothée Chalamet", "Julian Drake")
            ),
            videoQuality = "4K UHD",
            audioTrack = "Dolby Stereo",
            backdropDrawableRes = null,
            posterGradientIndex = 5,
            isTrending = false,
            isPopular = false,
            isNewRelease = false,
            isTopRated = true
        )
    )

    val series = listOf(
        MediaItem(
            id = "series-1",
            title = "Aetheria: Echoes of Eternity",
            type = MediaType.SERIES,
            tagline = "The rain washes away nothing in this city.",
            description = "In the rain-soaked dystopian metropolis of New Avalon, veteran homicide detective Maya Reed is assigned to solve a series of impossibly executed murders linked to an outlawed neural synchronization tech.",
            year = 2026,
            ageRating = "18+",
            durationOrSeasons = "3 Seasons",
            rating = 9.3f,
            genres = listOf("Mystery", "Sci-Fi", "Noir", "Drama"),
            director = "David Fincher",
            cast = castAetheria,
            videoQuality = "4K Dolby Vision",
            audioTrack = "Dolby Atmos 7.1",
            backdropDrawableRes = R.drawable.img_hero_series_1790253867708,
            posterGradientIndex = 6,
            watchProgress = 0.45f,
            remainingTimeText = "S2:E3 22m left",
            seasons = listOf(
                Season(1, "Season 1", 4, episodesAetheriaSeason1),
                Season(2, "Season 2", 2, episodesAetheriaSeason2),
                Season(3, "Season 3", 8, emptyList())
            ),
            isFeatured = true,
            isTrending = true,
            isPopular = true,
            isNewRelease = true,
            isTopRated = true
        ),
        MediaItem(
            id = "series-2",
            title = "Silicon Underworld",
            type = MediaType.SERIES,
            tagline = "Code is the new currency of blood.",
            description = "A gritty tech thriller following an underground group of cryptographic hackers taking down sovereign debt cartels and AI monopolies.",
            year = 2025,
            ageRating = "16+",
            durationOrSeasons = "4 Seasons",
            rating = 8.9f,
            genres = listOf("Crime", "Drama", "Tech Thriller"),
            director = "Sam Esmail",
            cast = listOf(
                CastMember("Rami Malek", "Elliot Vance"),
                CastMember("Christian Slater", "Cipher Leader"),
                CastMember("Carly Chaikin", "Darlene")
            ),
            videoQuality = "4K UHD",
            audioTrack = "Dolby 5.1",
            backdropDrawableRes = null,
            posterGradientIndex = 7,
            watchProgress = 0.85f,
            remainingTimeText = "S3:E8 12m left",
            isTrending = true,
            isPopular = true,
            isNewRelease = false,
            isTopRated = true
        ),
        MediaItem(
            id = "series-3",
            title = "The Last Dominion",
            type = MediaType.SERIES,
            tagline = "Kingdoms fall when dragons wake.",
            description = "An epic dark fantasy saga of seven fractured noble houses competing for dominion over the frozen crystal throne as an ancient celestial eclipse approaches.",
            year = 2025,
            ageRating = "18+",
            durationOrSeasons = "2 Seasons",
            rating = 9.0f,
            genres = listOf("Fantasy", "Action", "Adventure", "Drama"),
            director = "Miguel Sapochnik",
            cast = listOf(
                CastMember("Henry Cavill", "Lord Vaelor"),
                CastMember("Katheryn Winnick", "Queen Astrid"),
                CastMember("Charles Dance", "High Chancellor")
            ),
            videoQuality = "4K UHD HDR",
            audioTrack = "Dolby Atmos",
            backdropDrawableRes = null,
            posterGradientIndex = 8,
            isTrending = true,
            isPopular = true,
            isNewRelease = false,
            isTopRated = true
        ),
        MediaItem(
            id = "series-4",
            title = "Orbital Station 9",
            type = MediaType.SERIES,
            tagline = "Isolated in the cold silence of Jupiter.",
            description = "A deep-space research crew on Jupiter's moon Europa must confront unexplained acoustic signals coming from the sub-glacial ocean.",
            year = 2026,
            ageRating = "16+",
            durationOrSeasons = "3 Seasons",
            rating = 8.7f,
            genres = listOf("Sci-Fi", "Mystery", "Thriller"),
            director = "Ridley Scott",
            cast = listOf(
                CastMember("Sigourney Frost", "Dr. Ripley Stone"),
                CastMember("Michael Fassbender", "David Unit 8")
            ),
            videoQuality = "4K UHD",
            audioTrack = "Dolby Atmos",
            backdropDrawableRes = null,
            posterGradientIndex = 9,
            isTrending = false,
            isPopular = true,
            isNewRelease = true,
            isTopRated = false
        ),
        MediaItem(
            id = "series-5",
            title = "Chronicles of the Deep",
            type = MediaType.SERIES,
            tagline = "Earth's final untamed frontier.",
            description = "An immersive natural history documentary series capturing the bioluminescent creatures and hydrothermal vents of the world's most remote ocean trenches.",
            year = 2024,
            ageRating = "All",
            durationOrSeasons = "1 Season",
            rating = 9.4f,
            genres = listOf("Documentary", "Nature", "Exploration"),
            director = "David Attenborough",
            cast = listOf(
                CastMember("Sir David Attenborough", "Narrator")
            ),
            videoQuality = "4K HDR 60fps",
            audioTrack = "Dolby Atmos Spatial",
            backdropDrawableRes = null,
            posterGradientIndex = 10,
            isTrending = false,
            isPopular = false,
            isNewRelease = false,
            isTopRated = true
        )
    )

    val allMedia: List<MediaItem> = movies + series

    val liveChannels = listOf(
        LiveChannel(
            id = "ch-1",
            channelNumber = 101,
            name = "PULSE SPORTS 1 HD",
            category = "Sports",
            logoText = "SPORTS 1",
            currentProgram = "UEFA Champions Super Final: Madrid vs Milan",
            timeSlot = "19:45 - 22:15",
            programDescription = "Live coverage of the decisive cup clash direct from the Grand Coliseum with multi-angle ultra slow-motion and stadium commentary.",
            progress = 0.62f,
            viewersCount = "2.4M watching",
            quality = "4K 60fps HDR",
            isLive = true,
            isFavorite = true,
            backdropRes = R.drawable.img_live_arena_1790253891399,
            schedule = listOf(
                ProgramSchedule("18:30", "Pre-Match Studio & Analysis", "1h 15m"),
                ProgramSchedule("19:45", "UEFA Champions Super Final: Madrid vs Milan", "2h 30m", isCurrent = true),
                ProgramSchedule("22:15", "Post-Match Trophy Ceremony & Highlights", "45m"),
                ProgramSchedule("23:00", "SportsCenter Global Recap", "1h")
            )
        ),
        LiveChannel(
            id = "ch-2",
            channelNumber = 102,
            name = "CINESTREAM PRIME",
            category = "Cinema",
            logoText = "CINE PRIME",
            currentProgram = "Blockbuster Cinema: Interstellar Odyssey",
            timeSlot = "20:00 - 22:50",
            programDescription = "Uncut broadcast of the acclaimed cinematic masterpiece in native 4K with Dolby Atmos sound mix.",
            progress = 0.40f,
            viewersCount = "890K watching",
            quality = "1080p 60fps",
            isLive = true,
            isFavorite = true,
            schedule = listOf(
                ProgramSchedule("17:30", "Blade Runner 2049", "2h 30m"),
                ProgramSchedule("20:00", "Blockbuster Cinema: Interstellar Odyssey", "2h 50m", isCurrent = true),
                ProgramSchedule("22:50", "The Dark Knight Rises", "2h 45m")
            )
        ),
        LiveChannel(
            id = "ch-3",
            channelNumber = 103,
            name = "VELOCITY RACING",
            category = "Sports",
            logoText = "VELOCITY",
            currentProgram = "Formula Velocity: Grand Prix Night Race",
            timeSlot = "21:00 - 23:00",
            programDescription = "High-speed night street race through Singapore Marina Bay featuring on-board telemetry cameras.",
            progress = 0.75f,
            viewersCount = "1.2M watching",
            quality = "1080p 60fps HD",
            isLive = true,
            schedule = listOf(
                ProgramSchedule("20:00", "Drivers Grid Walk", "1h"),
                ProgramSchedule("21:00", "Formula Velocity: Grand Prix Night Race", "2h", isCurrent = true),
                ProgramSchedule("23:00", "Podium Celebrations & Team Radios", "30m")
            )
        ),
        LiveChannel(
            id = "ch-4",
            channelNumber = 104,
            name = "HORIZON NEWS 24",
            category = "News",
            logoText = "NEWS 24",
            currentProgram = "World News Live: Global Markets & Summit",
            timeSlot = "21:00 - 22:00",
            programDescription = "Comprehensive international news coverage, geopolitical analysis and real-time financial markets update.",
            progress = 0.20f,
            viewersCount = "450K watching",
            quality = "1080p HD",
            isLive = true,
            schedule = listOf(
                ProgramSchedule("20:00", "European Evening Dispatch", "1h"),
                ProgramSchedule("21:00", "World News Live: Global Markets & Summit", "1h", isCurrent = true),
                ProgramSchedule("22:00", "Deep Dive: Tech Revolution", "1h")
            )
        ),
        LiveChannel(
            id = "ch-5",
            channelNumber = 105,
            name = "NATURA GEO HD",
            category = "Documentary",
            logoText = "NATURA",
            currentProgram = "Wild Predators of Serengeti in 4K",
            timeSlot = "20:30 - 21:45",
            programDescription = "Breathtaking high-frame-rate documentary tracking lion prides across the vast African savannah.",
            progress = 0.50f,
            viewersCount = "320K watching",
            quality = "4K UHD",
            isLive = true,
            schedule = listOf(
                ProgramSchedule("19:00", "Secrets of the Amazon Basin", "1h 30m"),
                ProgramSchedule("20:30", "Wild Predators of Serengeti in 4K", "1h 15m", isCurrent = true),
                ProgramSchedule("21:45", "Cosmic Wonders: The James Webb Discoveries", "1h 15m")
            )
        ),
        LiveChannel(
            id = "ch-6",
            channelNumber = 106,
            name = "BEAT BEATS LIVE",
            category = "Music",
            logoText = "BEATS",
            currentProgram = "Electric Tomorrowland: Mainstage Live",
            timeSlot = "21:00 - 02:00",
            programDescription = "Live festival broadcast featuring top global DJs, light shows, fireworks and electronic dance music.",
            progress = 0.35f,
            viewersCount = "1.8M watching",
            quality = "1080p 60fps HD",
            isLive = true,
            schedule = listOf(
                ProgramSchedule("19:00", "Sunset Chillout Session", "2h"),
                ProgramSchedule("21:00", "Electric Tomorrowland: Mainstage Live", "5h", isCurrent = true)
            )
        ),
        LiveChannel(
            id = "ch-7",
            channelNumber = 107,
            name = "TOON GALAXY",
            category = "Kids",
            logoText = "TOONS",
            currentProgram = "Super Astro Explorers: Season 3",
            timeSlot = "21:00 - 21:30",
            programDescription = "Fun animated adventures for kids exploring colorful cosmic planets with robot companions.",
            progress = 0.80f,
            viewersCount = "210K watching",
            quality = "1080p HD",
            isLive = true,
            schedule = listOf(
                ProgramSchedule("20:30", "Dino Riders Adventure", "30m"),
                ProgramSchedule("21:00", "Super Astro Explorers: Season 3", "30m", isCurrent = true),
                ProgramSchedule("21:30", "Lego Legends Quest", "30m")
            )
        )
    )
}
