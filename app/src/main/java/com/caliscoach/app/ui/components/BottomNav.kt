package com.caliscoach.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.DarkSurface
import com.caliscoach.app.ui.theme.TextDim

data class NavItem(val route: String, val label: String, val icon: ImageVector)
val navItems = listOf(
    NavItem("dashboard", "Home", Icons.Default.FitnessCenter),
    NavItem("history", "History", Icons.Default.History),
    NavItem("routines", "Routines", Icons.Default.PlayArrow),
)

@Composable
fun BottomNav(navController: NavController) {
    val current = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar(containerColor = DarkSurface) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = current == item.route,
                onClick = { if (current != item.route) navController.navigate(item.route) { popUpTo("dashboard") { saveState = true }; launchSingleTop = true; restoreState = true } },
                icon = { Icon(item.icon, item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = AccentGreen, selectedTextColor = AccentGreen, unselectedIconColor = TextDim, unselectedTextColor = TextDim, indicatorColor = DarkSurface)
            )
        }
    }
}
