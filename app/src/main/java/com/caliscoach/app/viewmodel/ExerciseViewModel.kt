package com.caliscoach.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.caliscoach.app.CalisCoachApp
import com.caliscoach.app.data.model.ExerciseType
import com.caliscoach.app.data.model.FormFeedback
import com.caliscoach.app.data.repository.WorkoutRepository
import com.caliscoach.app.pose.PoseAnalyzer
import com.caliscoach.app.pose.RepCounter
import com.google.mlkit.vision.pose.Pose
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(app: Application) : AndroidViewModel(app) {
    private val db = (app as CalisCoachApp).database
    private val repo = WorkoutRepository(db.workoutDao(), db.dailyStatsDao())
    private val repCounter = RepCounter()

    private val _reps = MutableStateFlow(0)
    val reps: StateFlow<Int> = _reps.asStateFlow()
    private val _feedback = MutableStateFlow(FormFeedback.DETECTING)
    val feedback: StateFlow<FormFeedback> = _feedback.asStateFlow()
    private val _timer = MutableStateFlow(0L)
    val timer: StateFlow<Long> = _timer.asStateFlow()
    private val _isActive = MutableStateFlow(false)
    val isActive: StateFlow<Boolean> = _isActive.asStateFlow()
    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    private var timerJob: Job? = null
    private var exerciseType: ExerciseType = ExerciseType.PUSHUP

    fun setExerciseType(type: String) { exerciseType = ExerciseType.entries.firstOrNull { it.name == type } ?: ExerciseType.PUSHUP }

    fun start() {
        _isActive.value = true; _isSaved.value = false; repCounter.reset(); _reps.value = 0; _timer.value = 0
        timerJob = viewModelScope.launch { while (_isActive.value) { delay(1000); _timer.value++ } }
    }

    fun stop() { _isActive.value = false; timerJob?.cancel() }

    fun onPoseDetected(pose: Pose) {
        if (!_isActive.value) return
        when (exerciseType) {
            ExerciseType.PUSHUP -> { val r = PoseAnalyzer.analyzePushup(pose); _feedback.value = r.feedback; _reps.value = repCounter.countPushup(r) }
            ExerciseType.SQUAT -> { val r = PoseAnalyzer.analyzeSquat(pose); _feedback.value = r.feedback; _reps.value = repCounter.countSquat(r) }
            ExerciseType.PLANK -> { val r = PoseAnalyzer.analyzePlank(pose); _feedback.value = r.feedback }
            else -> _feedback.value = FormFeedback.GOOD_FORM
        }
    }

    fun saveWorkout() {
        viewModelScope.launch { repo.saveWorkout(exerciseType.name, _reps.value, _timer.value.toInt(), repCounter.getFormScore()); _isSaved.value = true }
    }
}
