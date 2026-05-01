package com.caliscoach.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.caliscoach.app.data.db.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts ORDER BY timestamp DESC")
    fun getAll(): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE date = :date ORDER BY timestamp DESC")
    fun getByDate(date: String): Flow<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE date BETWEEN :start AND :end ORDER BY timestamp DESC")
    fun getBetweenDates(start: String, end: String): Flow<List<WorkoutEntity>>

    @Query("SELECT COALESCE(SUM(reps),0) FROM workouts WHERE exerciseType = :type")
    fun getTotalReps(type: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(reps),0) FROM workouts WHERE exerciseType = :type AND date = :date")
    fun getTotalRepsByDate(type: String, date: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationSeconds),0) FROM workouts WHERE exerciseType = 'PLANK'")
    fun getTotalPlankSeconds(): Flow<Int>
}
