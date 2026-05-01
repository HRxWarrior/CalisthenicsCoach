package com.caliscoach.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.caliscoach.app.ui.components.FitnessCard
import com.caliscoach.app.ui.components.SimpleBarChart
import com.caliscoach.app.ui.components.StatBox
import com.caliscoach.app.ui.navigation.Screen
import com.caliscoach.app.ui.theme.AccentBlue
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.AccentOrange
import com.caliscoach.app.ui.theme.AccentPurple
import com.caliscoach.app.ui.theme.DarkBg
import com.caliscoach.app.ui.theme.TextPrimary
import com.caliscoach.app.ui.theme.TextSecondary
import com.caliscoach.app.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(navController: NavController, vm: DashboardViewModel = viewModel()) {
    val weekly by vm.weeklyStats.collectAsState()
    val pushups by vm.totalPushups.collectAsState()
    val squats by vm.totalSquats.collectAsState()
    val plank by vm.totalPlankSec.collectAsState()
    val streak by vm.currentStreak.collectAsState()

    Column(Modifier.fillMaxSize().background(DarkBg).verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("CALISTHENICS", color = AccentGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Dashboard", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Spacer(Modifier.height(20.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatBox("Push-ups", "$pushups", AccentGreen)
            StatBox("Squats", "$squats", AccentBlue)
            StatBox("Plank", "${plank / 60}m", AccentOrange)
            StatBox("Streak", "$streak", AccentPurple)
        }

        Spacer(Modifier.height(20.dp))
        FitnessCard(accent = AccentGreen) {
            Column {
                Text("Weekly Activity", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                val chartData = weekly.takeLast(7).map { s ->
                    val day = s.date.takeLast(2)
                    day to (s.totalPushups + s.totalSquats)
                }
                if (chartData.isNotEmpty()) {
                    SimpleBarChart(chartData, chartData.maxOf { it.second })
                } else {
                    Text("No workouts yet. Start training!", color = TextSecondary, fontSize = 13.sp)
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("Quick Start", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))

        FitnessCard(accent = AccentGreen, onClick = { navController.navigate(Screen.Exercise.createRoute("PUSHUP")) }) {
            Row { androidx.compose.material3.Icon(Icons.Default.FitnessCenter, "Pushups", tint = AccentGreen); Spacer(Modifier.padding(8.dp)); Column { Text("Push-ups", color = TextPrimary, fontWeight = FontWeight.Bold); Text("Camera + form tracking", color = TextSecondary, fontSize = 12.sp) } }
        }
        Spacer(Modifier.height(8.dp))
        FitnessCard(accent = AccentBlue, onClick = { navController.navigate(Screen.Exercise.createRoute("SQUAT")) }) {
            Row { androidx.compose.material3.Icon(Icons.Default.DirectionsRun, "Squats", tint = AccentBlue); Spacer(Modifier.padding(8.dp)); Column { Text("Squats", color = TextPrimary, fontWeight = FontWeight.Bold); Text("Rep counting + knee check", color = TextSecondary, fontSize = 12.sp) } }
        }
        Spacer(Modifier.height(8.dp))
        FitnessCard(accent = AccentOrange, onClick = { navController.navigate(Screen.Exercise.createRoute("PLANK")) }) {
            Row { androidx.compose.material3.Icon(Icons.Default.SelfImprovement, "Plank", tint = AccentOrange); Spacer(Modifier.padding(8.dp)); Column { Text("Plank", color = TextPrimary, fontWeight = FontWeight.Bold); Text("Timer + posture feedback", color = TextSecondary, fontSize = 12.sp) } }
        }
        Spacer(Modifier.height(80.dp))
    }
}
