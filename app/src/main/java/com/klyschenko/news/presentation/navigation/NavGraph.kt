package com.klyschenko.news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.klyschenko.news.presentation.screen.settings.SettingScreen
import com.klyschenko.news.presentation.screen.subsriptions.SubscriptionsScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Subscriptions.route
    ) {
        composable(Screen.Subscriptions.route) {
            SubscriptionsScreen(
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingScreen(
                onBackButtonClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {

    data object Subscriptions : Screen(route = "Subscriptions")

    data object Settings : Screen(route = "Settings")

}