package com.example.crikstatsassignment.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crikstatsassignment.ui.home.HomeScreen

private const val PLAYER_STATS_ACTIVITY = "com.example.crikstatsassignment.feature_player.PlayerStatsActivity"

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToPlayerStats = {
                    // Navigate to the activity in the feature module
                    try {
                        val intent = Intent(context, Class.forName(PLAYER_STATS_ACTIVITY))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        // Handle exception if module is not available
                        e.printStackTrace()
                    }
                }
            )
        }
        // Note: The player_stats screen is in a separate activity
        // in the feature module, as per the assignment's user flow.
    }

