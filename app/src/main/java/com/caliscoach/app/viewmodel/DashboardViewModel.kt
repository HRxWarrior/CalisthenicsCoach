package com.caliscoach.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.caliscoach.app.CalisCoachApp
import com.caliscoach.app.data.db.entity.DailyStatsEntity
import com.caliscoach.app.data.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as CalisCoachApp).database
    private val repo = WorkoutRepository(db.workoutDao(), db.dailyStatsDao())
    private val _weeklyStats = MutableStateFlow<List<DailyStatsEntity>>(emptyList())
    val weeklyStats: StateFlow<List<DailyStatsEntity>> = _weeklyStats.asStateFlow()
    private val _totalPushups = MutableStateFlow(0)
    val totalPushups: StateFlow<Int> = _totalPushups.asStateFlow()
    private val _totalSquats = MutableStateFlow(0)
    val totalSquats: StateFlow<Int> = _totalSquats.asStateFlow()
    private val _totalPlankSec = MutableStateFlow(0)
    val totalPlankSec: StateFlow<Int> = _totalPlankSec.asStateFlow()
    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    init {
        viewModelScope.launch { repo.getWeeklyStats().collectLatest { _weeklyStats.value = it; _currentStreak.value = it.firstOrNull()?.streak ?: 0 } }
        viewModelScope.launch { repo.getTotalReps("PUSHUP").collectLatest { _totalPushups.value = it } }
        viewModelScope.launch { repo.getTotalReps("SQUAT").collectLatest { _totalSquats.value = it } }
        viewModelScope.launch { repo.getTotalPlankSeconds().collectLatest { _totalPlankSec.value = it } }
    }
}
