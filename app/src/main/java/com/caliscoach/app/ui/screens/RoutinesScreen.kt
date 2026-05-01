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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.caliscoach.app.data.model.RoutineData
import com.caliscoach.app.ui.components.FitnessCard
import com.caliscoach.app.ui.navigation.Screen
import com.caliscoach.app.ui.theme.AccentBlue
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.AccentOrange
import com.caliscoach.app.ui.theme.DarkBg
import com.caliscoach.app.ui.theme.TextPrimary
import com.caliscoach.app.ui.theme.TextSecondary

@Composable
fun RoutinesScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Text("ROUTINES", color = AccentGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Beginner Programs", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Spacer(Modifier.height(16.dp))

        val colors = listOf(AccentGreen, AccentBlue, AccentOrange)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(RoutineData.all) { idx, routine ->
                FitnessCard(accent = colors[idx % colors.size], onClick = { navController.navigate(Screen.RoutineDetail.createRoute(idx)) }) {
                    Column {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(routine.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            Text(routine.difficulty, color = colors[idx % colors.size], fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text("${routine.exercises.size} exercises ~ ${routine.estimatedMinutes} min", color = TextSecondary, fontSize = 13.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(routine.exercises.joinToString(" | ") { it.type.label }, color = TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
