package com.caliscoach.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.caliscoach.app.CalisCoachApp
import com.caliscoach.app.data.db.entity.WorkoutEntity
import com.caliscoach.app.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as CalisCoachApp).database
    private val repo = WorkoutRepository(db.workoutDao(), db.dailyStatsDao())
    private val _workouts = MutableStateFlow<List<WorkoutEntity>>(emptyList())
    val workouts: StateFlow<List<WorkoutEntity>> = _workouts.asStateFlow()
    init { viewModelScope.launch { repo.getAllWorkouts().collectLatest { _workouts.value = it } } }
}
