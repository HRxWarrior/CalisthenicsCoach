package com.caliscoach.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.caliscoach.app.ui.screens.DashboardScreen
import com.caliscoach.app.ui.screens.ExerciseScreen
import com.caliscoach.app.ui.screens.HistoryScreen
import com.caliscoach.app.ui.screens.RoutineDetailScreen
import com.caliscoach.app.ui.screens.RoutinesScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) { DashboardScreen(navController) }
        composable(Screen.History.route) { HistoryScreen(navController) }
        composable(Screen.Routines.route) { RoutinesScreen(navController) }
        composable(Screen.Exercise.route, arguments = listOf(navArgument("type") { type = NavType.StringType })) { entry ->
            ExerciseScreen(navController, entry.arguments?.getString("type") ?: "PUSHUP")
        }
        composable(Screen.RoutineDetail.route, arguments = listOf(navArgument("index") { type = NavType.IntType })) { entry ->
            RoutineDetailScreen(navController, entry.arguments?.getInt("index") ?: 0)
        }
    }
}
