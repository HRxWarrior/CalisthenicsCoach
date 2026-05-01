package com.caliscoach.app.data.repository

import com.caliscoach.app.data.db.dao.DailyStatsDao
import com.caliscoach.app.data.db.dao.WorkoutDao
import com.caliscoach.app.data.db.entity.DailyStatsEntity
import com.caliscoach.app.data.db.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutRepository(private val workoutDao: WorkoutDao, private val statsDao: DailyStatsDao) {
    fun getAllWorkouts(): Flow<List<WorkoutEntity>> = workoutDao.getAll()
    fun getWorkoutsByDate(date: String): Flow<List<WorkoutEntity>> = workoutDao.getByDate(date)
    fun getWeeklyStats(): Flow<List<DailyStatsEntity>> = statsDao.getLastWeek()
    fun getTotalReps(type: String): Flow<Int> = workoutDao.getTotalReps(type)
    fun getTotalPlankSeconds(): Flow<Int> = workoutDao.getTotalPlankSeconds()

    suspend fun saveWorkout(type: String, reps: Int, duration: Int, formScore: Float) {
        val today = LocalDate.now().toString()
        workoutDao.insert(WorkoutEntity(exerciseType = type, reps = reps, durationSeconds = duration, formScore = formScore, date = today))
        val existing = statsDao.getByDate(today)
        val stats = existing ?: DailyStatsEntity(date = today)
        val yesterday = LocalDate.now().minusDays(1).toString()
        val yesterdayStats = statsDao.getByDate(yesterday)
        val streak = if (yesterdayStats != null) yesterdayStats.streak + 1 else if (existing != null) existing.streak else 1
        val updated = stats.copy(
            totalPushups = stats.totalPushups + if (type == "PUSHUP") reps else 0,
            totalSquats = stats.totalSquats + if (type == "SQUAT") reps else 0,
            totalPlankSeconds = stats.totalPlankSeconds + if (type == "PLANK") duration else 0,
            totalWorkouts = stats.totalWorkouts + 1,
            streak = streak
        )
        statsDao.insertOrUpdate(updated)
    }
}
