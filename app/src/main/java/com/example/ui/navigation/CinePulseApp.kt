package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.components.CineNavTab
import com.example.ui.components.CinePulseBottomBar
import com.example.ui.screens.DetailsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveTvScreen
import com.example.ui.screens.MoviesScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.PlayerScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SeriesScreen
import com.example.ui.theme.BackgroundDark
import com.example.ui.viewmodel.CinePulseViewModel

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Search : Screen("search")
    object LiveTv : Screen("live_tv")
    object Movies : Screen("movies")
    object Series : Screen("series")
    object Profile : Screen("profile")
    object Details : Screen("details/{mediaId}") {
        fun createRoute(mediaId: String) = "details/$mediaId"
    }
    object Player : Screen("player/{mediaId}?season={season}&episode={episode}") {
        fun createRoute(mediaId: String, season: Int? = null, episode: Int? = null): String {
            return if (season != null && episode != null) {
                "player/$mediaId?season=$season&episode=$episode"
            } else {
                "player/$mediaId"
            }
        }
    }
}

@Composable
fun CinePulseApp(
    viewModel: CinePulseViewModel,
    navController: NavHostController = rememberNavController()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine current active tab
    val currentTab = when (currentRoute) {
        Screen.Home.route -> CineNavTab.HOME
        Screen.Search.route -> CineNavTab.SEARCH
        Screen.LiveTv.route -> CineNavTab.LIVE_TV
        Screen.Movies.route -> CineNavTab.MOVIES
        Screen.Series.route -> CineNavTab.SERIES
        Screen.Profile.route -> CineNavTab.PROFILE
        else -> CineNavTab.HOME
    }

    // Show bottom bar only on the 6 primary tabs
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.LiveTv.route,
        Screen.Movies.route,
        Screen.Series.route,
        Screen.Profile.route
    )

    // Initial route based on onboarding state
    val startDestination = if (userProfile.isOnboarded) Screen.Home.route else Screen.Onboarding.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        bottomBar = {
            if (showBottomBar) {
                CinePulseBottomBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = BackgroundDark
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp)
        ) {
            // Screen 1: Enter Your Name Screen
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onContinue = { name ->
                        viewModel.saveUserName(name)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Onboarding.route) { inclusive = true }
                        }
                    }
                )
            }

            // Screen 2: Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { mediaId ->
                        navController.navigate(Screen.Details.createRoute(mediaId))
                    },
                    onNavigateToPlayer = { mediaId ->
                        navController.navigate(Screen.Player.createRoute(mediaId))
                    },
                    onNavigateToSearch = {
                        navController.navigate(Screen.Search.route)
                    },
                    onNavigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    },
                    onSeeAllMovies = {
                        navController.navigate(Screen.Movies.route)
                    },
                    onSeeAllSeries = {
                        navController.navigate(Screen.Series.route)
                    }
                )
            }

            // Screen 3: Search Screen
            composable(Screen.Search.route) {
                SearchScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { mediaId ->
                        navController.navigate(Screen.Details.createRoute(mediaId))
                    }
                )
            }

            // Screen 4: Live TV Screen
            composable(Screen.LiveTv.route) {
                LiveTvScreen(
                    viewModel = viewModel,
                    onWatchLive = { channelId ->
                        navController.navigate(Screen.Player.createRoute("live_$channelId"))
                    }
                )
            }

            // Screen 5: Profile Screen
            composable(Screen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { mediaId ->
                        navController.navigate(Screen.Details.createRoute(mediaId))
                    },
                    onLogout = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // Screen 6: Movie / Series Details Screen
            composable(
                route = Screen.Details.route,
                arguments = listOf(navArgument("mediaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: "movie-1"
                DetailsScreen(
                    mediaId = mediaId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onPlay = { id, season, episode ->
                        navController.navigate(Screen.Player.createRoute(id, season, episode))
                    },
                    onNavigateToDetails = { newMediaId ->
                        navController.navigate(Screen.Details.createRoute(newMediaId))
                    }
                )
            }

            // Screen 7: Video Player Screen
            composable(
                route = Screen.Player.route,
                arguments = listOf(
                    navArgument("mediaId") { type = NavType.StringType },
                    navArgument("season") {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument("episode") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: "movie-1"
                val season = backStackEntry.arguments?.getInt("season")?.takeIf { it != -1 }
                val episode = backStackEntry.arguments?.getInt("episode")?.takeIf { it != -1 }

                PlayerScreen(
                    mediaId = mediaId,
                    seasonNumber = season,
                    episodeNumber = episode,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // Screen 8: Series Screen
            composable(Screen.Series.route) {
                SeriesScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { mediaId ->
                        navController.navigate(Screen.Details.createRoute(mediaId))
                    },
                    onNavigateToPlayer = { mediaId ->
                        navController.navigate(Screen.Player.createRoute(mediaId))
                    }
                )
            }

            // Screen 9: Movies Screen
            composable(Screen.Movies.route) {
                MoviesScreen(
                    viewModel = viewModel,
                    onNavigateToDetails = { mediaId ->
                        navController.navigate(Screen.Details.createRoute(mediaId))
                    },
                    onNavigateToPlayer = { mediaId ->
                        navController.navigate(Screen.Player.createRoute(mediaId))
                    }
                )
            }
        }
    }
}
