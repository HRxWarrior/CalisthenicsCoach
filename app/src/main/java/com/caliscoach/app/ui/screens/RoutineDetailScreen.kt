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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.caliscoach.app.data.model.ExerciseType
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
fun RoutineDetailScreen(navController: NavController, index: Int) {
    val routine = RoutineData.all.getOrNull(index) ?: return
    val colors = listOf(AccentGreen, AccentBlue, AccentOrange)

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = AccentGreen) }
            Column {
                Text(routine.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text("${routine.difficulty} ~ ${routine.estimatedMinutes} min", color = TextSecondary, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(routine.exercises) { idx, ex ->
                val color = when (ex.type) { ExerciseType.PUSHUP -> AccentGreen; ExerciseType.SQUAT -> AccentBlue; ExerciseType.PLANK -> AccentOrange; else -> AccentGreen }
                FitnessCard(accent = color, onClick = { navController.navigate(Screen.Exercise.createRoute(ex.type.name)) }) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("${idx + 1}. ${ex.type.label}", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            val target = if (ex.targetReps > 0) "${ex.targetReps} reps" else "${ex.targetSeconds}s hold"
                            Text("Target: $target", color = TextSecondary, fontSize = 13.sp)
                        }
                        Text("START", color = color, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
