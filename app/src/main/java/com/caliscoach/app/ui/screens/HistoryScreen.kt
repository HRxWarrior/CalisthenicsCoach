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
import androidx.compose.foundation.lazy.items
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
import com.caliscoach.app.ui.theme.AccentBlue
import com.caliscoach.app.ui.theme.AccentGreen
import com.caliscoach.app.ui.theme.AccentOrange
import com.caliscoach.app.ui.theme.DarkBg
import com.caliscoach.app.ui.theme.TextPrimary
import com.caliscoach.app.ui.theme.TextSecondary
import com.caliscoach.app.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(navController: NavController, vm: HistoryViewModel = viewModel()) {
    val workouts by vm.workouts.collectAsState()

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Text("HISTORY", color = AccentGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Workout Log", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Spacer(Modifier.height(16.dp))

        if (workouts.isEmpty()) {
            Text("No workouts recorded yet. Start training!", color = TextSecondary, fontSize = 14.sp)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(workouts) { w ->
                    val color = when (w.exerciseType) { "PUSHUP" -> AccentGreen; "SQUAT" -> AccentBlue; "PLANK" -> AccentOrange; else -> AccentGreen }
                    FitnessCard(accent = color) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(w.exerciseType.lowercase().replaceFirstChar { it.uppercase() }, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(w.date, color = TextSecondary, fontSize = 12.sp)
                            }
                            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                                if (w.reps > 0) Text("${w.reps} reps", color = color, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                if (w.durationSeconds > 0) Text("${w.durationSeconds}s", color = color, fontSize = 13.sp)
                                val score = (w.formScore * 100).toInt()
                                if (score > 0) Text("Form: $score%", color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
