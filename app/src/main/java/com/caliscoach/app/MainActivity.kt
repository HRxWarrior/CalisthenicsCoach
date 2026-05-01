package com.caliscoach.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.caliscoach.app.ui.components.BottomNav
import com.caliscoach.app.ui.navigation.NavGraph
import com.caliscoach.app.ui.theme.CalisTheme
import com.caliscoach.app.ui.theme.DarkBg

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalisTheme {
                val navController = rememberNavController()
                val current by navController.currentBackStackEntryAsState()
                val showNav = current?.destination?.route in listOf("dashboard", "history", "routines")
                Scaffold(bottomBar = { if (showNav) BottomNav(navController) }, containerColor = DarkBg) { p ->
                    Box(Modifier.padding(p)) { NavGraph(navController) }
                }
            }
        }
    }
}
