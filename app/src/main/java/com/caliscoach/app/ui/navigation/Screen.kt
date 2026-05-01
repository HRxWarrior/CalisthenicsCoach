package com.caliscoach.app.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object History : Screen("history")
    object Routines : Screen("routines")
    object Exercise : Screen("exercise/{type}") { fun createRoute(type: String) = "exercise/$type" }
    object RoutineDetail : Screen("routine/{index}") { fun createRoute(idx: Int) = "routine/$idx" }
}
